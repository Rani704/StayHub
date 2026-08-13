package com.example.rani.stayhub.strategy;

import java.math.BigDecimal;
import com.example.rani.stayhub.entity.Inventory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatedPrice(Inventory inventory) {
     BigDecimal price = wrapped.calculatedPrice(inventory);
     return price.multiply(inventory.getSurgeFactor());
    }

}
