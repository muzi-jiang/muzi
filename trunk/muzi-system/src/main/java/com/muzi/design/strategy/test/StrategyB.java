package com.muzi.test.strategy.test;

public class StrategyB implements StrategyInterface {
    @Override
    public String getStrategy(String name) {
        return "B" + name;
    }
}
