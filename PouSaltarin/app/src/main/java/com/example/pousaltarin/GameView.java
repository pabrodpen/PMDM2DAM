package com.example.pousaltarin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

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
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
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
        pou.setSpeedY(pou.getSpeedY() + 0.5f); // Incremento de velocidad de caída más suave

        // Actualizar el Pou
        pou.update();

        // Comprobar colisiones entre el Pou y las plataformas
        boolean enContacto = false;
        for (Plataforma platform : platforms) {
            if (Rect.intersects(pou.getRect(), platform.getRect())) {
                enContacto = true;
                // Si el Pou está cayendo y en contacto con una plataforma, permitir el salto
                if (isFalling && pou.getRect().top < platform.getRect().top) {
                    pou.setSpeedY(-35); // Velocidad inicial de salto hacia arriba
                    break; // Solo permitimos un salto por colisión
                }
                // Si el Pou está tocando la plataforma por debajo, simplemente continúa su movimiento sin detenerse
                if (pou.getRect().bottom > platform.getRect().bottom) {
                    // No hacemos nada, el Pou atraviesa la plataforma
                }
            }
        }

        // Si el Pou no está en contacto con ninguna plataforma y está cayendo, aumentar su velocidad de caída
        if (!enContacto && isFalling) {
            pou.setSpeedY(pou.getSpeedY() + 0.5f); // Aumento suave de la velocidad de caída
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
            double inclinacion = Math.atan2(-xAcceleration, yAcceleration) * 180 / Math.PI; // Cambio aquí

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
        int minDistanceBetweenPlatforms = 350; // Distancia mínima horizontal entre plataformas
        int minVerticalDistanceBetweenPlatforms = 400; // Distancia mínima vertical entre plataformas

        // Definir los límites horizontales para que las plataformas estén dentro de la pantalla
        int minX = 0;
        int maxX = screenWidth - platformWidth;

        // Definir el límite inferior para que las plataformas estén dentro de la pantalla
        int maxY = screenHeight - platformHeight;

        // Inicializar la posición de la primera plataforma
        int lastPlatformX = 0; // Inicializa en 0

        // Contador para llevar el registro de las plataformas creadas
        int numPlatformsCreated = 0;

        while (numPlatformsCreated < numPlatformsToCreate) {
            // Generar una posición X aleatoria para la plataforma
            int platformX = random.nextInt(maxX - minX + 1) + minX;

            // Asegurarse de que la nueva plataforma esté lo suficientemente lejos de la anterior horizontalmente
            if (Math.abs(platformX - lastPlatformX) < minDistanceBetweenPlatforms) {
                // Ajustar la posición X si la distancia entre plataformas es menor que la mínima
                if (platformX > lastPlatformX) {
                    platformX += minDistanceBetweenPlatforms; // Mueve la plataforma hacia la derecha
                } else {
                    platformX -= minDistanceBetweenPlatforms; // Mueve la plataforma hacia la izquierda
                }
            }

            // Generar una posición Y aleatoria para la plataforma dentro de los límites verticales
            int platformY = random.nextInt(maxY - minVerticalDistanceBetweenPlatforms - platformHeight) + minVerticalDistanceBetweenPlatforms;

            // Crear la plataforma y agregarla a la lista
            // Añadimos true como quinto argumento para indicar que la plataforma es pasable
            Plataforma newPlatform = new Plataforma(platformX, platformY, platformX + platformWidth, platformY + platformHeight, true);

            // Verificar si la nueva plataforma se superpone con alguna de las existentes
            boolean overlap = false;
            for (Plataforma existingPlatform : platforms) {
                if (Rect.intersects(newPlatform.getRect(), existingPlatform.getRect())) {
                    overlap = true;
                    break;
                }
            }

            // Si no hay superposición, agregamos la nueva plataforma
            if (!overlap) {
                platforms.add(newPlatform);
                numPlatformsCreated++;
                lastPlatformX = platformX;
            }
        }
    }
}
