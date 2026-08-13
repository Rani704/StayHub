package com.example.rani.stayhub.strategy;

import java.math.BigDecimal;
import com.example.rani.stayhub.entity.Inventory;


public interface PricingStrategy {

    BigDecimal calculatedPrice(Inventory inventory);
}
