package com.example.rani.stayhub.strategy;

import java.math.BigDecimal;
import com.example.rani.stayhub.entity.Inventory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;
    @Override
    public BigDecimal calculatedPrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatedPrice(inventory);
        double occupancyRate = (double)inventory.getBookedCount() /  inventory.getTotalCount();
        if(occupancyRate > 0.8) {
            return price.multiply(BigDecimal.valueOf(1.2));
        } else {
            return price;
        }
    }

}
