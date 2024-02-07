package com.example.animaciones1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Thread hilo = null;
    Animacion animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animacion = new Animacion(this);
        setContentView(animacion);

        // Obtener la altura máxima de la vista
        int maxY = getResources().getDisplayMetrics().heightPixels;
        animacion.setMaxY(maxY);

        hilo = new Thread(animacion);
        hilo.start();
    }

    // Detener el hilo cuando la actividad está en pausa
    @Override
    protected void onPause() {
        super.onPause();
        animacion.continuar = false;
    }

    // Reanudar el hilo cuando la actividad se reanuda
    @Override
    protected void onResume() {
        super.onResume();
        animacion.continuar = true;
        if (!hilo.isAlive()) {
            hilo = new Thread(animacion);
            hilo.start();
        }
    }
}
