package com.example.weekly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;

public class TaskActivity extends AppCompatActivity
{
    private static String task;
    private static Time time;

    private Button addTask;
    private EditText editTask;
    private EditText editTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        addTask = findViewById(R.id.addTask);
        editTask = findViewById(R.id.editTask);
        editTime = findViewById(R.id.editTime);

        if(editTask.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        addTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!editTask.getText().toString().equals("") &&
                    !editTime.getText().toString().equals(""))
                {
                    task = editTask.getText().toString();
                    String strTime = editTime.getText().toString();
                    if (!strTime.contains(":"))
                    {
                        strTime += ":00";
                    }
                    time = Time.valueOf(strTime + ":00");

                    MainActivity.addTask(time, task);
                    MainActivity.escribeFichero(getApplicationContext());
                    MainActivity.reloadTasks(getApplicationContext());

                    TaskActivity.super.onBackPressed();
                }
            }
        });
    }
}