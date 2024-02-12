package com.example.repaso1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    /*
    * Crea una aplicación donde puedas dibujar círculos y cuadrados en la pantalla y luego arrastrarlos
    * con el dedo. Además, asegúrate de que puedas eliminar las formas arrastrándolas fuera de la pantalla.
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


}