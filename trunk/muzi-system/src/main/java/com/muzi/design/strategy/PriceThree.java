package com.muzi.design.strategy;

import java.math.BigDecimal;

@PriceRegion(min = 2000)
public class PriceThree implements Price {
    @Override
    public BigDecimal getPrice(BigDecimal bigDecimal) {
        return bigDecimal.multiply(new BigDecimal("0.5"));
    }
}
