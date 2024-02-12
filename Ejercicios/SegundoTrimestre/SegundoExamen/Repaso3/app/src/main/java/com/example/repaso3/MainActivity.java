package com.example.repaso3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView[] imageViews = new ImageView[5];
    Drawable[] bichos = new Drawable[5];
    Drawable[] aplastados = new Drawable[5];
    boolean[] estaAplastado = new boolean[5];

    TextView puntaje;
    Handler handler;
    int screenWidth, screenHeight;
    int score=0;

    int bichosAplastados=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        puntaje=findViewById(R.id.textView);

        imageViews[0] = findViewById(R.id.imageView);
        imageViews[1] = findViewById(R.id.imageView2);
        imageViews[2] = findViewById(R.id.imageView3);
        imageViews[3] = findViewById(R.id.imageView4);
        imageViews[4] = findViewById(R.id.imageView5);

        bichos[0] = getResources().getDrawable(R.drawable.abeja);
        bichos[1] = getResources().getDrawable(R.drawable.bicho);
        bichos[2] = getResources().getDrawable(R.drawable.bicho2);
        bichos[3] = getResources().getDrawable(R.drawable.hormiga);
        bichos[4] = getResources().getDrawable(R.drawable.mariquita);

        aplastados[0] = getResources().getDrawable(R.drawable.aplastado1);
        aplastados[1] = getResources().getDrawable(R.drawable.aplastado2);
        aplastados[2] = getResources().getDrawable(R.drawable.aplastado3);
        aplastados[3] = getResources().getDrawable(R.drawable.aplastado4);
        aplastados[4] = getResources().getDrawable(R.drawable.aplastado5);

        MediaPlayer[] sonidos = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.sonido1),
                MediaPlayer.create(this, R.raw.sonido2),
                MediaPlayer.create(this, R.raw.sonido3),
                MediaPlayer.create(this, R.raw.sonido4),
                MediaPlayer.create(this, R.raw.sonido5)
        };


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(bichosAplastados<5){
                    Toast.makeText(getBaseContext(),"FIN DEL JUEGO",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        },10000);




        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        for (int i = 0; i < 5; i++) {
            final ImageView bugImage = imageViews[i];

            int finalI = i;
            bugImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sonidos[finalI].start();
                    bugImage.setImageDrawable(aplastados[finalI]);
                    score+=20;
                    bichosAplastados++;
                    puntaje.setText("Puntaje:"+score);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((ViewGroup) imageViews[finalI].getParent()).removeView(imageViews[finalI]);
                        }
                    },1000);
                }
            });


            // Generar posición inicial aleatoria para cada imagen
             float startX = (float) (Math.random() * screenWidth);
             float startY = (float) (Math.random() * screenHeight);

            // Generar velocidad aleatoria para cada imagen
             final float[] velocidadX = {(float) (Math.random() * 5)}; // Cambia el rango según tu necesidad
             final float[] velocidadY = {(float) (Math.random() * 5)}; // Cambia el rango según tu necesidad

            handler.post(new Runnable() {
                int dt = 10;

                @Override
                public void run() {
                    // Calcular la nueva posición del insecto
                    float x = bugImage.getX() + velocidadX[0] * dt;
                    float y = bugImage.getY() + velocidadY[0] * dt;

                    // Ajustar la posición del insecto para que esté dentro de los límites de la pantalla
                    if (x > screenWidth - bugImage.getWidth() || x < 0) {
                        velocidadX[0] = -velocidadX[0]; // Cambiar dirección en el eje x
                    }
                    if (y > screenHeight - bugImage.getHeight() || y < 0) {
                        velocidadY[0] = -velocidadY[0]; // Cambiar dirección en el eje y
                    }

                    // Actualizar la posición del insecto
                    bugImage.setX(x);
                    bugImage.setY(y);

                    // Programar la próxima actualización
                    handler.postDelayed(this, 200); // Actualizar cada 200 milisegundos
                }
            });


            findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Verificar si el clic fue dentro de algún insecto
                    if (!clickDentro(v)) {
                        score -= 5;
                        puntaje.setText("Puntaje:"+score);
                        Toast.makeText(MainActivity.this, "¡Has perdido 5 puntos!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public boolean clickDentro(View view) {
        Rect rect = new Rect();
        for (ImageView imageView : imageViews) {
            imageView.getHitRect(rect);
            if (rect.contains((int) view.getX(), (int) view.getY())) {
                return true;
            }
        }
        return false;
    }

}
