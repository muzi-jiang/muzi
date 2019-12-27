package com.muzi.design.strategy;

import java.math.BigDecimal;

public class PriceContext {

    public BigDecimal getPriceContext(BigDecimal costPrice) throws Exception {
         return PriceFactory.getInstance().getFactoryPrice(costPrice).getPrice(costPrice);
    }

}
