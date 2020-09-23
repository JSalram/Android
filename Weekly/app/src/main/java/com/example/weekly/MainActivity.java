package com.example.weekly;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    // Activity
    public static final String MOD_TASK = "MOD_TASK";
    public static final String MOD_TIME = "MOD_TIME";
    public static final String POS_DAY = "POS_DAY";
    public static final String MOD_I = "MOD_I";
    public static final String MOD = "MOD";
    private int I;
    private boolean modifying;
    private String task;
    private String time;

    // Notification
    private NotificationManagerCompat notificationManager;
    private String taskNot;

    // Variables
    private int posDay;
    private WeeklyDays weeklyDays;
    private Day actualDay;

    // Layout
    private ConstraintLayout background;
    private TextView day;
    private LinearLayout taskList;
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
        boolean adding = intent.getBooleanExtra(TaskActivity.ADD, false);
        modifying = intent.getBooleanExtra(TaskActivity.MOD, false);
        posDay = intent.getIntExtra(TaskActivity.POS_DAY, 0);
        I = intent.getIntExtra(TaskActivity.MOD_I, 0);
        String task = intent.getStringExtra(TaskActivity.TASK);
        String strTime = intent.getStringExtra(TaskActivity.TIME);

        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);

        weeklyDays = new WeeklyDays();
        actualDay = weeklyDays.days[posDay];

        background = findViewById(R.id.ConstraintLayout);
        day = findViewById(R.id.day);
        add = findViewById(R.id.add);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        taskList = findViewById(R.id.ll);

        colorize();
        readFile();
        day.setText(weeklyDays.getDay(posDay));

        if (adding) {addTask(Time.valueOf(strTime + ":00"), task);}
        if (modifying) {modifyTask(I, task, strTime);}
        reloadTasks();
        notifyTask();
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
                colorize();
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
                colorize();
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
                colorize();
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
                colorize();
                day.setText(weeklyDays.getDay(posDay));
                reloadTasks();
                return true;
            }
        });
    }
    private void colorize()
    {
        background.setBackgroundColor(Colors.bgColor);
        add.getBackground().setTint(Colors.addColor);
//        add.setBackgroundColor(Colors.addColor);
        prev.getBackground().setTint(Colors.arrowColor);
//        prev.setBackgroundColor(Colors.arrowColor);
        next.getBackground().setTint(Colors.arrowColor);
//        next.setBackgroundColor(Colors.arrowColor);

        if (posDay == 0)
        {
            day.setBackgroundColor(Colors.todayColor);
        }
        else
        {
            day.setBackgroundColor(Colors.dayColor);
        }
    }

    //// TASKER ////
    private void reloadTasks()
    {
        Typeface tasksFont = ResourcesCompat.getFont(this, R.font.cabin);
        taskList.removeAllViews();
        sortTasks();

        if (actualDay.tasks.size() > 0)
        {
            for (int i = 0; i < actualDay.tasks.size(); i++)
            {
                final CheckBox cb = new CheckBox(this);
                cb.setTypeface(tasksFont);
                cb.setTextSize(35);
                cb.setPadding(10, 5, 0, 12);
                cb.setTextColor(Colors.tasksColor);
                cb.setButtonTintList(ColorStateList.valueOf(Colors.tasksColor));
                String newTask = "";

                Time time = actualDay.time.get(i);
                String task = actualDay.tasks.get(i);

                newTask += time.toString().substring(0, 5) + " - " + task;
                cb.setText(newTask);

                Time actualTime = Time.valueOf(Calendar.getInstance().getTime().toString().substring(11, 19));
                if (posDay == 0 && time.compareTo(actualTime) < 0)
                {
                    cb.setChecked(true);
                    cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
            tv.setTextSize(40);
            tv.setTextColor(Colors.tasksColor);

            tv.setPadding(0, 600, 0, 0);

            taskList.addView(tv);
        }
    }
    private void sortTasks()
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
    private void addTask(Time t, String s)
    {
        actualDay.addTask(t, s);
        writeFile();
    }
    private void removeTasks()
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
    private void removeTask(int i)
    {
        actualDay.tasks.remove(i);
        actualDay.time.remove(i);
        reloadTasks();
        writeFile();

        Toast t = Toast.makeText(this, "Recordatorio eliminado", Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, taskList.getWidth()/2);
        t.show();
    }
    private void modifyTask(int i, String task, String strTime)
    {
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
                task = actualDay.tasks.get(I);
                time = String.valueOf(actualDay.time.get(I));
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
    private void writeFile()
    {
        StringBuilder s = new StringBuilder();
        s.append(taskNot).append(System.lineSeparator());
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
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(this.openFileOutput("tasks.txt", Context.MODE_PRIVATE));
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

            if (inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;

                while ((receiveString = bufferedReader.readLine()) != null)
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
                    else
                    {
                        taskNot = receiveString;
                    }
                }

                bufferedReader.close();
                inputStream.close();
            }
        }
        catch (IOException ignored) {}
    }

    //// NOTIFICATIONS ////
    private NotificationCompat.Builder createNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "weeklyID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Próxima tarea")
                .setContentText(taskNot)
                .setSound(Uri.parse("android.resource://"
                        + this.getPackageName() + "/" + R.raw.sound))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);

        return builder;
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
    private void notifyTask()
    {
        if (posDay == 0 && actualDay.tasks.size() > 0)
        {
            for (int i = 0; i < taskList.getChildCount(); i++)
            {
                View v = taskList.getChildAt(i);
                if (!((CheckBox) v).isChecked())
                {
                    String savedTask = ((CheckBox) v).getText().toString();
                    Time savedTime = Time.valueOf(savedTask.substring(0, 5) + ":00");
                    savedTime.setHours(savedTime.getHours()-2);
                    savedTime.setMinutes(savedTime.getMinutes()-30);
                    Time now = Time.valueOf(Calendar.getInstance().getTime().toString().substring(11, 19));
                    if (!savedTask.equals(taskNot) && now.compareTo(savedTime) >= 0)
                    {
                        taskNot = savedTask;
                        NotificationCompat.Builder builder = createNotification();
                        notificationManager.notify(100, builder.build());
                        writeFile();
                    }
                    break;
                }
            }
        }
    }

    //// ACTIVITIES ////
    private void launchSecondActivity()
    {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(POS_DAY, posDay);
        intent.putExtra(MOD_I, I);
        intent.putExtra(MOD, modifying);
        intent.putExtra(MOD_TASK, task);
        intent.putExtra(MOD_TIME, time);
        startActivity(intent);
    }
}