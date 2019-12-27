package com.muzi.design.strategy;

import java.math.BigDecimal;

/**
 * 1000以内不打折
 */
@PriceRegion(max = 1000)
public class PriceOne implements Price {
    @Override
    public BigDecimal getPrice(BigDecimal bigDecimal) {
        return bigDecimal;
    }
}
