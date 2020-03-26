package com.example.superselain;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/* TODO ---DISCLAIMER--- I wasn't sure if the assignment wanted me to make it so that the app would detect every url change (by overriding WebViewClient.doUpdateVisitedHistory according to Google) and store the urls to the list
    but after doing some research and trying to do that I came to the conclusion that hopefully that wasn't necessary */

public class MainActivity extends AppCompatActivity {

    ArrayList<String> prevPages;
    ArrayList<String> nextPages;

    int prevIndex = 0;
    int nextIndex = 0;
    WebView web;
    EditText urlView;
    Button back;
    Button fwd;
    Button JS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prevPages = new ArrayList();
        nextPages = new ArrayList();

        urlView = findViewById(R.id.urlText);
        web = findViewById(R.id.webView);
        back = findViewById(R.id.backButton);
        fwd = findViewById(R.id.fwdButton);
        JS = findViewById(R.id.JSbutton);

        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);



        urlView.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    System.out.println(urlView.getText().toString());

                    if (urlView.getText().toString().equals("index.html"))
                    {
                        web.loadUrl("file:///android_asset/index.html");
                        prevPages.add("file:///android_asset/index.html");
                        prevIndex = prevPages.size()-1;
                        nextIndex = 0;
                        nextPages.clear();
                    }
                    else {

                        String url = "http://" + urlView.getText().toString();
                        web.loadUrl(url);
                        nextIndex = 0;
                        nextPages.clear();

                        if (prevPages.size() == 10) {
                            prevPages.remove(0);
                            prevPages.add(url); // this is to keep tabs on previously visited pages
                            prevIndex = prevPages.size()-1;

                        } else {

                            if (prevIndex != prevPages.size()-1){
                                prevIndex = prevPages.size()-1;
                            }

                            prevPages.add(url);
                            prevIndex = prevPages.size()-1;
                        }

                        System.out.println("PREV ="+ prevIndex);
                        System.out.println("NEXT ="+ nextIndex);
                        System.out.println("NEXTPAGES SIZE=" + nextPages.size());
                        System.out.println(nextPages);
                        System.out.println("###################");
                        return true;

                        }
                }
                return false;
            }
        });


    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void executeJSBack(View v) {
        web.evaluateJavascript("javascript:initialize()", null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void executeJS (View v)
    {
        web.evaluateJavascript("javascript:shoutOut()", null);
    }

    public void goFwd(View v) {
        if (nextIndex == 0) // if there are no next pages
        {
            Toast toast = Toast.makeText(this, "No next pages stored", Toast.LENGTH_SHORT);
            toast.show();

        } else {

            String url = nextPages.get(nextIndex-1);
            web.loadUrl(url);

            prevIndex++;
            nextIndex--;
            System.out.println("PREV ="+ prevIndex);
            System.out.println("NEXT ="+ nextIndex);
            System.out.println("NEXTPAGES SIZE=" + nextPages.size());
            System.out.println(nextPages);
            System.out.println("###################");

        }
    }

    public void goBack(View v) {
        if (prevIndex == 0)
        {
            Toast toast = Toast.makeText(this, "No previous pages stored", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            String url = prevPages.get(prevIndex-1); // prevIndex = list size -1, eg 6 pages in list makes last index is 5 so the previous page is 5-1

            if (nextPages.size() == 10) {
                nextPages.remove(9);
            }
            nextPages.add(web.getUrl());
            web.loadUrl(url);

            if (0 <= prevIndex && prevIndex < 9) { // this prevents outOfBoundsException with nextIndex where the array size and nextIndex would both be 9
                nextIndex++;
            }

            prevIndex--;
            System.out.println("NEXTPAGES SIZE=" + nextPages.size());
            System.out.println("PREV ="+ prevIndex);
            System.out.println("NEXT ="+ nextIndex);
            System.out.println(nextPages);
            System.out.println("###################");
        }
    }

}
