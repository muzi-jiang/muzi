package com.muzi.test.strategy.test;

public class StrategyA implements StrategyInterface {

    @Override
    public String getStrategy(String name) {
        return "A"+name;
    }
}
