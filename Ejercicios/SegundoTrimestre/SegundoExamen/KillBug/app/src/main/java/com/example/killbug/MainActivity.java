package com.example.killbug;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView[] bugImages = new ImageView[5];
    private Drawable[] normalDrawables = new Drawable[5];
    private Drawable[] squashedDrawables = new Drawable[5];
    private boolean[] isSquashed = new boolean[5];
    private MediaPlayer squishSound;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        squishSound = MediaPlayer.create(this, R.raw.golpe);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                generateRandomBugs();
                handler.postDelayed(this, 2147483647); // Máximo valor de tiempo
                // Cambia la velocidad de generación de insectos

            }
        };
        handler.postDelayed(runnable, 0);

        bugImages[0] = findViewById(R.id.bug1);
        bugImages[1] = findViewById(R.id.bug2);
        bugImages[2] = findViewById(R.id.bug3);
        bugImages[3] = findViewById(R.id.bug4);
        bugImages[4] = findViewById(R.id.bug5);

        normalDrawables[0] = getResources().getDrawable(R.drawable.abeja);
        normalDrawables[1] = getResources().getDrawable(R.drawable.bicho);
        normalDrawables[2] = getResources().getDrawable(R.drawable.bicho2);
        normalDrawables[3] = getResources().getDrawable(R.drawable.hormiga);
        normalDrawables[4] = getResources().getDrawable(R.drawable.mariquita);

        squashedDrawables[0] = getResources().getDrawable(R.drawable.aplastado1);
        squashedDrawables[1] = getResources().getDrawable(R.drawable.aplastado2);
        squashedDrawables[2] = getResources().getDrawable(R.drawable.aplastado3);
        squashedDrawables[3] = getResources().getDrawable(R.drawable.aplastado4);
        squashedDrawables[4] = getResources().getDrawable(R.drawable.aplastado5);

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            bugImages[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isSquashed[finalI]) {
                        squishSound.start();
                        bugImages[finalI].setImageDrawable(squashedDrawables[finalI]);
                        isSquashed[finalI] = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Después de 1 segundo, cambiar la imagen a la normal
                                bugImages[finalI].setImageDrawable(normalDrawables[finalI]);
                                isSquashed[finalI] = false;
                                ((ViewGroup) bugImages[finalI].getParent()).removeView(bugImages[finalI]);
                            }
                        }, 1000); // Mostrar la imagen aplastada durante 1 segundo
                    }
                }
            });

        }
    }

    private void generateRandomBugs() {
        Random random = new Random();

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        for (int i = 0; i < 5; i++) {
            final ImageView bugImage = bugImages[i];

            // Inicializar la posición del insecto
            float startX = random.nextInt(screenWidth);
            float startY = random.nextInt(screenHeight);

            // Ajustar la posición del insecto para que esté dentro de los límites de la pantalla
            if (startX > screenWidth - bugImage.getWidth()) {
                startX = screenWidth - bugImage.getWidth();
            }
            if (startY > screenHeight - bugImage.getHeight()) {
                startY = screenHeight - bugImage.getHeight();
            }

            bugImage.setX(startX);
            bugImage.setY(startY);

            // Hacer que los insectos sean visibles
            bugImage.setVisibility(View.VISIBLE);

            // Calcular la dirección inicial del movimiento
            final float targetX = random.nextFloat() * screenWidth;
            final float targetY = random.nextFloat() * screenHeight;
            final float dx = targetX - startX;
            final float dy = targetY - startY;
            final float distance = (float) Math.sqrt(dx * dx + dy * dy);
            final float speed = 1.5f; // Velocidad de movimiento del insecto

            // Actualizar la posición del insecto de forma continua
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Mover el insecto hacia el objetivo con una fracción de la distancia en cada iteración
                    float x = bugImage.getX();
                    float y = bugImage.getY();
                    if (distance > 0) {
                        float factor = speed / distance;
                        x += dx * factor;
                        y += dy * factor;
                    }

                    // Actualizar la posición del insecto
                    bugImage.setX(x);
                    bugImage.setY(y);

                    // Programar la próxima actualización de posición
                    handler.postDelayed(this, 10); // Actualizar cada 10 milisegundos
                }
            });
        }
    }


}
