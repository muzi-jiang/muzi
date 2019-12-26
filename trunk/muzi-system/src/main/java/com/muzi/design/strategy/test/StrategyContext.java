package com.muzi.test.strategy.test;

public class StrategyContext {

    private StrategyInterface strategyInterface;

    public StrategyContext(StrategyInterface strategyInterface){
        this.strategyInterface = strategyInterface;
    }

    public String getContext(String name){
        return strategyInterface.getStrategy(name);
    }
}
