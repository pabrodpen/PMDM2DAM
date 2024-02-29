package com.example.pousaltarin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pou {
    private Bitmap bitmap;
    private int x, y; // Posición de Pou
    private int screenWidth, screenHeight; // Dimensiones de la pantalla
    private int width, height; // Dimensiones del bitmap de Pou
    private float speedX; // Velocidad horizontal del Pou
    private float speedY; // Velocidad vertical del Pou
    boolean isJumping;

    public Pou(Context context, int screenWidth, int screenHeight, int width, int height) {
        // Cargar el bitmap de Pou
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pou_recortado);

        // Escalar el bitmap para ajustarlo al tamaño deseado
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        // Establecer las dimensiones del bitmap de Pou
        this.width = width;
        this.height = height;

        // Guardar las dimensiones de la pantalla
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Iniciar movimiento desde el centro de la pantalla
        moverDesdeElCentro();
    }

    public void update() {
        // Mover el Pou
        x += speedX;
        y += speedY;

        // Restringir el Pou dentro de los límites de la pantalla
        if (x < 0) {
            x = 0;
        } else if (x + width > screenWidth) {
            x = screenWidth - width;
        }

        if (y < 0) {
            y = 0;
        } else if (y + height > screenHeight) {
            y = screenHeight - height;
        }
    }

    private void moverDesdeElCentro() {
        // Mover desde el centro de la pantalla
        x = screenWidth / 2 - width / 2;
        y = screenHeight / 2 - height / 2;

        // Establecer una velocidad inicial
        speedX = 400; // Puedes ajustar esta velocidad según sea necesario
        speedY = 400; // Puedes ajustar esta velocidad según sea necesario
    }

    public void draw(Canvas canvas) {
        // Dibujar el bitmap de Pou en la posición actual
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getRect() {
        return new Rect(x, y, x + width, y + height);
    }

    // Agrega los métodos setY, getSpeedX y getSpeedY
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }
}
