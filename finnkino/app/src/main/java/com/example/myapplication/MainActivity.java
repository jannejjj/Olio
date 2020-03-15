package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends AppCompatActivity {

    Spinner kinoSpinner;

    Finnkino finnkino = Finnkino.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        readAreas(); // reading the area XML

        kinoSpinner = (Spinner)findViewById(R.id.kinoSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_spinner_dropdown_item, finnkino.getTheatreNames() );
        kinoSpinner.setAdapter( adapter );

    }


    public void readAreas() {
        finnkino.readXML();
    }


    public void getMovies()
    {

    }

}