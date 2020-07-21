package com.example.bien_mal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    // Sonido
    private SoundPool fin;
    private SoundPool cuentaAtras;
    // Maximum sound stream
    private static final int MAX_STREAMS = 5;
    // Stream type
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private boolean cargado;
    private float volumen;
    // Sounds
    private int idFin;
    private int idCuentaAtras;

    // Elementos
    private List<String> frasesPersonalizadas;
    private Frases frases;
    private boolean annadiendo;
    private boolean juegoComenzado;

    private Button jugar;
    private Button annadir;
    private Button info;
    private TextView frase;
    private TextView bienMal;
    private TextView tiempo;
    private EditText editarFrase;
    private ScrollView scrollView;
    private LinearLayout ll;
    private ImageButton volver;
    private ImageButton borrar;
    private ImageButton reiniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();
        tamanno();

        jugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                jugar();
                contador();
            }
        });

        volver.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (annadiendo)
                {
                    volver();
                    if (view != null)
                    {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    escibeFichero(getApplicationContext());
                }
                else
                {
                    volver();
                }
            }
        });
        reiniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                reiniciar();
            }
        });

        annadir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (annadiendo)
                {
                    if (!editarFrase.getText().toString().equals(""))
                    {
                        annadeFrase();
                    }
                }
                else
                {
                    annadir();
                }
            }
        });

        borrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                borrar();
            }
        });

        info.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                info();
            }
        });

        sonido();
    }

    public void inicializar()
    {
        jugar = findViewById(R.id.nueva);
        annadir = findViewById(R.id.add);
        jugar.setBackgroundColor(Color.parseColor("#03DAC5"));
        annadir.setBackgroundColor(Color.parseColor("#03DAC5"));

        frase = findViewById(R.id.frase);
        bienMal = findViewById(R.id.bienMal);
        info = findViewById(R.id.info);
        tiempo = findViewById(R.id.tiempo);
        editarFrase = findViewById(R.id.crear);
        scrollView = findViewById(R.id.scroll);
        ll = findViewById(R.id.ll);
        volver = findViewById(R.id.volver);
        borrar = findViewById(R.id.borrar);
        reiniciar = findViewById(R.id.reiniciar);

        frases = new Frases();
        frasesPersonalizadas = new LinkedList<>();
        annadiendo = false;
        juegoComenzado = false;

        leeFichero(getApplicationContext());
        frases.annadeLista(frasesPersonalizadas);
    }
    public void volver()
    {
        if (juegoComenzado)
        {
            jugar.setText(R.string.continuar);
            reiniciar.setVisibility(View.VISIBLE);
        }
        else
        {
            jugar.setText(R.string.comenzar);
        }

        if (!juegoComenzado)
        {
            frases = new Frases();
            frases.annadeLista(frasesPersonalizadas);
        }

        frase.setVisibility(View.INVISIBLE);
        frase.setText("");

        ll.removeAllViews();
        editarFrase.setText("");

        bienMal.setText(R.string.titulo);
        bienMal.setTextColor(Color.BLACK);
        bienMal.setVisibility(View.VISIBLE);
        jugar.setVisibility(View.VISIBLE);
        volver.setVisibility(View.GONE);
        borrar.setVisibility(View.GONE);
        info.setVisibility(View.VISIBLE);
        tiempo.setText("");

        scrollView.setVisibility(View.GONE);

        editarFrase.setVisibility(View.GONE);
        jugar.setClickable(true);
        annadiendo = false;
    }
    public void reiniciar()
    {
        frases = new Frases();
        frases.annadeLista(frasesPersonalizadas);
        reiniciar.setVisibility(View.GONE);
        juegoComenzado = false;

        String reiniciaLista = "Lista de frases reiniciada";
        Toast.makeText(this, reiniciaLista, Toast.LENGTH_LONG).show();
    }
    public void annadir()
    {
        frase.setVisibility(View.VISIBLE);
        frase.setText("Frases personalizadas:");

        volver.setVisibility(View.VISIBLE);
        borrar.setVisibility(View.VISIBLE);
        bienMal.setVisibility(View.INVISIBLE);
        jugar.setVisibility(View.INVISIBLE);
        tiempo.setVisibility(View.INVISIBLE);
        editarFrase.setVisibility(View.VISIBLE);
        info.setVisibility(View.GONE);
        reiniciar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        jugar.setClickable(false);
        annadiendo = true;

        if (!frasesPersonalizadas.isEmpty())
        {
            for (int i = 0; i < frasesPersonalizadas.size(); i++)
            {
                Typeface face = ResourcesCompat.getFont(this, R.font.aldrich);
                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setTextSize(25);
                cb.setPadding(50, 5, 0, 0);
                cb.setTypeface(face);
                cb.setText(frasesPersonalizadas.get(i));
                ll.addView(cb);
            }
        }
    }
    public void borrar()
    {
        if (!frasesPersonalizadas.isEmpty())
        {
            for (int i = 0; i < frasesPersonalizadas.size(); i++)
            {
                View v = ll.getChildAt(i);
                if (((CheckBox) v).isChecked())
                {
                    ll.removeViewAt(i);
                    frasesPersonalizadas.remove(i);
                    i--;
                }
            }
        }
    }
    public void annadeFrase()
    {
        frases.annadeFrase(editarFrase.getText().toString());
        frasesPersonalizadas.add(editarFrase.getText().toString());

        Typeface face = ResourcesCompat.getFont(this, R.font.aldrich);
        CheckBox cb = new CheckBox(getApplicationContext());
        cb.setTextSize(25);
        cb.setPadding(50, 5, 0, 0);
        cb.setTypeface(face);
        cb.setText(frasesPersonalizadas.get(frasesPersonalizadas.size()-1));
        ll.addView(cb);

        editarFrase.setText("");
    }
    public void jugar()
    {
        juegoComenzado = true;
        volver.setVisibility(View.VISIBLE);
        frase.setVisibility(View.VISIBLE);
        bienMal.setText("");
        tiempo.setVisibility(View.INVISIBLE);
        info.setVisibility(View.GONE);
        reiniciar.setVisibility(View.GONE);
        jugar.setText(R.string.continuar);
        jugar.setClickable(false);
        volver.setClickable(false);
        annadir.setClickable(false);
        jugar.setBackgroundColor(Color.parseColor("#00554D"));
        annadir.setBackgroundColor(Color.parseColor("#00554D"));

        if (frases.listaVacia())
        {
            frases = new Frases();
            frases.annadeLista(frasesPersonalizadas);
        }

        String nuevaFrase = frases.fraseAleatoria();
        if (frase.getText().toString().equals(nuevaFrase))
        {
            nuevaFrase = frases.fraseAleatoria();
        }
        frase.setText(nuevaFrase);
    }
    public void contador()
    {
        Handler handler = new Handler();
        int ms = 2250 + ((frase.getText().toString().length() / 10)*250);
        handler.postDelayed(new Runnable() {
            public void run() {
                String bienMal = frases.bienMal();
                MainActivity.this.bienMal.setText(bienMal);

                if (bienMal.equals("Bien"))
                {
                    MainActivity.this.bienMal.setTextColor(Color.GREEN);
                }
                else
                {
                    MainActivity.this.bienMal.setTextColor(Color.RED);
                }

                reproduceCuentaAtras();

                new CountDownTimer(3100, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tiempo.setVisibility(View.VISIBLE);
                        long timer = (millisUntilFinished + 900) / 1000;
                        if (timer > 0)
                        {
                            tiempo.setText("Tiempo: " + timer);
                        }
                    }


                    public void onFinish() {
                        tiempo.setText("¡TIEMPO!");
                        jugar.setClickable(true);
                        annadir.setClickable(true);
                        volver.setClickable(true);
                        jugar.setBackgroundColor(Color.parseColor("#03DAC5"));
                        annadir.setBackgroundColor(Color.parseColor("#03DAC5"));
                        reproduceFin();
                    }

                }.start();
            }
        }, ms);
    }
    public void info()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create(); //Read Update
        alertDialog.setTitle("Instrucciones");
        alertDialog.setMessage("- Tendrás que responder a las preguntas bien o mal, dependiendo de lo que salga." +
                "\n- Tienes tan solo 3 segundos para responder cada pregunta." +
                "\n- Quien falle, tomará un chupito de lo que esté bebiendo." +
                "\n¡Disfruta jugando y consume con responsabilidad!");
        alertDialog.show();
    }
    public void reproduceFin()
    {
        if (cargado)
        {
            float leftVolumn = volumen;
            float rightVolumn = volumen;
            // Play sound. Returns the ID of the new stream.
            fin.play(idFin,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }
    public void reproduceCuentaAtras()
    {
        if (cargado)
        {
            float leftVolumn = volumen;
            float rightVolumn = volumen;
            // Play sound. Returns the ID of the new stream.
            cuentaAtras.play(idCuentaAtras,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }
    public void sonido()
    {
        // AudioManager audio settings for adjusting the volume
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Current volumn Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);

        // Volumn (0 --> 1)
        volumen = currentVolumeIndex / maxVolumeIndex;

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        setVolumeControlStream(streamType);

        // For Android SDK >= 21
        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

        fin = builder.build();
        cuentaAtras = builder.build();

        // When Sound Pool load complete.
        fin.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
            {
                cargado = true;
            }
        });
        cuentaAtras.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1)
            {
                cargado = true;
            }
        });

        // Sonido de TIEMPO
        idFin = fin.load(this, R.raw.ding, 1);
        // Sonido de RELOJ
        idCuentaAtras = cuentaAtras.load(this, R.raw.clock, 1);

    }
    private void escibeFichero(Context context)
    {
        StringBuilder s = new StringBuilder();
        if (!frasesPersonalizadas.isEmpty())
        {
            for (int i = 0; i < frasesPersonalizadas.size(); i++)
            {
                s.append(frasesPersonalizadas.get(i));
                if (i != frasesPersonalizadas.size()-1)
                {
                    s.append(System.lineSeparator());
                }
            }
        }

        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("frasesPersonalizadas.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(s.toString());
            outputStreamWriter.close();
        }
        catch (IOException ignored) {}
    }
    private void leeFichero(Context context)
    {
        try
        {
            InputStream inputStream = context.openFileInput("frasesPersonalizadas.txt");

            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;

                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    frasesPersonalizadas.add(receiveString);
                }

                bufferedReader.close();
                inputStream.close();
            }
        }
        catch (IOException ignored) {}
    }
    public void tamanno()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if (height > 2000)
        {
            frase.setTextSize(50);
            bienMal.setTextSize(75);
        }

//        String toastMsg = width + " x " + height;
//        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed()
    {
        if (annadiendo)
        {
            volver();
            escibeFichero(getApplicationContext());
        }
        else
        {
            if (juegoComenzado && jugar.isClickable())
            {
                volver();
            }
        }
    }
}