package com.market.market.seeder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.market.market.entity.Magazine;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Product;
import com.market.market.repository.MagazineRepository;
import com.market.market.repository.PriceSnapshotRepository;
import com.market.market.repository.ProductRepository;

@Component
public class PriceSnapshotSeeder {

    private final PriceSnapshotRepository priceSnapshotRepository;
    private final MagazineRepository magazineRepository;
    private final ProductRepository productRepository;
    private final Random random = new Random();

    public PriceSnapshotSeeder(PriceSnapshotRepository priceSnapshotRepository,
                               MagazineRepository magazineRepository,
                               ProductRepository productRepository) {
        this.priceSnapshotRepository = priceSnapshotRepository;
        this.magazineRepository = magazineRepository;
        this.productRepository = productRepository;
    }

    public void seed(int count) {
        List<Magazine> magazines = magazineRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (int i = 0; i < count; i++) {
            // Randomly select magazine, product, and unit
            Magazine magazine = magazines.get(random.nextInt(magazines.size()));
            Product product = products.get(random.nextInt(products.size()));

            Optional<PriceSnapshot> latestSnapshot = priceSnapshotRepository.findTopByOrderBySnapshotDateDesc();
                LocalDate baseDate = latestSnapshot
                .map(snapshot -> snapshot.getSnapshotDate().plusDays(7)) // If exists, use the latest snapshot date
                .orElse(LocalDate.now()); // Default to today if no snapshots exist

            // Create a new PriceSnapshot
            PriceSnapshot priceSnapshot = new PriceSnapshot();
            priceSnapshot.setMagazine(magazine);
            priceSnapshot.setProduct(product);
            priceSnapshot.setSnapshotDate(baseDate); // Random date within the last 30 days
            priceSnapshot.setPrice(generateWeeklyPrice(priceSnapshotRepository.findFirstByProductProductIdOrderBySnapshotDateDesc(product.getProductId())
            .orElse(BigDecimal.valueOf(5.00)) ));
            priceSnapshot.setCurrency("RON"); // Assuming USD as the currency

            // Save the PriceSnapshot to the database
            priceSnapshotRepository.save(priceSnapshot);
        }
    }


    private BigDecimal generateWeeklyPrice(BigDecimal basePrice) {

        // Determine the maximum absolute change
        int maxChange = basePrice.compareTo(BigDecimal.valueOf(50)) < 0 ? 2 : 5;

        // Pick a random integer change amount between 0 and maxChange
        double changeDouble = random.nextDouble() * maxChange;

        // Randomly decide up or down
        boolean increase = random.nextBoolean();
        BigDecimal delta = BigDecimal.valueOf(changeDouble)
                                    .setScale(2, RoundingMode.HALF_UP);

        BigDecimal newPrice = increase
            ? basePrice.add(delta)
            : basePrice.subtract(delta);

        // Ensure we never go below zero
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            newPrice = basePrice;
        }

        return newPrice.setScale(2, RoundingMode.HALF_UP);
    }

}
