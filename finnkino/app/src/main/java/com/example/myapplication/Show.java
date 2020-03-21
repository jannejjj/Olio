package com.example.myapplication;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Show {

    String title;
    String length;
    String year;
    String rating;
    Date start;

    public Show(String t, String l, String y, String r, String st)
    {
        title = t;
        length = l;
        year = y;
        rating = r;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH-mm-ss");
        try
        {
            start = sdf.parse(st);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
