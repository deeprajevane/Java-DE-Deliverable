package com.java.practice;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {

        System.out.println("This is the main method with string array");
        int temp =123;
        Main m = new Main();
        m.testMethod();



    }

    public static void main(int x) {
        System.out.println("This is the main method with int x");
    }

    public void testMethod(){
        List<Integer> ls = new ArrayList<>();
        numberMethod(ls,"even");
    }
    //@PostMapping("/create")
    public String createOrder(String productName, int Quantity ){
        //JPA repository
        //save methods
        //try catch block
       //list integer
        return "item is created successfully";
    }
    //check whether even or odd
    //return either even or list from a input list
    public List<Integer> numberMethod(List<Integer> list , String type){
        List<Integer> output = new ArrayList<>();
        list.add(12);
        list.add(15);
        if(type.equals("even")){
            output =  list.stream().filter(n->n%2==0).toList();
        }
        else{
//            output = (List<Integer>) list.stream().filter(n->n%2!=0);
        }
        //JenkinsFile
///        define agent
        ///Get a code
        //Create a build
        //feign client
        //rest template
        /// session and session Factory
        /// Kafka

        System.out.println(output);


        return output;
    }

}