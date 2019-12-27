package com.muzi.design.prototype;

public class StudentA extends Student {


    public StudentA(){
        setName("StudentA");
    }


    @Override
    void draw() {
        System.out.println("我是学生A");
    }
}
