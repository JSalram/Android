package com.example.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity
{
    private TextView tv;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv = findViewById(R.id.textView);
        msg = MainActivity.getMensaje();

        printMensaje();
    }

    public void printMensaje()
    {
        if (!msg.equals(""))
        {
            tv.setText(msg);
        }
    }
}