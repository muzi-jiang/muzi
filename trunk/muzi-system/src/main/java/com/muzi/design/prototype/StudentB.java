package com.muzi.design.prototype;

public class StudentB extends Student {

    public StudentB(){
        setName("StudentB");
    }

    @Override
    void draw() {
        System.out.println("我是学生B");
    }
}
