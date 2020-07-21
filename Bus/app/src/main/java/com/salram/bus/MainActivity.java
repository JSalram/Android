package com.salram.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity
{
    private double saldoTarjeta;
    private boolean recargando;

    private EditText dineroRecarga;
    private Button viaje;
    private Button recarga;
    private TextView titulo;
    private TextView saldo;
    private TextView viajesRestantes;


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
        viajesRestantes = findViewById(R.id.viajesRestantes);

        recargando = false;

        viaje.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viaje();
                viajesRestantes();
            }
        });
        viaje.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                saldoTarjeta = 0;
                saldo.setText(String.valueOf((int)saldoTarjeta));
                return false;
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
            saldoTarjeta = BigDecimal.valueOf(saldoTarjeta)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            saldo.setText(String.valueOf(saldoTarjeta));
        }
        else
        {
            saldoTarjeta = 0;
            saldo.setText(String.valueOf((int)saldoTarjeta));
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
                saldo.setText(String.valueOf(saldoTarjeta));
                dineroRecarga.setText("");
            }
        }
        else
        {
            viaje.setVisibility(View.GONE);
            dineroRecarga.setVisibility(View.VISIBLE);
            recargando = true;
        }
    }
    public void viajesRestantes()
    {
        if (saldoTarjeta > 0)
        {
            double viajes = saldoTarjeta / 0.7;
            int restantes = (int)viajes;
            viajesRestantes.setText("Viajes restantes: " + restantes);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (recargando)
        {
            dineroRecarga.setVisibility(View.GONE);
            viaje.setVisibility(View.VISIBLE);
            recargando = false;
        }
        else
        {
            super.onBackPressed();
        }
    }
}