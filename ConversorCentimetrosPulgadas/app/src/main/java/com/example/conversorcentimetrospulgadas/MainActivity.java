package com.example.conversorcentimetrospulgadas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText cm;
    EditText inches;
    Button centimetrosPulgadas;
    Button pulgadasCentimetros;
    double conversion = 2.54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cm = findViewById(R.id.cm);
        inches = findViewById(R.id.inches);
        centimetrosPulgadas = findViewById(R.id.centimetrosPulgadas);
        pulgadasCentimetros = findViewById(R.id.pulgadasCentimetros);

        centimetrosPulgadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (centimetrosAPulgadas() != 0.0) {
                    inches.setText(String.valueOf(centimetrosAPulgadas()));
                }
            }
        });
        pulgadasCentimetros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pulgadasACentimetros() != 0.0) {
                    cm.setText(String.valueOf(pulgadasACentimetros()));
                }
            }
        });
    }

    public double centimetrosAPulgadas ()
    {
        double centimetros = 0.0;
        try {
            centimetros = Double.parseDouble(cm.getText().toString());
        }
        catch (Exception e) {}
        return centimetros / conversion;
    }
    public double pulgadasACentimetros ()
    {
        double pulgadas = 0.0;
        try {
            pulgadas = Double.parseDouble(inches.getText().toString());
        }
        catch (Exception e) {}
        return pulgadas * conversion;
    }
}
