package com.example.conversor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    String[] codigos;

    Spinner de;
    Spinner a;
    Button conv;
    TextView text;
    EditText cantidad;
    Dinero d;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        de = findViewById(R.id.de);
        a = findViewById(R.id.a);
        conv = findViewById(R.id.conv);
        text = findViewById(R.id.text);
        cantidad = findViewById(R.id.cantidad);
        codigos = Dinero.getCodigos();

        actualizaCambio();

        conv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                convertir();
            }
        });
    }

    public void convertir()
    {
        if (!cantidad.getText().toString().equals(""))
        {
            text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            switch (de.getSelectedItemPosition())
            {
                case 0:
                    d = new Dinero(Double.parseDouble(cantidad.getText().toString()), TipoMoneda.euro);
                    break;
                case 1:
                    d = new Dinero(Double.parseDouble(cantidad.getText().toString()), TipoMoneda.dolar);
                    break;
                case 2:
                    d = new Dinero(Double.parseDouble(cantidad.getText().toString()), TipoMoneda.yen);
                    break;
                case 3:
                    d = new Dinero(Double.parseDouble(cantidad.getText().toString()), TipoMoneda.libra);
                    break;
                case 4:
                    d = new Dinero(Double.parseDouble(cantidad.getText().toString()), TipoMoneda.won);
                    break;
            }

            switch (a.getSelectedItemPosition())
            {
                case 0:
                {
                    String conv = d.getSimbolo() + " = " + d.convierteEn(TipoMoneda.euro).toString();
                    text.setText(conv);
                }
                    break;
                case 1:
                {
                    String conv = d.getSimbolo() + " = " + d.convierteEn(TipoMoneda.dolar).toString();
                    text.setText(conv);
                }
                    break;
                case 2:
                {
                    String conv = d.getSimbolo() + " = " + d.convierteEn(TipoMoneda.yen).toString();
                    text.setText(conv);
                }
                    break;
                case 3:
                {
                    String conv = d.getSimbolo() + " = " + d.convierteEn(TipoMoneda.libra).toString();
                    text.setText(conv);
                }
                    break;
                case 4:
                {
                    String conv = d.getSimbolo() + " = " + d.convierteEn(TipoMoneda.won).toString();
                    text.setText(conv);
                }
                    break;
            }
        }
    }
    public void actualizaCambio()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    int i = 1;
                    for (String codigo : codigos)
                    {
                        double cambio = 0.0;
                        URL url = new URL("https://api.exchangeratesapi.io/latest?symbols=" + codigo);

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(60000);

                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        String page = in.readLine();
                        cambio += Double.parseDouble(page.substring(16, page.indexOf('}')));
                        Dinero.actualizaListaInternet(cambio, i);

                        in.close();
                        i++;
                    }
                }
                catch (Exception e)
                {
                    Log.d("MyTag", e.toString());
                }
            }
        }).start();
    }
}