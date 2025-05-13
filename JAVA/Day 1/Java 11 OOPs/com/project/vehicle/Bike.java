package com.project.vehicle;

public class Bike extends Vehicle{
    public Bike(String brand) {
        super(brand);
    }

    @Override
    public void accelerate(double speed) {
        if(!isRunning())
        {
            System.out.println(" Bike is not started");
            return;
        }
        setSpeed(speed);
        System.out.println(getBrand() + " Bike is running on " + getSpeed() + "Km/h");
    }

    @Override
    public String getInfo(){
        if(isRunning())
        return getBrand() + " Bike is running with " +getSpeed() + " Km/h";
        return getBrand() + " Bike is not running";
    }
}
