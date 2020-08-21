package com.example.weekly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskActivity extends AppCompatActivity
{
    private static String task;
    private static int time;

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

        addTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!editTask.getText().toString().equals("") &&
                    !editTime.getText().toString().equals(""))
                {
                    task = editTask.getText().toString();
                    time = Integer.parseInt(editTime.getText().toString());

                    TaskActivity.super.onBackPressed();
                }
            }
        });

    }

    public static String getTask() {return task;}
    public static int getTime() {return time;}

}