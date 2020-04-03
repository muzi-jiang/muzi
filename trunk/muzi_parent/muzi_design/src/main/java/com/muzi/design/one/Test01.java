package com.muzi.design.one;


/**
 * 单例模式 效率高 线程安全 不占内存  推荐使用
 */
public class Test01 {

    public static void main(String[] args) {
        Student instance1 = Student.getInstance();
        Student instance2 = Student.getInstance();
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance2 == instance1);
    }
}

class Student{

    private static volatile Student student;
    private Student(){}
    public static Student getInstance(){
        if(student == null){
            synchronized (Student.class){
                if(student == null){
                    student = new Student();
                }
            }
        }
        return student;
    }
}

