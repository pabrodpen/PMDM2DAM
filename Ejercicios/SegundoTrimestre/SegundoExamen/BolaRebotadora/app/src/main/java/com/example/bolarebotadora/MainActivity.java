package com.example.bolarebotadora;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private BolaRebote bolaRebote;
    private RelativeLayout contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener el contenedor de diseño
        contenedor = findViewById(R.id.contenedor);

        // Crear una nueva instancia de BolaRebote
        bolaRebote = new BolaRebote(0.5f, 0.5f, 0.02f, 0.03f, 0.001f, 0.01f);

        // Crear una nueva instancia de BolaReboteView y agregarla al contenedor
        BolaReboteView bolaReboteView = new BolaReboteView(this, bolaRebote);
        contenedor.addView(bolaReboteView);

        // Iniciar un hilo para actualizar continuamente la posición de la bola
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Actualizar la posición de la bola
                    bolaRebote.updatePosition();

                    // Redibujar la vista de la bola
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bolaReboteView.invalidate();
                        }
                    });

                    // Pausa breve para dar tiempo al hilo de dibujar
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo.start();
    }
}
