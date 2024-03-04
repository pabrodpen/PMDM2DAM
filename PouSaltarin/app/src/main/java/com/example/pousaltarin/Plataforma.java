package com.example.pousaltarin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Plataforma {

    private Rect rect;
    private boolean passable;
    private boolean moving; // Indica si la plataforma se mueve horizontalmente
    private int speedX; // Velocidad horizontal de la plataforma
    private int screenWidth;

    public Plataforma(int left, int top, int right, int bottom, boolean passable) {
        this.rect = new Rect(left, top, right, bottom);
        this.passable = passable;
        this.moving = moving;
        this.speedX = speedX;
        this.screenWidth = screenWidth;
    }

    public Plataforma(int left, int top, int right, int bottom, boolean passable, boolean moving, int speedX) {
        this(left, top, right, bottom, passable); // Invocar al otro constructor con un valor por defecto para screenWidth
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect, paint);
    }

    public void updateHorizontalPosition() {
        if (moving) {
            rect.left += speedX;
            rect.right += speedX;
            // Si la plataforma sale de la pantalla por la izquierda, la movemos a la derecha
            if (rect.right < 0) {
                rect.left = screenWidth;
                rect.right = rect.left + (rect.right - rect.left);
            }
            // Si la plataforma sale de la pantalla por la derecha, la movemos a la izquierda
            if (rect.left > screenWidth) {
                rect.right = 0;
                rect.left = rect.right - (rect.right - rect.left);
            }
        }
    }

    public Rect getRect() {
        return rect;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
}
