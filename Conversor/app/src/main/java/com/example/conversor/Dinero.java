package com.example.conversor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dinero
{
    static List<Moneda> listamonedas;
    private double cantidad;
    private TipoMoneda tMoneda;

    // STATIC
    static
    {
        listamonedas = new ArrayList<>();
        listamonedas.add(new Moneda(TipoMoneda.euro, 3, "€", 1.0, "EUR"));
        listamonedas.add(new Moneda(TipoMoneda.dolar, 2, "$", 1.14, "USD"));
        listamonedas.add(new Moneda(TipoMoneda.yen, 0, "¥", 118.81, "JPY"));
        listamonedas.add(new Moneda(TipoMoneda.libra, 2, "£", 0.87, "GBP"));
        listamonedas.add(new Moneda(TipoMoneda.won, 0, "₩", 1373.69, "KRW"));
    }

    // CONSTRUCTORES
    public Dinero(double cantidad, TipoMoneda tMoneda)
    {
        settMoneda(tMoneda);
        setCantidad(cantidad);
    }

    // PROPIEDADES
    public void setCantidad(double cantidad)
    {
        this.cantidad = cantidad;
    }
    public void settMoneda(TipoMoneda tMoneda)
    {
        this.tMoneda = tMoneda;
    }

    // MÉTODOS
    public String getSimbolo ()
    {
        if (tMoneda.ordinal() == 0)
        {
            return "€";
        }
        else if (tMoneda.ordinal() == 1)
        {
            return "$";
        }
        else if (tMoneda.ordinal() == 2)
        {
            return "¥";
        }
        else
        {
            return "£";
        }
    }
    private static Moneda buscaMoneda (TipoMoneda t) {return listamonedas.get(t.ordinal());}
    public Dinero convierteEn(TipoMoneda t)
    {
        double valor = this.cantidad / buscaMoneda(tMoneda).getCambioEuro();
        return new Dinero(valor * buscaMoneda(t).getCambioEuro(), t);
    }

    @Override
    public String toString()
    {
        double redondeo = 1.0;
        int i;
        for (i = 0; i < buscaMoneda(tMoneda).getDecimales(); i++)
        {
            redondeo *= 10.0;
        }

        cantidad = Math.round(cantidad * redondeo) / redondeo;

        if (buscaMoneda(tMoneda).getDecimales() == 0)
        {
            return (int)cantidad + buscaMoneda(tMoneda).getSimbolo();
        }
        else
        {
            return cantidad + buscaMoneda(tMoneda).getSimbolo();
        }
    }


    public static void actualizaListaInternet(double cambio, int i)
    {
        listamonedas.get(i).setCambioEuro(cambio);
    }
    public static String[] getCodigos()
    {
        String[] codigos = new String[listamonedas.size()-1];
        for (int i = 0; i < codigos.length; i++)
        {
            codigos[i] = listamonedas.get(i+1).getCodigo();
        }
        return codigos;
    }
}

