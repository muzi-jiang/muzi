package com.muzi.design.strategy.test;

/**
 * 策略模式
 */

public class StrategyTest {


    public static void main(String[] args) {

        StrategyContext strategyContextA = new StrategyContext(new StrategyA());
        StrategyContext strategyContextB = new StrategyContext(new StrategyB());


        System.out.println(strategyContextA.getContext("one"));
        System.out.println(strategyContextB.getContext("two"));

    }
}
