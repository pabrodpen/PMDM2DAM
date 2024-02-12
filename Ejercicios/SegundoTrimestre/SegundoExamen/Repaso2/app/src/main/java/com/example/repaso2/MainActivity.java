package com.example.repaso2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean continuar = true;
    float velocidad = 1.5f;
    int dt = 10;
    int tiempo = 0;
    Thread hilo = null;
    DinamicaView dinamica;
    float s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dinamica = new DinamicaView(this);
        setContentView(dinamica);
        s = getResources().getDisplayMetrics().density;
        hilo = new Thread(dinamica);
        hilo.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        continuar = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        continuar = true;
        if (!hilo.isAlive()) {
            hilo = new Thread(dinamica);
            hilo.start();
        }
    }

    class DinamicaView extends View implements Runnable {
        int[] x = new int[3]; // Array para las coordenadas X de las bolas
        int[] y = new int[3]; // Array para las coordenadas Y de las bolas
        int[] xmax = new int[3]; // Array para las coordenadas máximas X de las bolas
        int[] ymax = new int[3]; // Array para las coordenadas máximas Y de las bolas
        Paint paintFondo, paintParticula, paint;
        boolean[][] movXPositive = new boolean[3][3]; // Array para indicar la dirección X de las bolas
        boolean[][] movYPositive = new boolean[3][3]; // Array para indicar la dirección Y de las bolas

        public DinamicaView(Context context) {
            super(context);
            paintFondo = new Paint();
            paintParticula = new Paint();
            paint = new Paint();
            paintFondo.setColor(Color.WHITE);
            paintParticula.setColor(Color.RED);
            paint.setColor(Color.BLACK);

            // Inicializar las posiciones y direcciones de las tres bolas
            for (int i = 0; i < 3; i++) {
                x[i] = (int) (Math.random() * xmax[i]);
                y[i] = (int) (Math.random() * ymax[i]);
                for (int j = 0; j < 3; j++) {
                    movXPositive[i][j] = Math.random() < 0.5;
                    movYPositive[i][j] = Math.random() < 0.5;
                }
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            // Asignar valores máximos para las coordenadas de las bolas
            for (int i = 0; i < 3; i++) {
                xmax[i] = w;
                ymax[i] = h;
            }
        }

        @Override
        public void run() {
            // Implementa la lógica del movimiento para cada bola
            while (continuar) {
                for (int i = 0; i < 3; i++) {
                    // Movimiento en el eje X
                    for (int j = 0; j < 3; j++) {
                        if (movXPositive[i][j]) {
                            x[i] = x[i] + (int) (velocidad * dt);
                        } else {
                            x[i] = x[i] - (int) (velocidad * dt);
                        }

                        // Si llega a los extremos horizontales, invertir la dirección
                        if (x[i] > xmax[i] || x[i] < 0) {
                            movXPositive[i][j] = !movXPositive[i][j];
                        }

                        // Movimiento en el eje Y
                        if (movYPositive[i][j]) {
                            y[i] = y[i] + (int) (velocidad * dt);
                        } else {
                            y[i] = y[i] - (int) (velocidad * dt);
                        }

                        // Si llega a los extremos verticales, invertir la dirección
                        if (y[i] > ymax[i] || y[i] < 0) {
                            movYPositive[i][j] = !movYPositive[i][j];
                        }
                    }
                }
                postInvalidate();
                try {
                    Thread.sleep(dt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPaint(paintFondo);
            paint.setTextSize(20 * s);
            // Dibujar las tres bolas en posiciones diferentes
            for (int i = 0; i < 3; i++) {
                canvas.drawCircle(x[i], y[i], 30 * s, paintParticula);
            }
        }
    }

}
