package com.example.micuartograficointeractivo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Grafico grafico=new Grafico(this,null);

        setContentView(R.layout.activity_main);
    }
}