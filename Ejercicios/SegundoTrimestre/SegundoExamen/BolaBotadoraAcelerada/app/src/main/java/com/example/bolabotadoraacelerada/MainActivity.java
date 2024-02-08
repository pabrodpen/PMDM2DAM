package com.example.bolabotadoraacelerada;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean continuar = true;
    float velocidadInicial = 1.0f;
    float aceleracion = 0.05f; // Aceleración para aumentar la velocidad con el tiempo
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
        int x, y, ymax;
        Paint paintFondo, paintParticula, paint;
        float velocidad; // Velocidad actual de la pelota

        public DinamicaView(Context context) {
            super(context);
            paintFondo = new Paint();
            paintParticula = new Paint();
            paint = new Paint();
            paintFondo.setColor(Color.WHITE);
            paintParticula.setColor(Color.RED);
            paint.setColor(Color.BLACK);
            velocidad = velocidadInicial; // Inicializar la velocidad
        }

        @Override
        public void run() {
            while (continuar) {
                tiempo = tiempo + dt;
                // Calcular el desplazamiento usando la velocidad actual
                y = y + (int) (velocidad * dt);
                // Aumentar la velocidad con el tiempo
                velocidad += aceleracion * dt;
                // si llega abajo o arriba, invertir la dirección
                if (y > ymax || y < 0) {
                    velocidad = -velocidad;
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
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            x = w / 2;
            y = h / 3; // Inicializar y como un tercio del alto de la pantalla
            ymax = h;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPaint(paintFondo);
            paint.setTextSize(20 * s);
            canvas.drawCircle(x, y, 30 * s, paintParticula);
            canvas.drawText("y = " + y, 10 * s, 25 * s, paint);
            canvas.drawText("tiempo = " + tiempo, 10 * s, 50 * s, paint);
        }
    }
}
