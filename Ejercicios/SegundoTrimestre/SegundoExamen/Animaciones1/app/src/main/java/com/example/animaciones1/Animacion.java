package com.example.animaciones1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Animacion extends View implements Runnable {

    int x, y, ymax;
    Paint paintFondo, paintParticula, paint;
    boolean continuar = true;
    float velocidad = 1.5f;
    int dt = 10;
    int tiempo = 0;

    public Animacion(Context context) {
        super(context);

        // Inicializar los Paints
        paintFondo = new Paint();
        paintParticula = new Paint();
        paint = new Paint();

        // Establecer colores para los Paints
        paintFondo.setColor(Color.WHITE);
        paintParticula.setColor(Color.RED);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar el fondo
        canvas.drawPaint(paintFondo);

        // Dibujar la partícula
        canvas.drawCircle(x, y, 10, paintParticula);

        // Dibujar el tiempo
        canvas.drawText("Tiempo: " + tiempo, 10, 50, paint);
    }

    @Override
    public void run() {
        while (continuar) {
            tiempo = tiempo + dt;

            // Movimiento rectilíneo uniforme: y = y + v * t
            y = y + (int) (velocidad * dt);

            // Si llega abajo, invertimos la velocidad
            if (y > ymax) {
                velocidad = -velocidad;
            }

            // Si llega arriba, invertimos la velocidad
            if (y < 0) {
                velocidad = -velocidad;
            }

            // Redibujar la vista
            postInvalidate();

            try {
                Thread.sleep(dt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMaxY(int maxY) {
        this.ymax = maxY;
    }
}
