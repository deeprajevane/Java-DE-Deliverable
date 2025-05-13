package com.project.vehicle;

public class Car extends Vehicle {
    public Car(String brand){
        super(brand);
    }

    @Override
    public void accelerate(double speed) {
        if(!isRunning()){
            System.out.println("Car is not Started");
            return;
        }
        setSpeed(speed);
        System.out.println(getBrand() + " Car is running with " + getSpeed() + " Km/h");
    }
    @Override
    public String getInfo(){
        if(isRunning())
            return getBrand() + " Car is Started & running with " +getSpeed() + " Km/h";
        return getBrand() + " Car is Not running";
    }
}
