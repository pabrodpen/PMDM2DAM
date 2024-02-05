package com.example.miprimergraficointeractivo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Grafico miGrafico = new Grafico(this, null);

        setContentView(R.layout.activity_main);
    }
}