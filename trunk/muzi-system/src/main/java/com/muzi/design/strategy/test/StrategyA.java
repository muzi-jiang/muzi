package com.muzi.design.strategy.test;

public class StrategyA implements StrategyInterface {

    @Override
    public String getStrategy(String name) {
        return "A"+name;
    }
}
