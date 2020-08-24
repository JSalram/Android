package com.example.weekly;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeeklyDays
{
    public Day[] days;

    public WeeklyDays()
    {
        days = new Day[15];
        addDays();
    }

    public void addDays()
    {
        for (int i = 0; i < days.length; i++)
        {
            days[i] = new Day();
            days[i].day.add(Calendar.DATE, i);
        }
    }

    public void addTasks(int i, Time time, String task)
    {
        days[i].addTask(time, task);
    }

    public String getDay(int posDay)
    {
        SimpleDateFormat df = new SimpleDateFormat("EEEE dd 'de' MMM", Locale.getDefault());

        return Day.capitalizeDate(df.format(days[posDay].day.getTime()));
    }
}
