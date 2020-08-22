package com.example.weekly;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Day
{
    public Calendar day;
    public List<Integer> time;
    public List<String> tasks;

    public Day()
    {
        day = Calendar.getInstance();
        time = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public void addTask(int n, String s)
    {
        if (!s.equals("") && n >= 0 && n <= 23)
        {
            time.add(n);
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
