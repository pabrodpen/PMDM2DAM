package com.example.killbug;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

        squishSound = MediaPlayer.create(this, R.raw.squish);

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
                                bugImages[finalI].setImageDrawable(normalDrawables[finalI]);
                                isSquashed[finalI] = false;
                            }
                        }, 2000); // Tiempo antes de que vuelva a aparecer el insecto
                    }
                }
            });
        }
    }

    private void generateRandomBugs() {
        Random random = new Random();
        int numBugs = random.nextInt(5) + 1; // Genera de 1 a 5 bichos
        for (int i = 0; i < 5; i++) {
            if (i < numBugs) {
                bugImages[i].setVisibility(View.VISIBLE);
            } else {
                bugImages[i].setVisibility(View.INVISIBLE);
            }
        }
    }
}
