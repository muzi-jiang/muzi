package com.muzi.test.strategy;

import java.math.BigDecimal;

@PriceRegion(min = 1000,max = 2000)
public class PriceTwo implements Price {
    @Override
    public BigDecimal getPrice(BigDecimal bigDecimal) {
        return bigDecimal.multiply(new BigDecimal("0.9"));
    }
}
