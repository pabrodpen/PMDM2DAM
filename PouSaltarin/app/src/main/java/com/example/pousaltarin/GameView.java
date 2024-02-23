package com.example.pousaltarin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable, SensorEventListener {

    private boolean isPlaying;
    private Thread gameThread;
    private SurfaceHolder surfaceHolder;
    private Pou pou;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private int screenWidth;
    private int screenHeight;

    private List<Plataforma> platforms;
    private Random random;

    public GameView(Context context) {
        super(context);
        surfaceHolder = getHolder();

        // Obtener el servicio del sensor
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // Registra el acelerómetro y el giroscopio
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Obtener el ancho y la altura de la pantalla
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();

        // Iniciar la lista de plataformas
        platforms = new ArrayList<>();
        random = new Random();

        // Crear una instancia de Pou con el ancho y la altura de la pantalla
        pou = new Pou(context, screenWidth, screenHeight, 150, 100); // o cualquier otro ancho y alto que desees

        // Crear plataformas
        createPlatforms();

        // Iniciar los sensores
        startSensor();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        // Comprobar si el Pou está cayendo (velocidad vertical positiva)
        boolean isFalling = pou.getSpeedY() > 0;

        // Aplicar gravedad
        pou.setSpeedY(pou.getSpeedY() + 1); // Siempre aplicamos gravedad

        // Actualizar el Pou
        pou.update();

        // Comprobar colisiones entre el Pou y las plataformas
        boolean enContacto = false;
        for (Plataforma platform : platforms) {
            if (Rect.intersects(pou.getRect(), platform.getRect())) {
                enContacto = true;
                // Si el Pou está cayendo y en contacto con una plataforma, permitir el salto
                if (isFalling) {
                    pou.setSpeedY(-20); // Velocidad inicial de salto hacia arriba
                    break; // Solo permitimos un salto por colisión
                } else {
                    // Detener la caída si está en una plataforma
                    pou.setSpeedY(0);
                    break; // No necesitamos seguir comprobando colisiones
                }
            }
        }

        // Si el Pou no está en contacto con ninguna plataforma y está cayendo, detener su caída
        if (!enContacto && isFalling) {
            pou.setSpeedY(0);
        }
    }




    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            // Dibujar el fondo, el Pou, las plataformas, etc.
            canvas.drawColor(Color.WHITE); // Fondo blanco como ejemplo
            pou.draw(canvas);
            for (Plataforma platform : platforms) {
                platform.draw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            // Control de velocidad de fotogramas
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Obtener los valores de la aceleración en los ejes X e Y
            float xAcceleration = event.values[0];
            float yAcceleration = event.values[1];

            // Calcular la inclinación en grados
            double inclinacion = Math.atan2(xAcceleration, yAcceleration) * 180 / Math.PI;

            // Ajustar la velocidad horizontal del Pou según la inclinación
            // Aquí asumimos que un ángulo positivo implica inclinación hacia la derecha y viceversa
            float velocidadHorizontal = 10; // Velocidad horizontal base
            pou.setSpeedX(velocidadHorizontal * (float)Math.sin(Math.toRadians(inclinacion)));
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se utiliza en este ejemplo
    }

    public void startSensor() {
        // Registra el SensorEventListener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSensor() {
        // Pausa la detección de sensores cuando el juego está pausado
        sensorManager.unregisterListener(this);
    }

    private void createPlatforms() {
        int numPlatformsToCreate = 15; // Número total de plataformas que deseamos crear
        int platformWidth = 120;
        int platformHeight = 20;
        int minDistanceBetweenPlatforms = 100; // Distancia mínima horizontal entre plataformas
        int minVerticalDistanceBetweenPlatforms = 100; // Distancia mínima vertical entre plataformas
        int maxY = screenHeight - platformHeight; // Máxima posición Y para la generación de plataformas

        int lastPlatformX = 0; // Posición X de la última plataforma generada
        int lastPlatformY = 0; // Posición Y de la última plataforma generada

        int maxHeight = (int) (screenHeight * 1); // Limitar la altura máxima para la generación de plataformas (último cuarto excluido)

        int numPlatformsCreated = 0; // Contador para llevar el registro de las plataformas creadas

        while (numPlatformsCreated < numPlatformsToCreate) {
            // Generar una posición X aleatoria para la plataforma
            int platformX = random.nextInt(screenWidth - platformWidth);

            // Generar una posición Y aleatoria para la plataforma
            int platformY = random.nextInt(maxHeight);

            // Asegurarse de que la nueva plataforma esté lo suficientemente lejos de la anterior
            if (numPlatformsCreated > 0 && platformY < lastPlatformY + minVerticalDistanceBetweenPlatforms) {
                platformY = lastPlatformY + minVerticalDistanceBetweenPlatforms;
            }

            // Actualizar la posición X de la última plataforma generada
            lastPlatformX = platformX;

            platforms.add(new Plataforma(platformX, platformY, platformX + platformWidth, platformY + platformHeight));

            numPlatformsCreated++; // Incrementar el contador de plataformas creadas
            lastPlatformY = platformY; // Actualizar la última posición Y generada
        }
    }


}

