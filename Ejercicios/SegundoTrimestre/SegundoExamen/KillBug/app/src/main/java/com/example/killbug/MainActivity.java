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
                handler.postDelayed(this, 3000); // Cambia la velocidad de generación de insectos
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
        normalDrawables[2] = getResources().getDrawable(R.drawable.bicho3);
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
            int x = random.nextInt(screenWidth);
            int y = random.nextInt(screenHeight);

            // Ajustar la posición del insecto para que esté dentro de los límites de la pantalla
            if (x > screenWidth - bugImages[i].getWidth()) {
                x = screenWidth - bugImages[i].getWidth();
            }
            if (y > screenHeight - bugImages[i].getHeight()) {
                y = screenHeight - bugImages[i].getHeight();
            }

            // Establecer la posición del insecto
            bugImages[i].setX(x);
            bugImages[i].setY(y);

            // Hacer que los insectos sean visibles
            bugImages[i].setVisibility(View.VISIBLE);
        }

        // Programar la eliminación de las imágenes aplastadas
        for (int i = 0; i < 5; i++) {
            if (isSquashed[i]) {
                final int finalI = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((ViewGroup) bugImages[finalI].getParent()).removeView(bugImages[finalI]);
                        isSquashed[finalI] = false;
                    }
                }, 500); // Eliminar el elemento aplastado después de 1 segundo
            }
        }
    }



}
