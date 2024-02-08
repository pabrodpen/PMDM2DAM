package com.example.bolarebotadora;

public class BolaRebote {
    private float xPos, yPos; // Posición de la bola en la pantalla
    private float xVel, yVel; // Velocidades en dirección X e Y
    private float gravity; // Gravedad aplicada a la bola
    private float dt; // Paso de tiempo

    public BolaRebote(float xPos, float yPos, float xVel, float yVel, float gravity, float dt) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.gravity = gravity;
        this.dt = dt;
    }

    public void updatePosition() {
        // Actualizar la posición de la bola
        xPos += xVel * dt;
        yPos += yVel * dt;

        // Aplicar la gravedad a la velocidad en dirección Y
        yVel += gravity * dt;

        // Rebotar en las paredes
        if (xPos < 0 || xPos > 1) {
            xVel *= -1;
        }
        if (yPos < 0 || yPos > 1) {
            yVel *= -1;
        }
    }

    // Métodos getters y setters
    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public float getXVel() {
        return xVel;
    }

    public float getYVel() {
        return yVel;
    }

    public float getGravity() {
        return gravity;
    }

    public float getDt() {
        return dt;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public void setXVel(float xVel) {
        this.xVel = xVel;
    }

    public void setYVel(float yVel) {
        this.yVel = yVel;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setDt(float dt) {
        this.dt = dt;
    }
}
