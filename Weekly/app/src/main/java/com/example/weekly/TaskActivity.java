package com.example.weekly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class TaskActivity extends AppCompatActivity
{
    public static final String EXTRA_BOOLEAN = "com.example.weekly.TaskActivity.EXTRA_BOOLEAN";
    public static final String EXTRA_BOOLEAN2 = "com.example.weekly.TaskActivity.EXTRA_BOOLEAN2";
    public static final String EXTRA_NUMBER = "com.example.weekly.TaskActivity.EXTRA_NUMBER";
    public static final String EXTRA_NUMBER2 = "com.example.weekly.TaskActivity.EXTRA_NUMBER2";
    public static final String EXTRA_STRING = "com.example.weekly.TaskActivity.EXTRA_STRING";
    public static final String EXTRA_TIME = "com.example.weekly.TaskActivity.EXTRA_TIME";

    private boolean adding;
    private boolean modifying;
    private String modTask;
    private String modTime;

    private int I;
    private int posDay;
    private String task;
    private String strTime;

    private EditText editTask;
    private EditText editTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        posDay = intent.getIntExtra(MainActivity.POS_DAY, 0);
        I = intent.getIntExtra(MainActivity.MOD_I, 0);
        modifying = intent.getBooleanExtra(MainActivity.MOD, false);
        modTask = intent.getStringExtra(MainActivity.MOD_TASK);
        modTime = intent.getStringExtra(MainActivity.MOD_TIME);
        adding = !modifying;

        Button addTask = findViewById(R.id.addTask);
        editTask = findViewById(R.id.editTask);
        editTime = findViewById(R.id.editTime);

        if(editTask.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        if (modifying)
        {
            editTask.setText(modTask);
            editTime.setText(modTime.substring(0, 5));
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
                    strTime = editTime.getText().toString();
                    if (!strTime.contains(":"))
                    {
                        strTime += ":00";
                    }

                    launchFirstActivity();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        modifying = false;
        adding = false;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_BOOLEAN, adding);
        intent.putExtra(EXTRA_BOOLEAN2, modifying);
        intent.putExtra(EXTRA_NUMBER, posDay);
        startActivity(intent);
    }

    public void launchFirstActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_BOOLEAN, adding);
        intent.putExtra(EXTRA_BOOLEAN2, modifying);
        intent.putExtra(EXTRA_NUMBER, posDay);
        intent.putExtra(EXTRA_NUMBER2, I);
        intent.putExtra(EXTRA_STRING, task);
        intent.putExtra(EXTRA_TIME, strTime);
        startActivity(intent);
        finish();
    }
}