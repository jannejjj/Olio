package com.example.myapplication;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BottleDispenser {

    private static BottleDispenser bt = new BottleDispenser(); // Tehdään singleton

    public static BottleDispenser getInstance() {
        return bt; // singletonin palautus
    }

    private int bottles;
    private int remainder;
    // The array for the Bottle-objects
    private ArrayList<Bottle> BottleArray = new ArrayList();
    private ArrayList<Bottle> PurchaseList = new ArrayList();
    public float money;


    public BottleDispenser() {


        bottles = 5;

        money = 0;

        BottleArray.add(new Bottle("Pepsi Max", 0.5, 1.80));
        BottleArray.add(new Bottle("Pepsi Max", 1.5, 2.20));
        BottleArray.add(new Bottle("Coca-Cola Zero", 0.5, 2.00));
        BottleArray.add(new Bottle("Coca-Cola Zero", 1.5, 2.50));
        BottleArray.add(new Bottle("Fanta Zero", 0.5, 1.95));
        remainder = BottleArray.size();
    }


    public void addMoney(float f) {
        money += f;
    }

    public int buyBottle(int buyIndex) {

        if ((remainder != 0) && (money != 0)) {

            Bottle bottle = BottleArray.get(buyIndex);

            if (bottle.getPrice() <= money) {
                remainder--;
                money -= bottle.getPrice();
            } else {
                return 1;
            }
        } else if (money == 0) {
            return 1;
        }
        else if (remainder == 0)
        {
            return 2;
        }
         return 0;
    }

    public float getMoney() {
        return money;

    }


    public void removeBottle(int removeIndex) {
        BottleArray.remove(removeIndex);
    }


    public ArrayList getContents() {
        ArrayList<String> Contents = new ArrayList();
        Contents.add("Choose bottle");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE); // Puhelimeni sijainti on USA joten pakotan tällä tavalla näyttämään valuutan euroina
        for (Bottle b : BottleArray) {
            String s = b.getName() + ", Price: " + formatter.format(b.getPrice()) + ", Size: " + b.getSize();
            Contents.add(s);
        }
        return Contents;
    }


    public void resetMoney()
    {
        money = 0;
    }

    public void listPurchase(int index)
    {
        PurchaseList.add(BottleArray.get(index));
    }

    public void saveReceipt(Context c)
    {
        int i = 1;
        try
        {

            OutputStream os = c.openFileOutput("receipt.txt", Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);

            bw.write("Purchase no. " + i);
            bw.write("\n");
            bw.write("Product: " + PurchaseList.get(PurchaseList.size()-1).getName());
            bw.write("\n");
            bw.write("Price: " + formatter.format(PurchaseList.get(PurchaseList.size()-1).getPrice()));

            bw.write("\n");
            bw.close();
            os.close();

            i++;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}