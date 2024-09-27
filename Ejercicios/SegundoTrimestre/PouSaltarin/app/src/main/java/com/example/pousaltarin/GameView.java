package com.example.pousaltarin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable, SensorEventListener {

    private boolean gameOver = false; // Bandera para controlar si el juego ha terminado

    Plataforma platform;
    private boolean isPlaying;
    private float platformSpeedX = 5; // Velocidad horizontal de las plataformas móviles

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
    private MediaPlayer jumpSound,gameOverSound,levelSound;
    private MediaPlayer backgroundMusic;
    private Bitmap backgroundImage1;
    private Bitmap backgroundImage2, backgroundImage3, backgroundImage4;
    private int backgroundCounter = 0;
    private int score=0;
    private TextView scoreTextView;

    private long startTime = System.currentTimeMillis(); // Variable para almacenar el tiempo de inicio del juego

    private long changeScreenTime = System.currentTimeMillis(); // Variable para almacenar el tiempo en que se cambió de pantalla



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
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
        platforms = new ArrayList<>();
        random = new Random();
        pou = new Pou(context, screenWidth, screenHeight, 150, 100);
        createPlatforms();
        startSensor();
        jumpSound = MediaPlayer.create(context, R.raw.salto);
        backgroundMusic = MediaPlayer.create(context, R.raw.cancion);
        gameOverSound = MediaPlayer.create(context, R.raw.perder);
        levelSound=MediaPlayer.create(context,R.raw.subenivel);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
        backgroundImage1 = BitmapFactory.decodeResource(getResources(), R.drawable.bajo_tierra);
        backgroundImage1 = Bitmap.createScaledBitmap(backgroundImage1, screenWidth, screenHeight, true);
        backgroundImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.superficie);
        backgroundImage2 = Bitmap.createScaledBitmap(backgroundImage2, screenWidth, screenHeight, true);
        backgroundImage3 = BitmapFactory.decodeResource(getResources(), R.drawable.cielo);
        backgroundImage3 = Bitmap.createScaledBitmap(backgroundImage3, screenWidth, screenHeight, true);
        backgroundImage4 = BitmapFactory.decodeResource(getResources(), R.drawable.espacio_recortado);
        backgroundImage4 = Bitmap.createScaledBitmap(backgroundImage4, screenWidth, screenHeight, true);
        scoreTextView = new TextView(context);
        scoreTextView.setX(50);
        scoreTextView.setY(50);
        scoreTextView.setTextSize(20);
        updateScoreTextView();
    }

    private void updateScoreTextView() {
        scoreTextView.setText("Score: " + score);
    }

    @Override
    public void run() {
        while (isPlaying && !gameOver) {
            update();
            draw();
            control();
            playBackgroundMusic();
        }
    }



    private void update() {
        boolean isFalling = pou.getSpeedY() > 0;
        pou.setSpeedY(pou.getSpeedY() + 0.5f);
        pou.update();

        // Si han pasado menos de 2 segundos desde el inicio del juego y el Pou cae al borde inferior de la pantalla
        if (System.currentTimeMillis() - startTime < 5000 && pou.getY() >= screenHeight - pou.getHeight() && backgroundCounter == 0) {
            // Hacer que el Pou salte como si estuviera en una plataforma
            pou.setSpeedY(-28);
            playJumpSound();
            score += 10;
        }

        // Si el Pou está en el borde superior de la pantalla, incrementar el contador de pantallas
        if (pou.getY() <= 0) {
            incrementBackgroundCounter();
            pou.setY(screenHeight - pou.getHeight());
            generateNewPlatforms();


            // Reproducir el sonido de nivel si el Pou pasa a las pantallas 3, 4 o 8
            if (backgroundCounter == 3 || backgroundCounter == 4 || backgroundCounter == 8) {
                playLevelSound();
            }

            // Actualizar el tiempo de cambio de pantalla
            changeScreenTime = System.currentTimeMillis();
        }

        boolean enContacto = false;
        for (Plataforma platform : platforms) {
            if (Rect.intersects(pou.getRect(), platform.getRect())) {
                enContacto = true;
                if (isFalling && pou.getRect().top < platform.getRect().top) {
                    pou.setSpeedY(-28);
                    playJumpSound();
                    score += 10;
                    // Verificar si estamos en la pantalla 4 o 5 y eliminar la plataforma
                    if (backgroundCounter == 4 || backgroundCounter == 5 ) {
                        platforms.remove(platform);
                    }
                    break;
                }
                if (pou.getRect().bottom > platform.getRect().bottom) {
                }
            }
        }
        if (!enContacto && isFalling) {
            pou.setSpeedY(pou.getSpeedY() + 0.5f);
        }

        // Actualizar la posición horizontal de las plataformas solo si el contador de pantallas es 7 o más
        if (backgroundCounter >= 7) {
            for (Plataforma platform : platforms) {
                if (platform.isMoving()) {
                    platform.updateHorizontalPosition();
                }
            }
        }

        // Verificar si el juego ha terminado
        checkGameOver();
    }


    private void playLevelSound() {
        if (levelSound != null) {
            levelSound.start();
        }
    }



    private void checkGameOver() {
        // Obtener el tiempo transcurrido desde el inicio del juego
        long elapsedTime = System.currentTimeMillis() - startTime;

        // Verificar si ha pasado el tiempo suficiente desde el inicio del juego y desde el último cambio de pantalla
        if (elapsedTime > 500 && System.currentTimeMillis() - changeScreenTime > 500) {
            // Si el Pou está cerca del borde inferior de la pantalla y ha pasado el tiempo necesario
            if (pou.getY() >= screenHeight - pou.getHeight() && backgroundCounter > 0) {
                playGameOverSound(); // Reproducir sonido de game over
                gameOver = true; // Establecer la bandera gameOver a true
                stopBackgroundMusic(); // Detener la música de fondo

                // Mostrar la pantalla de juego perdido
                Intent intent = new Intent(getContext(), GameOver.class);
                intent.putExtra("score", score); // Pasa el puntaje a GameOver
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }

            // Si está en la primera pantalla y ha pasado el tiempo necesario
            if (backgroundCounter == 0 && elapsedTime > 3000 && pou.getY() >= screenHeight - pou.getHeight()) {
                playGameOverSound(); // Reproducir sonido de game over
                gameOver = true; // Establecer la bandera gameOver a true
                stopBackgroundMusic(); // Detener la música de fondo

                // Mostrar la pantalla de juego perdido
                Intent intent = new Intent(getContext(), GameOver.class);
                intent.putExtra("score", score); // Pasa el puntaje a GameOver
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        }
    }



    private Bitmap vectorToBitmap(Drawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private Bitmap rotateVectorDrawable(Drawable vectorDrawable, float degrees) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees, vectorDrawable.getIntrinsicWidth() / 2f, vectorDrawable.getIntrinsicHeight() / 2f);
        canvas.concat(matrix);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private Bitmap getRotatedAirplaneBitmap() {
        Drawable airplaneDrawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_airplanemode_active_24);
        return rotateVectorDrawable(airplaneDrawable, 90);
    }

    private Bitmap getRotatedRocketBitmap() {
        Drawable airplaneDrawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_rocket_24);
        return rotateVectorDrawable(airplaneDrawable, 90);
    }

    private void drawPlatforms(Canvas canvas) {
        if (backgroundCounter >= 3 && backgroundCounter <= 5) {
            Drawable cloudDrawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_cloud_24);
            Bitmap cloudBitmap = vectorToBitmap(cloudDrawable);
            for (Plataforma platform : platforms) {
                canvas.drawBitmap(cloudBitmap, platform.getRect().left, platform.getRect().top, null);
            }
        } else if (backgroundCounter == 6 || backgroundCounter==7) {
            Bitmap rotatedAirplaneBitmap = getRotatedAirplaneBitmap();
            for (Plataforma platform : platforms) {
                if (platform.isMoving()) {
                    platform.updateHorizontalPosition(); // Actualizar la posición horizontal de la plataforma
                }
                canvas.drawBitmap(rotatedAirplaneBitmap, platform.getRect().left, platform.getRect().top, null);
            }
        }  else if (backgroundCounter >= 8) {
            Bitmap rotatedRocketBitmap = getRotatedRocketBitmap();
            for (Plataforma platform : platforms) {
                if (platform.isMoving()) {
                    platform.updateHorizontalPosition(); // Actualizar la posición horizontal de la plataforma
                }
                canvas.drawBitmap(rotatedRocketBitmap, platform.getRect().left, platform.getRect().top, null);
            }
        }else {
            // Si el contador de pantalla no coincide con ninguna de las condiciones anteriores, no dibujamos nada
        }
    }



    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            drawBackground(canvas);
            pou.draw(canvas);
            if (backgroundCounter > 3) {
                drawPlatforms(canvas);
            } else {
                for (Plataforma platform : platforms) {
                    platform.draw(canvas);
                }
            }
            drawScoreTextView(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawScoreTextView(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        canvas.drawText("Score: " + score, 50, 100, paint);
    }

    private void control() {
        try {
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
            float xAcceleration = event.values[0];
            float yAcceleration = event.values[1];
            double inclinacion = Math.atan2(-xAcceleration, yAcceleration) * 180 / Math.PI;
            float velocidadHorizontal = 10;
            pou.setSpeedX(velocidadHorizontal * (float)Math.sin(Math.toRadians(inclinacion)));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startSensor() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSensor() {
        sensorManager.unregisterListener(this);
    }

    private void createPlatforms() {
        int numPlatformsToCreate = 10;
        int platformWidth = 120;
        int platformHeight = 20;
        int minDistanceBetweenPlatforms = 350;
        int minX = 0;
        int maxX = screenWidth - platformWidth;
        int maxY = screenHeight - platformHeight;
        int lastPlatformX = 0;
        int numPlatformsCreated = 0;
        while (numPlatformsCreated < numPlatformsToCreate) {
            int platformX = random.nextInt(maxX - minX + 1) + minX;
            if (Math.abs(platformX - lastPlatformX) < minDistanceBetweenPlatforms) {
                if (platformX > lastPlatformX) {
                    platformX += minDistanceBetweenPlatforms;
                } else {
                    platformX -= minDistanceBetweenPlatforms;
                }
            }
            int platformY = random.nextInt(maxY);
            boolean willMove = random.nextBoolean(); // Decide si la plataforma se moverá
            Plataforma newPlatform = new Plataforma(platformX, platformY, platformX + platformWidth, platformY + platformHeight, willMove, screenWidth);
            boolean overlap = false;
            for (Plataforma existingPlatform : platforms) {
                if (Rect.intersects(newPlatform.getRect(), existingPlatform.getRect())) {
                    overlap = true;
                    break;
                }
            }
            if (!overlap) {
                platforms.add(newPlatform);
                numPlatformsCreated++;
                lastPlatformX = platformX;
            }
        }
    }

    private void generateNewPlatforms() {
        platforms.clear();
        int numPlatformsToCreate = 10; // Número de plataformas por defecto
        // Si estamos en la primera pantalla, incrementamos el número de plataformas
        if (backgroundCounter == 0) {
            numPlatformsToCreate += 5; // Aumentamos el número de plataformas a 15
        }

        int platformWidth = 120;
        int platformHeight = 20;
        int minDistanceBetweenPlatforms = 350;
        int minX = 0;
        int maxX = screenWidth - platformWidth;
        int minY = screenHeight - platformHeight;
        int verticalDistanceBetweenPlatforms = (minY - minDistanceBetweenPlatforms) / (numPlatformsToCreate - 1);

        for (int i = 0; i < numPlatformsToCreate; i++) {
            int platformX = random.nextInt(maxX - minX + 1) + minX;
            int platformY = minY - i * verticalDistanceBetweenPlatforms;
            boolean willMove = random.nextBoolean(); // Decide si la plataforma se moverá
            Plataforma newPlatform = new Plataforma(platformX, platformY, platformX + platformWidth, platformY + platformHeight, willMove, screenWidth);
            platforms.add(newPlatform);
        }
    }


    private void incrementBackgroundCounter() {
        backgroundCounter++;
    }

    private void playJumpSound() {
        if (jumpSound != null) {
            jumpSound.start();
        }
    }

    private void playBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.start();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
            backgroundMusic.release(); // Libera los recursos del reproductor de música
            backgroundMusic = null; // Establece el objeto MediaPlayer a null para indicar que ya no está en uso
        }
    }


    private void playGameOverSound() {
        if (gameOverSound != null) {
            gameOverSound.start();
        }
    }

    private void drawBackground(Canvas canvas) {
        if (backgroundCounter == 3) {
            canvas.drawBitmap(backgroundImage2, 0, 0, null);
        } else if (backgroundCounter >= 4 && backgroundCounter <= 7) {
            // Dibujar un fondo de color azul cielo
            canvas.drawColor(Color.parseColor("#87CEEB")); // Este código de color representa el azul cielo
        } else if (backgroundCounter >= 8) {
            // Dibujar el fondo de la imagen 4
            canvas.drawBitmap(backgroundImage4, 0, 0, null);
        } else if (backgroundCounter < 3) {
            canvas.drawBitmap(backgroundImage1, 0, 0, null);
        }
    }




}
