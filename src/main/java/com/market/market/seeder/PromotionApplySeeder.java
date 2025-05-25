package com.market.market.seeder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Promotion;
import com.market.market.entity.PromotionApply;
import com.market.market.repository.PriceSnapshotRepository;
import com.market.market.repository.PromotionApplyRepository;
import com.market.market.repository.PromotionRepository;

@Component
public class PromotionApplySeeder {

    private final PromotionApplyRepository applyRepo;
    private final PromotionRepository promotionRepo;
    private final PriceSnapshotRepository snapshotRepo;
    private final Random random = new Random();

    public PromotionApplySeeder(PromotionApplyRepository applyRepo,
                                PromotionRepository promotionRepo,
                                PriceSnapshotRepository snapshotRepo) {
        this.applyRepo     = applyRepo;
        this.promotionRepo = promotionRepo;
        this.snapshotRepo  = snapshotRepo;
    }

    /**
     * Seed `count` PromotionApply rows.
     * Randomly pairs promotions with snapshots, skipping exact duplicates.
     */
    public void seed(int count) {
        /*
         * SET DATE HERE
         */

        List<PriceSnapshot> snapshots = snapshotRepo.findBySnapshotDate(LocalDate.of(2025, 06, 30));
        if (snapshots.isEmpty()) return;
        Set<String> seen = new HashSet<>();
        int created = 0;

        while (created < count && !snapshots.isEmpty()) {
            
            PriceSnapshot ps = snapshots.get(random.nextInt(snapshots.size()));
            

            LocalDate date=ps.getSnapshotDate();
            LocalDate datePlus6 = date.plusDays(6);

            List<Promotion> maybePromo =promotionRepo.findByFromDateGreaterThanEqualAndToDateLessThanEqual(date, datePlus6);

            if (maybePromo.isEmpty()) {
                continue;
            }

            Promotion promo = maybePromo.get(random.nextInt(maybePromo.size()));

            String key = ps.getSnapshotId().toString();
            if (seen.contains(key)) {
                // Already created this pairing
                continue;
            }

            seen.add(key);
            PromotionApply apply = new PromotionApply(promo, ps);
            applyRepo.save(apply);
            created++;
        }
    }
}
