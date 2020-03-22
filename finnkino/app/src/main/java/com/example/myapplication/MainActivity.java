package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Spinner kinoSpinner;
    Finnkino finnkino = Finnkino.getInstance();
    ConstraintLayout constraintLayout;
    ListView listView;
    CustomAdapter customAdapter;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        readXML(); // reading the XML to start with

        constraintLayout = (ConstraintLayout)findViewById(R.id.ConstraintLayout);
        kinoSpinner = (Spinner)findViewById(R.id.spinner);
        listView = (ListView)findViewById(R.id.listView);



        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_spinner_dropdown_item, finnkino.getPostNames() ); // getting the spinner going
        kinoSpinner.setAdapter( adapter );


        kinoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void readXML() {
       // finnkino.readXML_FI();
        finnkino.readXML_EST();
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            int count = finnkino.getPostListLength();
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlistlayout,null);

            TextView textView_name = (TextView) convertView.findViewById(R.id.textView_name);
            TextView textView_hours = (TextView) convertView.findViewById(R.id.textView_hours);
            TextView textView_address = (TextView) convertView.findViewById(R.id.textView_address);

            ArrayList<Theatre> list = finnkino.getPostList();

            textView_name.setText(list.get(position).getName());
            textView_hours.setText(list.get(position).getOpen());
            textView_address.setText(list.get(position).getAddress());

            return convertView;
        }
    }

    public void search(View v)
    {

        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }
}