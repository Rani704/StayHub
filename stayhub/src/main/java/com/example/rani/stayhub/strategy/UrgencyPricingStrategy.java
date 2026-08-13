package com.example.rani.stayhub.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.rani.stayhub.entity.Inventory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy  {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatedPrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatedPrice(inventory);

        LocalDate today = LocalDate.now();
        if (!inventory.getDate().isBefore(today) && inventory.getDate().isBefore(today.plusDays(7))) {
            price = price.multiply(BigDecimal.valueOf(1.15));
        }
        
        return price;
    }
}

