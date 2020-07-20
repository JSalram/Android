package com.example.tresenraya;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TresEnRaya
{
    public int[] tablero;

    public TresEnRaya()
    {
        tablero = new int[9];
        this.iniciar();
    }

    public void mueveJugador1(int pos)
    {
        if (pos >= 0  && pos < 9)
        {
            tablero[pos] = 1;
        }
        else
        {
            throw new InvalidParameterException("Posición inválida");
        }
    }
    public void mueveJugador2(int pos)
    {
        if (pos >= 0  && pos < 9)
        {
            tablero[pos] = 2;
        }
        else
        {
            throw new InvalidParameterException("Posición inválida");
        }
    }
    public boolean movimientoValido (int pos)
    {return tablero[pos] == 0;}
    public void iniciar ()
    {
        int i;
        for (i = 0; i < tablero.length; i++)
        {
            tablero[i] = 0;
        }
    }
    public boolean quedanMovimientos ()
    {
        boolean quedan = false;
        int i;
        for (i = 0; i < tablero.length; i++)
        {
            if (tablero[i] == 0)
            {
                quedan = true;
            }
        }
        return quedan;
    }
    public boolean ganaJugador1 ()
    {
        boolean ganap1 = false;
        int i;
        for (i = 0; i < 9; i++)
        {
            if (tablero[i] == 1)
            {
                if ((i == 0 || i == 3 || i == 6) &&
                        tablero[i] == tablero[i + 1] &&
                        tablero[i] == tablero[i + 2])
                {
                    ganap1 = true;
                }
                if ((i == 0 || i == 1 || i == 2) &&
                        tablero[i] == tablero[i + 3] &&
                        tablero[i] == tablero[i + 6])
                {
                    ganap1 = true;
                }
                if (i == 0 && tablero[i] == tablero[i + 4]
                        && tablero[i] == tablero[i + 8])
                {
                    ganap1 = true;
                }
                if (i == 2 && tablero[i] == tablero[i + 2]
                        && tablero[i] == tablero[i + 4])
                {
                    ganap1 = true;
                }
            }
        }
        return ganap1;
    }
    public boolean ganaJugador1(int[] tablero)
    {
        boolean ganap1 = false;
        int i;
        for (i = 0; i < 9; i++)
        {
            if (tablero[i] == 1)
            {
                if ((i == 0 || i == 3 || i == 6) &&
                        tablero[i] == tablero[i + 1] &&
                        tablero[i] == tablero[i + 2])
                {
                    ganap1 = true;
                }
                if ((i == 0 || i == 1 || i == 2) &&
                        tablero[i] == tablero[i + 3] &&
                        tablero[i] == tablero[i + 6])
                {
                    ganap1 = true;
                }
                if (i == 0 && tablero[i] == tablero[i + 4]
                        && tablero[i] == tablero[i + 8])
                {
                    ganap1 = true;
                }
                if (i == 2 && tablero[i] == tablero[i + 2]
                        && tablero[i] == tablero[i + 4])
                {
                    ganap1 = true;
                }
            }
        }
        return ganap1;
    }
    public boolean ganaJugador2 ()
    {
        boolean ganap2 = false;
        int i;
        for (i = 0; i < 9; i++)
        {
            if (tablero[i] == 2)
            {
                if ((i == 0 || i == 3 || i == 6) &&
                        tablero[i] == tablero[i + 1] &&
                        tablero[i] == tablero[i + 2])
                {
                    ganap2 = true;
                }
                if ((i == 0 || i == 1 || i == 2) &&
                        tablero[i] == tablero[i + 3] &&
                        tablero[i] == tablero[i + 6])
                {
                    ganap2 = true;
                }
                if (i == 0 && tablero[i] == tablero[i + 4]
                        && tablero[i] == tablero[i + 8])
                {
                    ganap2 = true;
                }
                if (i == 2 && tablero[i] == tablero[i + 2]
                        && tablero[i] == tablero[i + 4])
                {
                    ganap2 = true;
                }
            }
        }
        return ganap2;
    }
    public boolean ganaJugador2(int[] tablero)
    {
        boolean ganap2 = false;
        int i;
        for (i = 0; i < 9; i++)
        {
            if (tablero[i] == 2)
            {
                if ((i == 0 || i == 3 || i == 6) &&
                        tablero[i] == tablero[i + 1] &&
                        tablero[i] == tablero[i + 2])
                {
                    ganap2 = true;
                }
                if ((i == 0 || i == 1 || i == 2) &&
                        tablero[i] == tablero[i + 3] &&
                        tablero[i] == tablero[i + 6])
                {
                    ganap2 = true;
                }
                if (i == 0 && tablero[i] == tablero[i + 4]
                        && tablero[i] == tablero[i + 8])
                {
                    ganap2 = true;
                }
                if (i == 2 && tablero[i] == tablero[i + 2]
                        && tablero[i] == tablero[i + 4])
                {
                    ganap2 = true;
                }
            }
        }
        return ganap2;
    }
    public int IA2Bloquea()
    {
        int i, pos = 0;
        for (i = 0; i < tablero.length; i++)
        {
            int[] copia = copiaTablero();
            if (movimientoValido(i))
            {
                copia[i] = 1;
                if (ganaJugador1(copia))
                {
                    pos = i;
                    tablero[i] = 2;
                    i = tablero.length;
                }
            }
        }
        return pos;
    }
    public int IA2Gana()
    {
        int i, pos = 0;
        for (i = 0; i < tablero.length; i++)
        {
            int[] copia = copiaTablero();
            if (movimientoValido(i))
            {
                copia[i] = 2;
                if (ganaJugador2(copia))
                {
                    pos = i;
                    tablero[i] = 2;
                    i = tablero.length;
                }
            }
        }
        return pos;
    }
    public int[] copiaTablero ()
    {
        int[] copia = new int[tablero.length];
        int i;
        for (i = 0; i < tablero.length; i++)
        {
            copia[i] = tablero[i];
        }
        return copia;
    }
}
