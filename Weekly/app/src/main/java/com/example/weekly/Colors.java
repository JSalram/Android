package com.example.weekly;

import android.graphics.Color;

import java.sql.Time;
import java.util.Calendar;

public class Colors
{
    public static int bgColor;
    public static int tabColor;
    public static int addColor;
    public static int arrowColor;
    public static int todayColor;
    public static int dayColor;
    public static int tasksColor;

    static
    {
        bgColor = Color.parseColor("#F3F6CD");
        tabColor = Color.parseColor("#055D80");
        addColor = Color.parseColor("#00A896");
        arrowColor = Color.parseColor("#02C39A");
        todayColor = Color.parseColor("#05668D");
        dayColor = Color.parseColor("#028090");
        tasksColor = Color.parseColor("#028090");
        darkMode();
    }

    public static void darkMode()
    {
        Time actualTime = Time.valueOf(Calendar.getInstance().getTime().toString().substring(11, 19));
        if (actualTime.compareTo(Time.valueOf("21:00:00")) > 0)
        {
            arrowColor = Color.parseColor("#00665C");
            addColor = Color.parseColor("#01795F");
            bgColor = Color.parseColor("#1A2019");
            tasksColor = Color.parseColor("#B2F0E8");
        }
    }
}
