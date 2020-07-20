package com.example.tresenraya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button reiniciar;
    TextView titulo;
    TextView player1;
    TextView player2;
    CheckBox ia;
    TresEnRaya ter = new TresEnRaya();
    int i; int j;
    TextView dificultad;
    Button facil;
    Button medio;
    Button dificil;
    boolean[] niveles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        titulo = findViewById(R.id.titulo);
        player1 = findViewById(R.id.p1);
        player2 = findViewById(R.id.p2);
        reiniciar = findViewById(R.id.reiniciar);
        ia = findViewById(R.id.ia);
        i = 0;
        j = 0;
        dificultad = findViewById(R.id.dificultad);
        facil = findViewById(R.id.facil);
        medio = findViewById(R.id.intermedio);
        dificil = findViewById(R.id.dificil);
        niveles = new boolean[3];

        botones();
        nivelDificultad();

        ia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ter.iniciar();
                reiniciar();
                muestraTitulo();
                titulo.setTextColor(Color.parseColor("#673AB7"));
                titulo.setText(R.string.raya3);
                if (!ia.isChecked())
                {
                    ocultarBotones();
                    mostrarNiveles();
                }
                else
                {
                    mostrarBotones();
                    ocultarNiveles();
                }
                i = 0; j = 0;
            }
        });

        reiniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ter.iniciar();
                reiniciar();
                if (i != 0 || j != 0)
                {
                    if (ia.isChecked())
                    {
                        contador(0, 0);
                        muestraCont();
                    }
                    else
                    {
                        contadorCPU(0, 0);
                        muestraCont();
                    }
                }
                else
                {
                    titulo.setText(R.string.raya3);
                }
            }
        });

        reiniciar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                i = 0; j = 0;
                ter.iniciar();
                reiniciar();
                muestraTitulo();
                titulo.setTextColor(Color.parseColor("#673AB7"));
                titulo.setText(R.string.raya3);
                reiniciarNiveles();
                if (!ia.isChecked())
                {
                    ocultarBotones();
                    mostrarNiveles();
                }
                return false;
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Instrucciones");
        alert.setMessage("Manteniendo pulsado el botón REINICIAR, puedes reiniciar el contador de victorias. \n" +
                            "Y si juegas contra la máquina, seleccionar el nivel de dificultad.");
        alert.setPositiveButton("Entendido",null);
        alert.show();
    }

    public void reiniciar()
    {
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
        b6.setEnabled(true);
        b7.setEnabled(true);
        b8.setEnabled(true);
        b9.setEnabled(true);

        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        b5.setText("");
        b6.setText("");
        b7.setText("");
        b8.setText("");
        b9.setText("");
    }
    public void fin()
    {
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);
        b7.setEnabled(false);
        b8.setEnabled(false);
        b9.setEnabled(false);
    }
    public String xo()
    {
        String xo;

        if (turno() % 2 == 0)
        {
            xo = "X";
        }
        else
        {
            xo = "O";
        }

        return xo;
    }
    public int turno()
    {
        int i = 0;
        if (!b1.getText().toString().equals(""))
        {
            i++;
        }
        if (!b2.getText().toString().equals(""))
        {
            i++;
        }
        if (!b3.getText().toString().equals(""))
        {
            i++;
        }
        if (!b4.getText().toString().equals(""))
        {
            i++;
        }
        if (!b5.getText().toString().equals(""))
        {
            i++;
        }
        if (!b6.getText().toString().equals(""))
        {
            i++;
        }
        if (!b7.getText().toString().equals(""))
        {
            i++;
        }
        if (!b8.getText().toString().equals(""))
        {
            i++;
        }
        if (!b9.getText().toString().equals(""))
        {
            i++;
        }

        return i;
    }
    public void contador(int p1, int p2)
    {
        i += p1; j += p2;
        String c1 = "P1: " + i;
        player1.setText(c1);
        String c2 = "P2: " + j;
        player2.setText(c2);
    }
    public void contadorCPU(int p1, int p2)
    {
        i += p1; j += p2;
        String c1 = "P1: " + i;
        player1.setText(c1);
        String c2 = "CPU: " + j;
        player2.setText(c2);
        titulo.setVisibility(View.INVISIBLE);
        player1.setVisibility(View.VISIBLE);
        player2.setVisibility(View.VISIBLE);
    }
    public void muestraCont()
    {
        titulo.setVisibility(View.INVISIBLE);
        player1.setVisibility(View.VISIBLE);
        player2.setVisibility(View.VISIBLE);
    }
    public void muestraTitulo()
    {
        player1.setVisibility(View.INVISIBLE);
        player2.setVisibility(View.INVISIBLE);
        titulo.setVisibility(View.VISIBLE);
    }
    public Button ia (int turno)
    {
        Button posicion = null;
        switch (dificultad())
        {
            case 0:
            {
                Button[] boton = {b1, b2, b3, b4, b5, b6, b7, b8, b9};
                Random r = new Random();
                int pos = r.nextInt(9);
                while (!ter.movimientoValido(pos))
                {
                    pos = r.nextInt(9);
                }
                ter.tablero[pos] = 2;
                posicion = boton[pos];
            }
                break;
            case 1:
            {
                Button[] boton = {b1, b2, b3, b4, b5, b6, b7, b8, b9};
                Random r = new Random();
                int[] copia = ter.copiaTablero();

                switch (turno)
                {
                    case 1:
                    {
                        int[] posInic = {0, 2, 4, 6, 8};
                        int pos = 2;
                        while (!ter.movimientoValido(posInic[pos]))
                        {
                            pos = r.nextInt(5);
                        }
                        ter.tablero[posInic[pos]] = 2;
                        posicion = boton[posInic[pos]];
                    }
                    break;
                    case 3:
                    case 5:
                    case 7:
                    {

                        posicion = boton[ter.IA2Bloquea()];
                        if (Arrays.equals(copia, ter.tablero))
                        {
                            int pos = r.nextInt(9);
                            while (!ter.movimientoValido(pos))
                            {
                                pos = r.nextInt(9);
                            }
                            ter.tablero[pos] = 2;
                            posicion = boton[pos];
                        }
                    }
                    break;
                    default:
                        throw new InvalidParameterException("Sin turnos");
                }
                return posicion;
            }
            case 2:
            {
                Button[] boton = {b1, b2, b3, b4, b5, b6, b7, b8, b9};
                Random r = new Random();
                int[] copia = ter.copiaTablero();

                switch (turno)
                {
                    case 1:
                    {
                        int[] posInic = {0, 2, 4, 6, 8};
                        int pos = 2;
                        while (!ter.movimientoValido(posInic[pos]))
                        {
                            pos = r.nextInt(5);
                        }
                        ter.tablero[posInic[pos]] = 2;
                        posicion = boton[posInic[pos]];
                    }
                    break;
                    case 3:
                    case 5:
                    case 7:
                    {
                        posicion = boton[ter.IA2Gana()];
                        if (Arrays.equals(copia, ter.tablero))
                        {
                            posicion = boton[ter.IA2Bloquea()];
                        }
                        if (Arrays.equals(copia, ter.tablero))
                        {
                            int pos = r.nextInt(9);
                            while (!ter.movimientoValido(pos))
                            {
                                pos = r.nextInt(9);
                            }
                            ter.tablero[pos] = 2;
                            posicion = boton[pos];
                        }
                    }
                    break;
                    default:
                        throw new InvalidParameterException("Sin turnos");
                }
                return posicion;
            }
        }
        return posicion;
    }
    public void jugar (Button b)
    {
        List<Button> botones = Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9);
        if (ia.isChecked())
        {
            if (b.getText().toString().equals(""))
            {
                if (turno() % 2 == 0)
                {
                    ter.mueveJugador1(botones.indexOf(b));
                    b.setTextColor(Color.parseColor("#3F51B5"));
                    if (ter.ganaJugador1())
                    {
                        muestraTitulo();
                        titulo.setTextColor(Color.parseColor("#3F51B5"));
                        titulo.setText(R.string.ganaP1);
                        muestraTitulo();
                        fin();
                        contador(1, 0);
                    }
                }
                else
                {
                    ter.mueveJugador2(botones.indexOf(b));
                    b.setTextColor(Color.parseColor("#F44336"));
                    if (ter.ganaJugador2())
                    {
                        titulo.setTextColor(Color.parseColor("#F44336"));
                        titulo.setText(R.string.ganaP2);
                        muestraTitulo();
                        fin();
                        contador(0, 1);
                    }
                }
                b.setText(xo());
            }
        }
        else
        {
            if (b.getText().toString().equals(""))
            {
                b.setTextColor(Color.parseColor("#3F51B5"));
                b.setText(xo());
                ter.mueveJugador1(botones.indexOf(b));
                if (ter.ganaJugador1())
                {
                    muestraTitulo();
                    titulo.setTextColor(Color.parseColor("#3F51B5"));
                    titulo.setText(R.string.ganaP1);
                    fin();
                    contador(1, 0);
                }
                if (turno() < 8 && !ter.ganaJugador1())
                {
                    Button cpu = ia(turno());
                    cpu.setTextColor(Color.parseColor("#F44336"));
                    cpu.setText(xo());
                    if (ter.ganaJugador2())
                    {
                        muestraTitulo();
                        titulo.setTextColor(Color.parseColor("#F44336"));
                        titulo.setText(R.string.ganaCPU);
                        fin();
                        contador(0, 1);
                    }
                }
            }
        }

        if (!ter.quedanMovimientos() && !ter.ganaJugador1() && !ter.ganaJugador2())
        {
            muestraTitulo();
            titulo.setTextColor(Color.parseColor("#673AB7"));
            titulo.setText(R.string.empate);
        }
    }
    public void botones()
    {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b3);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b4);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b5);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b6);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b7);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b8);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar(b9);
            }
        });
    }
    public void mostrarBotones()
    {
        Button[] boton = {b1, b2, b3, b4, b5, b6, b7, b8, b9};

        for (int i = 0; i < 9; i++)
        {
            boton[i].setVisibility(View.VISIBLE);
        }
    }
    public void ocultarBotones()
    {
        Button[] boton = {b1, b2, b3, b4, b5, b6, b7, b8, b9};

        for (int i = 0; i < 9; i++)
        {
            boton[i].setVisibility(View.INVISIBLE);
        }
    }
    public void mostrarNiveles()
    {
        dificultad.setVisibility(View.VISIBLE);
        facil.setVisibility(View.VISIBLE);
        medio.setVisibility(View.VISIBLE);
        dificil.setVisibility(View.VISIBLE);
    }
    public void ocultarNiveles()
    {
        dificultad.setVisibility(View.INVISIBLE);
        facil.setVisibility(View.INVISIBLE);
        medio.setVisibility(View.INVISIBLE);
        dificil.setVisibility(View.INVISIBLE);
    }
    public void reiniciarNiveles()
    {
        for (int i = 0; i < 3; i++)
        {
            niveles[i] = false;
        }
    }
    public int dificultad()
    {
        int nivel = 0;
        for (int i = 0; i < 3; i++)
        {
            if (niveles[i])
            {
                nivel = i;
            }
        }
        return nivel;
    }
    public void nivelDificultad()
    {
        facil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                niveles[0] = true;
                ocultarNiveles();
                mostrarBotones();
            }
        });
        medio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                niveles[1] = true;
                ocultarNiveles();
                mostrarBotones();
            }
        });
        dificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                niveles[2] = true;
                ocultarNiveles();
                mostrarBotones();
            }
        });
    }
}
