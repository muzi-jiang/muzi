package com.muzi.design.prototype;

/**
 * 原型模式
 */
public class Test {

    public static void main(String[] args) {
        StudentCache.loadStudent();

        StudentA studentA =(StudentA)StudentCache.getStudent("1");
        System.out.println(studentA.getName());

        StudentB studentB =(StudentB)StudentCache.getStudent("2");
        System.out.println(studentB.getName());

    }

}
