package com.example.rani.stayhub.strategy;

import java.math.BigDecimal;
import com.example.rani.stayhub.entity.Inventory;

public class BasePricingStrategy implements PricingStrategy {
   
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
      return inventory.getRoom().getBaseprice();
    }
}
