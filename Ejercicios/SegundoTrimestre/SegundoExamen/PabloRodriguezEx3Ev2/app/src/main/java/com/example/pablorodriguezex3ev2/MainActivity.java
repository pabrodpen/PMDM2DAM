package com.example.pablorodriguezex3ev2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean continuar = true;
    float velocidad = 0.5f;
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
        int[] x = new int[2]; // Array para las coordenadas X de las bolas
        int[] y = new int[2]; // Array para las coordenadas Y de las bolas
        int[] xmax = new int[2]; // Array para las coordenadas máximas X de las bolas
        int[] ymax = new int[2]; // Array para las coordenadas máximas Y de las bolas
        Paint paintFondo, paintParticula, paint;

        int score=0;
        boolean[][] movXPositive = new boolean[2][2]; // Array para indicar la dirección X de las bolas
        boolean[][] movYPositive = new boolean[2][2]; // Array para indicar la dirección Y de las bolas
        int[] colores = {Color.RED, Color.BLUE}; // Array de colores para las bolas

        public DinamicaView(Context context) {
            super(context);
            paintFondo = new Paint();
            paintParticula = new Paint();
            paint = new Paint();
            paintFondo.setColor(Color.WHITE);
            paintParticula.setStyle(Paint.Style.FILL);

            // Inicializar las posiciones y direcciones de las bolas
            for (int i = 0; i < 2; i++) {
                x[i] = (int) (Math.random() * xmax[i]);
                y[i] = (int) (Math.random() * ymax[i]);
                xmax[i] = getWidth();
                ymax[i] = getHeight();
                for (int j = 0; j < 2; j++) {
                    movXPositive[i][j] = Math.random() < 0.5;
                    movYPositive[i][j] = Math.random() < 0.5;
                }
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            // Asignar valores máximos para las coordenadas de las bolas
            for (int i = 0; i < 2; i++) {
                xmax[i] = w;
                ymax[i] = h;
            }
        }

        @Override
        public void run() {
            // Implementa la lógica del movimiento para cada bola
            while (continuar) {
                for (int i = 0; i < 2; i++) {
                    // Movimiento en el eje X
                    if (movXPositive[i][i]) {
                        x[i] = x[i] + (int) (velocidad * dt);
                    } else {
                        x[i] = x[i] - (int) (velocidad * dt);
                    }

                    // Si llega a los extremos horizontales, invertir la dirección
                    if (x[i] > xmax[i] || x[i] < 0) {
                        movXPositive[i][i] = !movXPositive[i][i];
                    }

                    // Movimiento en el eje Y
                    if (movYPositive[i][i]) {
                        y[i] = y[i] + (int) (velocidad * dt);
                    } else {
                        y[i] = y[i] - (int) (velocidad * dt);
                    }

                    // Si llega a los extremos verticales, invertir la dirección
                    if (y[i] > ymax[i] || y[i] < 0) {
                        movYPositive[i][i] = !movYPositive[i][i];
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
            canvas.drawText("puntuacion = " + score, 10 * s, 25 * s, paint);
            // Dibujar las dos bolas en posiciones diferentes
            for (int i = 0; i < 2; i++) {
                paintParticula.setColor(colores[i]); // Asignar el color correspondiente a la bola
                canvas.drawCircle(x[i], y[i], 30 * s, paintParticula);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getResources().getDisplayMetrics().heightPixels;

            switch (action) {
                case MotionEvent.ACTION_UP:
                    for (int i = 0; i < 2; i++) {
                        // Generar nuevas coordenadas aleatorias para cada bola
                        x[i] = (int) (Math.random() * screenWidth);
                        y[i] = (int) (Math.random() * screenHeight);
                    }
                    score++;
                    invalidate(); // Vuelve a dibujar la pantalla
                    break;
            }

            return true;
        }
    }
}
