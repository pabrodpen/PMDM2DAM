package com.example.miprimergrafico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraficoView extends View {

    public GraficoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Instrucciones para dibujar en el canvas mediante un objeto Paint y sus métodos para cambiar el color, tamaño de texto, etc.

        canvas.drawColor(Color.WHITE);

        // ALGUNOS MÉTODOS DE Paint y Canvas:

        Paint paint = new Paint();

        // PARA RELLENAR TODO EL CANVAS
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        // Establecer color rojo, verde, azul
        paint.setColor(Color.rgb(100, 20, 10));

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Densidad de escala para que se vea igual en distintas pantallas
        float s = getResources().getDisplayMetrics().scaledDensity;

        // Para suavizar los bordes de los caracteres y evitar el efecto de pixelado
        paint.setAntiAlias(true);

        // Tamaño de texto
        paint.setTextSize(20 * s);

        // Dibujar texto en el canvas
        canvas.drawText("width: " + width + " height: " + height, 20 * s, 40 * s, paint);

        // Las coordenadas se reescalan/redimensionan multiplicándose por s
        canvas.drawText("escala: " + s, 20 * s, 140 * s, paint);

        // Cambiar color a azul
        paint.setColor(Color.BLUE);

        // Dibujar línea horizontal en el canvas
        canvas.drawLine(0, 40 * s, width, 40 * s, paint);

        // Cambiar color a naranja
        paint.setColor(Color.YELLOW);

        // Dibujar línea vertical en el canvas
        canvas.drawLine(20 * s, 0, 20 * s, height, paint);
    }
}

