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
    private int screenCounter; // Contador de pantallas

    public Plataforma(int left, int top, int right, int bottom, boolean passable, int screenWidth) {
        this.rect = new Rect(left, top, right, bottom);
        this.passable = passable;
        this.moving = false; // Por defecto, la plataforma no se mueve
        this.speedX = 0; // Por defecto, la velocidad horizontal es 0
        this.screenWidth = screenWidth;
        this.screenCounter = 0; // Inicializar el contador de pantallas
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        if (screenCounter < 3) {
            paint.setColor(Color.parseColor("#654321"));
        } else if (screenCounter >= 7) {
            paint.setColor(Color.GRAY);
        }
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

        // Incrementar el contador de pantallas y verificar si es 7 o más
        screenCounter++;
        if (screenCounter >= 7) {
            moving = true; // Activar el movimiento de la plataforma
            speedX = 5; // Establecer la velocidad de movimiento
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
