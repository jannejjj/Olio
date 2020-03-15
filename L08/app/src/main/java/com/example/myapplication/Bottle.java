package com.example.myapplication;

public class Bottle {

    private String name;

    private String manufacturer;

    private double total_energy;

    private double size;

    private double price;



    public Bottle(){}

    public Bottle(String name_, double sz, double pz){
        name = name_;
        size = sz;
        price = pz;
    }

    public String getName(){
        return name;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public double getEnergy(){
        return total_energy;
    }

    public double getPrice() {
        return price;
    }

    public double getSize() {
        return size;
    }
}