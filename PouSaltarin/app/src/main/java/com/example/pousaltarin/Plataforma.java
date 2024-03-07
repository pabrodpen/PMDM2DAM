package com.example.pousaltarin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Plataforma {
    private Rect rect;
    private boolean moving;
    private int screenWidth;
    private float speedX;

    public Plataforma(int left, int top, int right, int bottom, boolean moving, int screenWidth) {
        this.rect = new Rect(left, top, right, bottom);
        this.moving = moving;
        this.screenWidth = screenWidth;
        this.speedX = 5; // Velocidad inicial en el eje X
    }

    public Rect getRect() {
        return rect;
    }

    public boolean isMoving() {
        return moving;
    }

    public void updateHorizontalPosition() {
        // Mover la plataforma horizontalmente en función de la velocidad
        rect.left += speedX;
        rect.right += speedX;

        // Si la plataforma alcanza los límites de la pantalla, cambiar la dirección
        if (rect.left < 0 || rect.right > screenWidth) {
            speedX = -speedX; // Invertir la dirección
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(rect, paint);
    }
}
