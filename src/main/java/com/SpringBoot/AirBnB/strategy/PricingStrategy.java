package com.SpringBoot.AirBnB.strategy;

import com.SpringBoot.AirBnB.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
