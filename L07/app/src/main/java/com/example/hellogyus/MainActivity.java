package com.example.hellogyus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Context context = null;

    TextView text;
    EditText edit;
    EditText edit2;
    EditText editIO;
    EditText ioName;
    TextWatcher tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.testMethod();

        context = MainActivity.this;

        edit = (EditText)findViewById(R.id.editText);
        edit2 = (EditText)findViewById(R.id.editText2);
        editIO = (EditText)findViewById(R.id.editTextIO);
        ioName = (EditText)findViewById(R.id.editTextFilename);

        text = (TextView) findViewById(R.id.textView);
        text.setText("Hei Maailma!");


        tw = new TextWatcher() { // Real-time text editing
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = edit2.getText().toString();
                text.setText(input);

            }
        };

    }

    public void testMethod(View v)
    {
        System.out.println("Hello World!"); // Tehtävä 1
    }

    public void changeText(View v)
    {
        text.setText("Hello World!");
    }

    public void inputToText(View v)
    {
        text.setText(edit.getText().toString());
    }

    public void activate4(View v)
    {
        edit2.addTextChangedListener(tw);

    }

    public void disable4(View v)
    {
        edit2.removeTextChangedListener(tw);
        text.setText("Hei Maailma!");
    }

    public void readFile(View v)
    {
        try
        {
            InputStream ins = context.openFileInput(ioName.getText().toString());

            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s = "";

            if ((s=br.readLine()) != null)
            {
                text.setText(s);
            }

            ins.close();
        }
        catch (IOException e)
        {
            Log.e("IOException,", "Virhe syötteessä");
        }
    }

    public void writeFile(View v)
    {
        try
        {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(ioName.getText().toString(), Context.MODE_PRIVATE));

            String s = "";

            s = editIO.getText().toString();
            ows.write(s);

            ows.close();
        }
        catch (IOException e)
        {
            Log.e("IOException,", "Virhe syötteessä");
        }

    }
}
