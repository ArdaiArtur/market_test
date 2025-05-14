package com.market.market;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.market.market.seeder.PriceSnapshotSeeder;
import com.market.market.seeder.PromotionSeeder;

@Configuration
public class SeederRunner {

    // mvn clean spring-boot:run -D spring-boot.run.arguments=--seed=true
    @Bean @Order(1)
    public CommandLineRunner seedPromotions(PromotionSeeder promotionSeeder) {
        return args -> {
            for (String arg : args) {
                if (arg.equalsIgnoreCase("--seed=true")) {
                    promotionSeeder.seed(1); // seed 10 
                    System.out.println("Seeding completed.");
                    return;
                }
            }
            System.out.println("No seeding argument provided. Skipping seeding...");
        };
    }

    @Bean @Order(2)
    public CommandLineRunner seedSnapshots(PriceSnapshotSeeder priceSnapshotSeeder) {
        return args -> {
            for (String arg : args) {
                if (arg.equalsIgnoreCase("--seed=true")) {
                    priceSnapshotSeeder.seed(1); // seed 10 
                    System.out.println("Seeding completed.");
                    return;
                }
            }
            System.out.println("No seeding argument provided. Skipping seeding...");
        };
    }

    
}
