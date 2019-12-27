package com.muzi.design.strategy;

import java.math.BigDecimal;

/**
 *
 * 策略模式
 *
 * 1000内  原价
 * 1000 -2000(包含1000) 0.9
 * 2000 以上   0.5
 */
public class Test {

    public static void main(String[] args) throws Exception {

       PriceContext priceContext = new PriceContext();

        System.out.println(priceContext.getPriceContext(new BigDecimal("800")));
        System.out.println(priceContext.getPriceContext(new BigDecimal("1000")));
        System.out.println(priceContext.getPriceContext(new BigDecimal("1500")));
        System.out.println(priceContext.getPriceContext(new BigDecimal("2000")));
        System.out.println(priceContext.getPriceContext(new BigDecimal("3000")));
    }
}
