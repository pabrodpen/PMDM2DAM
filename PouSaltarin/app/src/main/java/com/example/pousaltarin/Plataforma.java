package com.example.pousaltarin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Plataforma {

    private Rect rect;
    private Paint paint;



    // Agrega este constructor que acepta solo cuatro argumentos
    public Plataforma(int left, int top, int right, int bottom) {
        rect = new Rect(left, top, right, bottom);
        paint = new Paint();
        paint.setColor(Color.BLACK); // Color de la plataforma (puedes cambiarlo según tus preferencias)
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    public Rect getRect() {
        return rect;
    }
}
