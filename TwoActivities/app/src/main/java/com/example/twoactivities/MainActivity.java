package com.example.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    private Button enviar;
    private EditText mensaje;
    static String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enviar = findViewById(R.id.enviar);
        mensaje = findViewById(R.id.mensaje);

        enviar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                msg = mensaje.getText().toString();
                launchSecondActivity();
            }
        });
    }

    public void launchSecondActivity()
    {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public static String getMensaje() {return msg;}
}