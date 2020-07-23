package com.salram.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        leeFichero(getApplicationContext());

        viaje.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viaje();
            }
        });
        viaje.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                return borrar();
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
            viajesRestantes();
            escribeFichero(getApplicationContext());
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
                escribeFichero(getApplicationContext());
            }
        }
        else
        {
            viaje.setVisibility(View.GONE);
            dineroRecarga.setVisibility(View.VISIBLE);
            viajesRestantes.setVisibility(View.GONE);
            titulo.setText("Recarga");
            recargando = true;
        }
    }
    public boolean borrar()
    {
        saldoTarjeta = 0;
        saldo.setText(String.valueOf(saldoTarjeta));
        viajesRestantes.setText("Viajes restantes: 0");
        escribeFichero(getApplicationContext());
        return false;
    }
    public void viajesRestantes()
    {
        if (saldoTarjeta >= 0)
        {
            double viajes = saldoTarjeta / 0.7;
            int restantes = (int)viajes;
            viajesRestantes.setText("Viajes restantes: " + restantes);
        }
    }

    private void escribeFichero(Context context)
    {
        try
        {
            String dinero = String.valueOf(saldoTarjeta);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("saldoTarjeta.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(dinero);
            outputStreamWriter.close();
        }
        catch (IOException ignored) {}
    }
    private void leeFichero(Context context)
    {
        try
        {
            InputStream inputStream = context.openFileInput("saldoTarjeta.txt");

            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;

                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    saldoTarjeta += Double.parseDouble(receiveString);
                }

                bufferedReader.close();
                inputStream.close();

                saldo.setText(String.valueOf(saldoTarjeta));
                viajesRestantes();
            }
        }
        catch (IOException ignored) {}
    }

    @Override
    public void onBackPressed()
    {
        if (recargando)
        {
            dineroRecarga.setVisibility(View.GONE);
            viaje.setVisibility(View.VISIBLE);
            viajesRestantes.setVisibility(View.VISIBLE);
            titulo.setText(R.string.saldo);
            viajesRestantes();
            recargando = false;
        }
        else
        {
            super.onBackPressed();
        }
    }
}