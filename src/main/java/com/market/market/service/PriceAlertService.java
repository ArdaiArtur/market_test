package com.market.market.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.market.market.dto.PriceAlertCreateDto;
import com.market.market.dto.PriceAlertResponseDto;
import com.market.market.entity.PriceAlert;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Product;
import com.market.market.entity.User;
import com.market.market.repository.PriceAlertRepository;
import com.market.market.repository.PriceSnapshotRepository;
import com.market.market.repository.ProductRepository;
import com.market.market.repository.UserRepository;

@Service
public class PriceAlertService {

    private final PriceAlertRepository alertRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final PriceSnapshotRepository snapshotRepo;

    public PriceAlertService(PriceAlertRepository alertRepo,
                             UserRepository userRepo,
                             ProductRepository productRepo,
                             PriceSnapshotRepository snapshotRepo) {
        this.alertRepo   = alertRepo;
        this.userRepo    = userRepo;
        this.productRepo = productRepo;
        this.snapshotRepo = snapshotRepo;
    }

    /**
     * Create and persist a new price alert for the given user & product.
     */
    public PriceAlertResponseDto createAlert(PriceAlertCreateDto dto) {
    User user = userRepo.findById(dto.userId())
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.userId()));
    Product product = productRepo.findById(dto.productId())
        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + dto.productId()));

    PriceAlert alert = new PriceAlert();
    alert.setUser(user);
    alert.setProduct(product);
    alert.setTargetPrice(dto.targetPrice());

    PriceAlert saved = alertRepo.save(alert);

    return new PriceAlertResponseDto(
        saved.getAlertId(),
        user.getUserId(),
        product.getProductId(),
        saved.getTargetPrice(),
        saved.isTriggered(),
        saved.isActive(),
        saved.getCreatedAt(),
        saved.getTriggeredAt()
    );
}

    /**
     * Return all active, non-triggered alerts.
     */
    public List<PriceAlertResponseDto> getActiveAlerts() {
        return alertRepo.findByTriggeredFalseAndActiveTrue().stream()
            .map(alert -> new PriceAlertResponseDto(
                alert.getAlertId(),
                alert.getUser().getUserId(),
                alert.getProduct().getProductId(),
                alert.getTargetPrice(),
                alert.isTriggered(),
                alert.isActive(),
                alert.getCreatedAt(),
                alert.getTriggeredAt()
            ))
            .toList();
    }

    /**
     * Mark the given alert as triggered and record the timestamp.
     */
    public void markAsTriggered(PriceAlert alert) {
        alert.setTriggered(true);
        alert.setTriggeredAt(LocalDateTime.now());
        alertRepo.save(alert);
    }

    @Scheduled(cron = "0 0 3 * * *") // every day at 3:00 AM
    public void checkPriceAlerts() {
        List<PriceAlert> alerts = alertRepo.findByTriggeredFalseAndActiveTrue();
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        for (PriceAlert alert : alerts) {
            Optional<PriceSnapshot> snapshot = snapshotRepo
                .findTopByProduct_ProductIdAndSnapshotDateOrderBySnapshotDateDesc(alert.getProduct().getProductId(), monday);

            if (snapshot.isPresent() && snapshot.get().getPrice().compareTo(alert.getTargetPrice()) <= 0) {
                alert.setTriggered(true);
                alert.setTriggeredAt(LocalDateTime.now());
                alertRepo.save(alert);
                sendAlert(alert, snapshot.get());
            }
        }
    }

    private void sendAlert(PriceAlert alert, PriceSnapshot snapshot) {
        System.out.println("ALERT SENT: User " + alert.getUser().getUserId()
            + " notified that product " + alert.getProduct().getProductId()
            + " reached target price of " + alert.getTargetPrice()
            + ". Current price: " + snapshot.getPrice());
    }

}
