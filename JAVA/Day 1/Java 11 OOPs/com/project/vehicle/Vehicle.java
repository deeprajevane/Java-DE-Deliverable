package com.project.vehicle;

abstract class Vehicle implements BasicVehicle{
    private String brand;
    private int tankCapcity;
    private double speed;
    private boolean isRunning;

    public Vehicle(String brand) {
        this.brand = brand;
        this.speed =0.0;
        this.isRunning = true;

    }

    @Override
    public void start() {
        isRunning = true;
        System.out.println(brand + " is Starting!!");
    }

    @Override
    public void stop() {
        speed =0.0;
        isRunning = false;
        System.out.println(brand + " is stopped !!!");
    }

    @Override
    public abstract void accelerate(double speed);
    @Override
    public abstract String getInfo();

    public int getTankCapcity() {
        return tankCapcity;
    }

    public void setTankCapcity(int tankCapcity) {
        this.tankCapcity = tankCapcity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if(speed<0.0) {
            this.speed = 0.0;
            System.out.println("Speed Can't be Negative");
        }
        else {
            this.speed = speed;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}