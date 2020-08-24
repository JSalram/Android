package com.example.weekly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
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
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static int posDay;
    public static WeeklyDays weeklyDays;

    private TextView day;
    public static ScrollView scroller;
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
        add = findViewById(R.id.add);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        scroller = findViewById(R.id.tasks);
        taskList = findViewById(R.id.ll);

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
                reloadTasks(getApplicationContext());
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
                reloadTasks(getApplicationContext());
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
        for (int i = 0; i < taskList.getChildCount(); i++)
        {
            View v = taskList.getChildAt(i);
            if (((CheckBox) v).isChecked())
            {
                taskList.removeViewAt(i);
                weeklyDays.days[posDay].tasks.remove(i);
                weeklyDays.days[posDay].time.remove(i);
                i--;
            }
        }
    }
    public void focusToday()
    {
        if (posDay == 0)
        {
            day.setTypeface(null, Typeface.BOLD);
            day.setBackground(getDrawable(R.drawable.rounded_today));
        }
        else
        {
            day.setTypeface(null, Typeface.NORMAL);
            day.setBackground(getDrawable(R.drawable.rounded_day));
        }
    }


    //// STATICS ////
    public static void reloadTasks(Context context)
    {
        taskList.removeAllViews();
        sortTasks();

        if (weeklyDays.days[posDay].tasks.size() > 0)
        {
            for (int i = 0; i < weeklyDays.days[posDay].tasks.size(); i++)
            {
                CheckBox cb = new CheckBox(context);
                cb.setTextSize(28);
                cb.setPadding(10, 12, 0, 12);
                cb.setTextColor(Color.parseColor("#028090"));
                String newTask = "";

                Time time = weeklyDays.days[posDay].time.get(i);
                String task = weeklyDays.days[posDay].tasks.get(i);

                newTask += time.toString().substring(0, 5) + " - " + task;
                cb.setText(newTask);

                String arrayDay = weeklyDays.days[posDay].day.getTime().toString().substring(8, 10);
                String actualDay = Calendar.getInstance().getTime().toString().substring(8, 10);
                Time actualTime = Time.valueOf(Calendar.getInstance().getTime().toString().substring(11, 19));
                if (arrayDay.equals(actualDay) && time.compareTo(actualTime) < 0)
                {
                    cb.setChecked(true);
                }

                taskList.addView(cb);
            }
        }
        else
        {
            TextView tv = new TextView(context);
            tv.setText("No tienes recordatorios aún.");
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(25);
            tv.setTextColor(Color.parseColor("#028090"));

            tv.setPadding(0, 300, 0, 0);

            taskList.addView(tv);
        }
    }
    public static void sortTasks()
    {
        List<String> tasks = weeklyDays.days[posDay].tasks;
        List<Time> time = weeklyDays.days[posDay].time;

        int i, j, k;
        for (i = 0; i < tasks.size(); i++)
        {
            for (j = 0; j < tasks.size()-1; j++)
            {
                k = j + 1;
                if (time.get(j).compareTo(time.get(k)) > 0)
                {
                    Time timeTemp = time.get(j);
                    String taskTemp = tasks.get(j);
                    time.set(j, time.get(k));
                    tasks.set(j, tasks.get(k));
                    time.set(k, timeTemp);
                    tasks.set(k, taskTemp);
                }
            }
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
                Time time = weeklyDays.days[i].time.get(j);
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
                                Time time = Time.valueOf(strings[1]);
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
    public static void addTask(Time t, String s)
    {
        weeklyDays.days[posDay].addTask(t, s);
    }


    //// NOTIFICATIONS ////
    /*
    public void notification()
    {
        new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notificación")
                .setContentText("Esta es una notificación de prueba")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    */
}