package com.muzi.design.prototype;

import java.util.Hashtable;

public class StudentCache {

    private static Hashtable<String,Student> table = new Hashtable<>();

    public static Student getStudent(String id){
        Student student = table.get(id);
        try {
            return (Student) student.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadStudent(){
        StudentA studentA = new StudentA();
        studentA.setId("1");
        table.put(studentA.getId(),studentA);

        StudentB studentB = new StudentB();
        studentB.setId("2");
        table.put(studentB.getId(),studentB);
    }

}
