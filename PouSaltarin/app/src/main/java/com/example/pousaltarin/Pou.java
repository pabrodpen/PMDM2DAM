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
    private boolean isJumping; // Estado de salto del Pou

    public Pou(Context context, int screenWidth, int screenHeight,int width,int height) {
        // Cargar el bitmap de Pou
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pou_recortado);

        // Escalar el bitmap para ajustarlo al tamaño deseado
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        // Establecer las dimensiones del bitmap de Pou
        this.width = width;
        this.height = height;
        // Posición inicial de Pou (centrado en la parte inferior de la pantalla)
        x = (screenWidth - width) / 2;
        y = (screenHeight - height)/2;
        // Guardar las dimensiones de la pantalla
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // Inicializar la velocidad vertical del salto
        speedY = 500;
        // Inicializar el estado de salto
        isJumping = false;
    }

    public void update() {
        // Si está saltando, actualizar la velocidad vertical para simular la gravedad
        if (isJumping) {
            speedY += 200; // Ajusta la velocidad de gravedad según sea necesario
        }

        // Actualizar la posición vertical basada en la velocidad vertical
        y += speedY;

        // Asegurarse de que el Pou no se salga de la pantalla verticalmente
        if (y > screenHeight - height) {
            y = screenHeight - height;
            isJumping = false; // Restablecer el estado de salto si alcanza el suelo
            speedY = 0; // Restablecer la velocidad vertical
        }

        // Actualizar la posición horizontal basada en la velocidad horizontal
        x += speedX;

        // Asegurarse de que el Pou no se salga de la pantalla horizontalmente
        if (x < 0) {
            x = 0;
        } else if (x > screenWidth - width) {
            x = screenWidth - width;
        }
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

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }
}
