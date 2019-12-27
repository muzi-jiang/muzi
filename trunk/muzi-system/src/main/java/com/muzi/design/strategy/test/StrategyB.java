package com.muzi.design.strategy.test;

public class StrategyB implements StrategyInterface {
    @Override
    public String getStrategy(String name) {
        return "B" + name;
    }
}
