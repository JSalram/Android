package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Button button1;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        textView1 = findViewById(R.id.textView1);

        button1.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v) {
                textView1.setText(R.string.gritono);
                return false; // true = no se produce el click normal, false = se produce
            }
        });
    }

    public void button1_onClick (View v)
    {
        if (textView1.getText().length() == 0)
        {
            textView1.setText(R.string.holamundo);
        }
        else
        {
            textView1.setText("");
        }
    }
}
