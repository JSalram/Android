package com.example.weekly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TaskActivity extends AppCompatActivity
{
    // Activity
    public static final String ADD = "ADD";
    public static final String MOD = "MOD";
    public static final String POS_DAY = "POS_DAY";
    public static final String MOD_I = "MOD_I";
    public static final String TASK = "TASK";
    public static final String TIME = "TIME";
    private boolean adding;
    private boolean modifying;

    // Variables
    private int I;
    private int posDay;
    private String task;
    private String strTime;

    // Layout
    private EditText editTask;
    private EditText editTime;
    private Button addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        start();

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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ADD, adding);
        intent.putExtra(MOD, modifying);
        intent.putExtra(POS_DAY, posDay);
        startActivity(intent);
        finish();
    }


    //// FUNCTIONS ////
    private void start()
    {
        Intent intent = getIntent();
        posDay = intent.getIntExtra(MainActivity.POS_DAY, 0);
        I = intent.getIntExtra(MainActivity.MOD_I, 0);
        modifying = intent.getBooleanExtra(MainActivity.MOD, false);
        String modTask = intent.getStringExtra(MainActivity.MOD_TASK);
        String modTime = intent.getStringExtra(MainActivity.MOD_TIME);
        adding = !modifying;

        addTask = findViewById(R.id.addTask);
        editTask = findViewById(R.id.editTask);
        editTime = findViewById(R.id.editTime);

        colorize();

        if(editTask.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        if (modifying)
        {
            if (modTime != null)
            {
                editTask.setText(modTask);
                editTime.setText(modTime.substring(0, 5));
            }
        }
    }
    private void colorize()
    {
        ConstraintLayout background = findViewById(R.id.TaskActivityLayout);
        TextView taskView = findViewById(R.id.taskTitle);
        TextView timeView = findViewById(R.id.timeTitle);

        background.setBackgroundColor(Colors.bgColor);
        taskView.setTextColor(Colors.dayColor);
        timeView.setTextColor(Colors.dayColor);
        editTask.setTextColor(Colors.tasksColor);
        editTask.getBackground().setTint(Colors.dayColor);
        editTime.setTextColor(Colors.tasksColor);
        editTime.getBackground().setTint(Colors.dayColor);
        addTask.getBackground().setTint(Colors.addColor);
    }
    public void launchFirstActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ADD, adding);
        intent.putExtra(MOD, modifying);
        intent.putExtra(POS_DAY, posDay);
        intent.putExtra(MOD_I, I);
        intent.putExtra(TASK, task);
        intent.putExtra(TIME, strTime);
        startActivity(intent);
        finish();
    }
}