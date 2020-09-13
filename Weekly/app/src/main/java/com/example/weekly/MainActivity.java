package com.example.weekly;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener
{
    public static final String EXTRA_NUMBER = "com.example.weekly.MainActivity.EXTRA_NUMBER";
    public static final String EXTRA_NUMBER2 = "com.example.weekly.MainActivity.EXTRA_NUMBER2";
    public static final String EXTRA_BOOLEAN = "com.example.weekly.MainActivity.EXTRA_BOOLEAN";
    private int I;
    private boolean modifying;

    public NotificationManagerCompat notificationManager;

    public int posDay;
    public WeeklyDays weeklyDays;
    public Day actualDay;

    private TextView day;
    public ScrollView scroller;
    public LinearLayout taskList;
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
        Intent intent = getIntent();
        boolean adding = intent.getBooleanExtra(TaskActivity.EXTRA_BOOLEAN, false);
        modifying = intent.getBooleanExtra(TaskActivity.EXTRA_BOOLEAN2, false);
        posDay = intent.getIntExtra(TaskActivity.EXTRA_NUMBER, 0);
        I = intent.getIntExtra(TaskActivity.EXTRA_NUMBER2, 0);
        String task = intent.getStringExtra(TaskActivity.EXTRA_STRING);
        String strTime = intent.getStringExtra(TaskActivity.EXTRA_TIME);

        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);

        weeklyDays = new WeeklyDays();
        actualDay = weeklyDays.days[posDay];

        day = findViewById(R.id.day);
        add = findViewById(R.id.add);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        scroller = findViewById(R.id.tasks);
        taskList = findViewById(R.id.ll);

        focusToday();
        readFile();
        day.setText(weeklyDays.getDay(posDay));

        if (adding) {addTask(Time.valueOf(strTime + ":00"), task);}
        if (modifying) {modifyTask(I, task, strTime);}
        reloadTasks();
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
                reloadTasks();
                writeFile();
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
                    actualDay = weeklyDays.days[posDay];
                }
                focusToday();
                reloadTasks();
                day.setText(weeklyDays.getDay(posDay));
            }
        });

        next.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                posDay = weeklyDays.days.length-1;
                actualDay = weeklyDays.days[posDay];
                focusToday();
                day.setText(weeklyDays.getDay(posDay));
                reloadTasks();
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
                    actualDay = weeklyDays.days[posDay];
                }
                focusToday();
                reloadTasks();
                day.setText(weeklyDays.getDay(posDay));
            }
        });

        prev.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                posDay = 0;
                actualDay = weeklyDays.days[posDay];
                focusToday();
                day.setText(weeklyDays.getDay(posDay));
                reloadTasks();
                return true;
            }
        });
    }
    public void focusToday()
    {
        if (posDay == 0)
        {
            day.setBackgroundColor(getResources().getColor(R.color.todayColor));
        }
        else
        {
            day.setBackgroundColor(getResources().getColor(R.color.dayColor));
        }
    }

    //// TASKER ////
    public void reloadTasks()
    {
        boolean notified = false;
        Typeface tasksFont = ResourcesCompat.getFont(this, R.font.cabin);
        taskList.removeAllViews();
        sortTasks();

        if (actualDay.tasks.size() > 0)
        {
            for (int i = 0; i < actualDay.tasks.size(); i++)
            {
                final CheckBox cb = new CheckBox(this);
                cb.setTypeface(tasksFont);
                cb.setTextSize(30);
                cb.setPadding(10, 5, 0, 12);
                cb.setTextColor(Color.parseColor("#028090"));
                String newTask = "";

                Time time = actualDay.time.get(i);
                String task = actualDay.tasks.get(i);

                newTask += time.toString().substring(0, 5) + " - " + task;
                cb.setText(newTask);

                Time actualTime = Time.valueOf(Calendar.getInstance().getTime().toString().substring(11, 19));
                if (posDay == 0 && time.compareTo(actualTime) < 0)
                {
                    cb.setChecked(true);
                }
                else if (posDay == 0 && !notified)
                {
                    NotificationCompat.Builder builder = createNotification(newTask, this);
                    notificationManager.notify(100, builder.build());
                    notified = true;
                }

                final int finalI = i;
                cb.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View view)
                    {
                        I = finalI;
                        showPopup(cb);
                        return true;
                    }
                });

                taskList.addView(cb);
            }
        }
        else
        {
            TextView tv = new TextView(this);
            tv.setTypeface(tasksFont);
            tv.setText(R.string.noTasks);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(35);
            tv.setTextColor(Color.parseColor("#028090"));

            tv.setPadding(0, 250, 0, 0);

            taskList.addView(tv);
        }
    }
    public void sortTasks()
    {
        List<String> tasks = actualDay.tasks;
        List<Time> time = actualDay.time;

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
    public void addTask(Time t, String s)
    {
        actualDay.addTask(t, s);
        writeFile();
    }
    public void removeTasks()
    {
        for (int i = 0; i < taskList.getChildCount(); i++)
        {
            View v = taskList.getChildAt(i);
            if (((CheckBox) v).isChecked())
            {
                taskList.removeViewAt(i);
                actualDay.tasks.remove(i);
                actualDay.time.remove(i);
                i--;
            }
        }
    }
    public void removeTask(int i)
    {
        actualDay.tasks.remove(i);
        actualDay.time.remove(i);
        reloadTasks();
        writeFile();

        Toast t = Toast.makeText(this, "Recordatorio eliminado", Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, taskList.getWidth()/2);
        t.show();
    }
    public void modifyTask(int i, String task, String strTime)
    {
        System.out.println(i);
        actualDay.tasks.set(i, task);
        actualDay.time.set(i, Time.valueOf(strTime + ":00"));
        modifying = false;
        writeFile();
    }

    //// MENU ////
    public void showPopup(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.task_menu, popup.getMenu());
        popup.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.modify:
            {
                modifying = true;
                launchSecondActivity();
                return true;
            }
            case R.id.remove:
            {
                removeTask(I);
                return true;
            }
            default:
                return false;
        }
    }

    //// FILES ////
    public void writeFile()
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
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("tasks.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(s.toString());
            outputStreamWriter.close();
        }
        catch (IOException ignored) {}
    }
    private void readFile()
    {
        try
        {
            InputStream inputStream = this.openFileInput("tasks.txt");

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

    //// NOTIFICATIONS ////
    public NotificationCompat.Builder createNotification(String taskNot, Context context)
    {
        return
        new NotificationCompat.Builder(context, "weeklyID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Próxima tarea")
                .setContentText(taskNot)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
    }
    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("weeklyID", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //// ACTIVITIES ////
    public void launchSecondActivity()
    {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(EXTRA_NUMBER, posDay);
        intent.putExtra(EXTRA_NUMBER2, I);
        intent.putExtra(EXTRA_BOOLEAN, modifying);
        startActivity(intent);
        finish();
    }
}