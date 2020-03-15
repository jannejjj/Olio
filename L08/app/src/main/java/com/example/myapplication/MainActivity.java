package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner bottleSpinner;
    TextView moneyView;
    SeekBar seekBar;
    TextView addView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bottleSpinner = (Spinner)findViewById(R.id.pulloSpinner);
        moneyView = (TextView)findViewById(R.id.textViewMoney);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        addView = (TextView)findViewById(R.id.addView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_spinner_dropdown_item, bt.getContents() );
        bottleSpinner.setAdapter( adapter );


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);
                addView.setText(formatter.format(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    BottleDispenser bt = BottleDispenser.getInstance();

    public void addMoney (View v)
    {

        float amount = seekBar.getProgress();

        bt.addMoney(amount);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);

        String s = formatter.format(bt.getMoney());
        moneyView.setText(s);
        seekBar.setProgress(0);
    }

    public void buyBottle (View v)
    {
        int pos = bottleSpinner.getSelectedItemPosition();
        if (pos == 0)
        {
            Toast toast = Toast.makeText(this, "That's not a bottle!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            int code = bt.buyBottle(pos - 1);
            if (code == 1)
            {
                Toast toast = Toast.makeText(this, "You don't have enough money!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(this, ("You bought " + bottleSpinner.getSelectedItem()), Toast.LENGTH_SHORT);
                toast.show();
                bt.listPurchase(pos - 1);
                bt.removeBottle(pos - 1);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, bt.getContents());
                bottleSpinner.setAdapter(adapter);

                NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE); // päivitetään rahatilanne
                String s = formatter.format(bt.getMoney());


                moneyView.setText(s);
            }
        }
    }


    public void takeMoney (View v)
    {
            if (bt.money != 0)
            {
                NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);
                Toast toast = Toast.makeText(this, "Returning " + formatter.format(bt.getMoney()), Toast.LENGTH_SHORT);
                toast.show();

                bt.resetMoney();

                String s = formatter.format(bt.getMoney());
                moneyView.setText(s);
            }
            else
            {
                Toast toast = Toast.makeText(this, "There's no money in the dispenser!", Toast.LENGTH_SHORT);
                toast.show();
            }

    }

    public void saveReceipt (View v)
    {
        bt.saveReceipt(v.getContext());
    }


}
