package com.salram.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private double saldoTarjeta;
    private boolean recargando;

    private EditText dineroRecarga;
    private Button viaje;
    private Button recarga;
    private TextView titulo;
    private TextView saldo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();
    }

    public void inicializar()
    {
        dineroRecarga = findViewById(R.id.dineroRecarga);
        viaje = findViewById(R.id.viaje);
        recarga = findViewById(R.id.recarga);
        titulo = findViewById(R.id.titulo);
        saldo = findViewById(R.id.saldo);

        recargando = false;

        viaje.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viaje();
            }
        });
        recarga.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                recarga();
            }
        });
    }
    public void viaje()
    {
        if (saldoTarjeta >= 0.7)
        {
            saldoTarjeta -= 0.7;
            saldo.setText(String.valueOf(saldoTarjeta));
        }
    }
    public void recarga()
    {
        if (recargando)
        {
            String recargastr = dineroRecarga.getText().toString();
            if (!recargastr.equals(""))
            {
                double recarga = Double.parseDouble(recargastr);
                saldoTarjeta += recarga;
                saldo.setText(String.valueOf(saldoTarjeta));            }
        }
        else
        {
            viaje.setVisibility(View.GONE);
            dineroRecarga.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (recargando)
        {
            dineroRecarga.setVisibility(View.GONE);
            viaje.setVisibility(View.VISIBLE);
        }
        super.onBackPressed();
    }
}