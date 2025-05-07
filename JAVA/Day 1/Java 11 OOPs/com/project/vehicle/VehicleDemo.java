package com.project.vehicle;

public class VehicleDemo {
    public static void main(String args[]){
        Car myCar = new Car("Audi");
        myCar.start();
        myCar.accelerate(60);
        myCar.accelerate(40);
        System.out.println(myCar.getInfo());
        myCar.stop();
        myCar.accelerate(20);

        Bike myBike = new Bike("Pulsur");
        myBike.start();
        myBike.accelerate(30);
        myBike.accelerate(-10.0);
        myBike.stop();
        myBike.accelerate(40);
        myBike.getInfo();

    }
}
