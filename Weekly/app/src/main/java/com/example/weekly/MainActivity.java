package com.example.weekly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    public static int posDay;
    public static WeeklyDays weeklyDays;

    private TextView day;
    private ScrollView scroller;
    public static LinearLayout taskList;
    private ImageButton add;
    private ImageButton prev;
    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start();

        buttons();
    }


    //// FUNCTIONS ////
    public void start()
    {
        weeklyDays = new WeeklyDays();

        day = findViewById(R.id.day);
        scroller = findViewById(R.id.tasks);
        taskList = findViewById(R.id.ll);
        add = findViewById(R.id.add);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);

        day.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        focusToday();

        posDay = 0;

        leeFichero(getApplicationContext());
        day.setText(weeklyDays.getDay(posDay));
        reloadTasks(getApplicationContext());
    }
    public void buttons()
    {
        // ADD
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                launchSecondActivity();
            }
        });

        add.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                removeTasks();
                reloadTasks(getApplicationContext());
                escribeFichero(getApplicationContext());
                return true;
            }
        });

        // NEXT
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (posDay < weeklyDays.days.length-1)
                {
                    posDay++;
                }
                focusToday();
                reloadTasks(getApplicationContext());
                day.setText(weeklyDays.getDay(posDay));
            }
        });

        next.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                posDay = weeklyDays.days.length-1;
                focusToday();
                day.setText(weeklyDays.getDay(posDay));
                return true;
            }
        });

        // PREV
        prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (posDay > 0)
                {
                    posDay--;
                }
                focusToday();
                reloadTasks(getApplicationContext());
                day.setText(weeklyDays.getDay(posDay));
            }
        });

        prev.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                posDay = 0;
                focusToday();
                day.setText(weeklyDays.getDay(posDay));
                return true;
            }
        });
    }
    public void launchSecondActivity()
    {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }
    public void removeTasks()
    {
        while (weeklyDays.days[posDay].tasks.size() > 0)
        {
            weeklyDays.days[posDay].tasks.remove(0);
            weeklyDays.days[posDay].time.remove(0);
        }
    }
    public void focusToday()
    {
        if (posDay == 0)
        {
            day.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            day.setTypeface(null, Typeface.NORMAL);
        }
    }
    public void notification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Notificación")
                .setContentText("Esta es una notificación de prueba")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }


    //// STATICS ////
    public static void reloadTasks(Context context)
    {
        taskList.removeAllViews();
        for (int i = 0; i < weeklyDays.days[posDay].tasks.size(); i++)
        {
            CheckBox cb = new CheckBox(context);
            cb.setTextSize(25);
            cb.setPadding(25, 10, 0, 10);
            String newTask = "";
            if (weeklyDays.days[posDay].time.get(i) < 10)
            {
                newTask += "0";
            }
            newTask += weeklyDays.days[posDay].time.get(i) + ":00 - " + weeklyDays.days[posDay].tasks.get(i);
            cb.setText(newTask);
            taskList.addView(cb);
        }
    }
    public static void escribeFichero(Context context)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < weeklyDays.days.length; i++)
        {
            for (int j = 0; j < weeklyDays.days[i].tasks.size(); j++)
            {
                String day = weeklyDays.days[i].day.getTime().toString().substring(0, 10);
                int time = weeklyDays.days[i].time.get(j);
                String task = weeklyDays.days[i].tasks.get(j);
                s.append(day).append(";");
                s.append(time).append(";");
                s.append(task).append(System.lineSeparator());
            }
        }

        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("tasks.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(s.toString());
            outputStreamWriter.close();
        }
        catch (IOException ignored) {}
    }
    private void leeFichero(Context context)
    {
        try
        {
            InputStream inputStream = context.openFileInput("tasks.txt");

            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;

                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    if (!receiveString.equals("") && receiveString.contains(";"))
                    {
                        String[] strings = receiveString.split(";");
                        for (int i = 0; i < weeklyDays.days.length; i++)
                        {
                            String date = weeklyDays.days[i].day.getTime().toString().substring(0, 10);
                            if (strings[0].equals(date))
                            {
                                int time = Integer.parseInt(strings[1]);
                                String task = strings[2];
                                weeklyDays.days[i].addTask(time, task);
                            }
                        }
                    }
                }

                bufferedReader.close();
                inputStream.close();
            }
        }
        catch (IOException ignored) {}
    }
    public static void addTask(int n, String s)
    {
        weeklyDays.days[posDay].addTask(n, s);
    }
}