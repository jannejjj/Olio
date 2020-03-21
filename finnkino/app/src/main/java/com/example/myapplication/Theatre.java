package com.example.myapplication;

public class Theatre {
    String Name;
    String Country;
    String Address;
    String Open;

    public Theatre(String country, String name, String open , String address)
    {
        Country = country;
        Name = name;
        Open = open;
        Address = address;
    }
    public String getName()
    {
        return Name;
    }

    public String getOpen() { return Open; }

    public String getCountry() { return Country;}

    public String getAddress() { return Address;}
}
