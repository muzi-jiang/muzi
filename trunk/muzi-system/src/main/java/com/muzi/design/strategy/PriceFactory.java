package com.muzi.design.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceFactory {

    private static PriceFactory FACTORY = new PriceFactory();

    static PriceFactory getInstance(){
        return FACTORY;
    }

    private List<Class<? extends Price>> priceList = new ArrayList<>();

    private PriceFactory(){
        priceList.add(PriceOne.class);
        priceList.add(PriceTwo.class);
        priceList.add(PriceThree.class);
    }

    public Price getFactoryPrice(BigDecimal price) throws Exception {
        for (Class<? extends Price> aClass : priceList){
            PriceRegion annotation = aClass.getAnnotation(PriceRegion.class);
            if(price.compareTo(new BigDecimal(annotation.min())) >=0 &&
                    price.compareTo(new BigDecimal(annotation.max())) <0){
                return aClass.newInstance();
            }
        }
        return null;
    }

}
