package com.example.rani.stayhub.strategy;

import java.math.BigDecimal;
import com.example.rani.stayhub.entity.Inventory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatedPrice(Inventory inventory) {
       BigDecimal price = wrapped.calculatedPrice(inventory);
       boolean isTodayHoliday = true;
       if(isTodayHoliday){
        price = price.multiply(BigDecimal.valueOf(1.25));
       }
       return price;
    }
}
