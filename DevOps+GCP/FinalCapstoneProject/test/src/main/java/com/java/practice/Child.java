package com.java.practice;

public class Child extends Main{

    public static void main(String[] args) {

        Child child = new Child();
        Main parent = new Child();
        Main main =  new Main();
        child.testMethod();
        parent.testMethod();
        main.testMethod();
        //with the help of super keyword I will call parent class

    }

    public void testMethod(){
        super.testMethod();
        System.out.println("this is child class Method");

    }
}
