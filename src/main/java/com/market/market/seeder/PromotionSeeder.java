package com.market.market.seeder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.market.market.entity.Promotion;
import com.market.market.repository.PromotionRepository;


@Component
public class PromotionSeeder {

    private final PromotionRepository promotionRepository;
    private final Random random = new Random();


    public PromotionSeeder(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public void seed(int count) {
        for (int i = 0; i < count; i++) {
            Promotion promotion = new Promotion();
            /*
             * SET DATE HERE
             */
            LocalDate fromDate = LocalDate.of(2025, 06,23);

            // Add 1–12 days to get a valid toDate
            LocalDate toDate = fromDate.plusDays(1 + random.nextInt(7));


            BigDecimal discount = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextInt(5, 51) // Random integer between 5 and 50
            );

            if (!promotionRepository.existsByFromDateAndToDateAndDiscountPct(fromDate, toDate, discount)) {
                // Set the generated values to the promotion entity
                promotion.setFromDate(fromDate);
                promotion.setToDate(toDate);
                promotion.setDiscountPct(discount);

                // Save the promotion to the repository
            promotionRepository.save(promotion);
            } else {
                // Optionally, log or handle the case where the promotion is a duplicate
                System.out.println("Duplicate promotion detected, skipping...");
            }
        }
    }
}
