package com.example.conversor;

import java.security.InvalidParameterException;

enum TipoMoneda
{
    euro, dolar, yen, libra, won
}

public class Moneda
{
    TipoMoneda tMoneda;
    private int decimales;
    private String simbolo;
    private double cambioEuro;
    private String codigo;

    public Moneda(TipoMoneda tMoneda, int decimales, String simbolo, double cambioEuro, String codigo)
    {
        this.tMoneda = tMoneda;
        if (decimales >= 0 && decimales <= 4)
        {
            this.decimales = decimales;
        }
        else
        {
            throw new InvalidParameterException("Los decimales deben ser mayores que 0 y menores que 4");
        }
        if (!simbolo.equals(""))
        {
            this.simbolo = simbolo;
        }
        else
        {
            throw new InvalidParameterException("El símbolo no puede estar vacío");
        }
        if (cambioEuro >= 0.0)
        {
            this.cambioEuro = cambioEuro;
        }
        else
        {
            throw new InvalidParameterException("El cambio a euros no puede ser negativo");
        }
        this.codigo = codigo;
    }

    public int getDecimales() {return decimales;}
    public String getSimbolo() {return simbolo;}
    public String getCodigo() {return codigo;}

    public double getCambioEuro() {return cambioEuro;}
    public void setCambioEuro(double cambioEuro)
    {
        if (cambioEuro >= 0.0)
        {
            this.cambioEuro = cambioEuro;
        }
    }
}
