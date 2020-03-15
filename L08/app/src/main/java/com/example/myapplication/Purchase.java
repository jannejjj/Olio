package com.example.myapplication;

public class Purchase {

    float price;
    String name;

    public Purchase(float f, String s)
    {
        price = f;
        name = s;
    }

    public String getName(){
        return name;
    }

    public float getPrice() {
        return price;
    }
}
