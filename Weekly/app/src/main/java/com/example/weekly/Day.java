package com.example.weekly;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Day
{
    public Calendar day;
    public List<Time> time;
    public List<String> tasks;

    public Day()
    {
        day = Calendar.getInstance();
        time = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public void addTask(Time t, String s)
    {
        if (!s.equals("") && t.getTime() >= -3600000 && t.getTime() <= 82740000)
        {
            time.add(t);
            tasks.add(s);
        }
    }

    public static String capitalizeDate(String s)
    {
        String[] words = s.split(" ");
        words[0] = words[0].substring(0, 1).toUpperCase() + words[0].substring(1);
        words[3] = words[3].substring(0, 1).toUpperCase() + words[3].substring(1);

        StringBuilder date = new StringBuilder();

        for (int i = 0; i < words.length; i++)
        {
            date.append(words[i]);
            if (i < words.length-1)
            {
                date.append(" ");
            }
        }

        s = date.toString();

        return s;
    }
}
