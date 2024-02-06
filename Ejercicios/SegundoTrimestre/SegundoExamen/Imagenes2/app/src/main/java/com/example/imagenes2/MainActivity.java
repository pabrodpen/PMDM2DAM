package com.example.imagenes2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);

        infoTextView = findViewById(R.id.infoTextView);


        // Cargar las imágenes desde recursos como Bitmap
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.illojuan);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.knekro);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.ibai);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.djmario);



        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 400, 400, true);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, 400, 400, true);
        bitmap3 = Bitmap.createScaledBitmap(bitmap3, 400, 400, true);
        bitmap4 = Bitmap.createScaledBitmap(bitmap4, 400, 400, true);


        // Configurar los ImageButton con las imágenes Bitmap
        imageButton1.setImageBitmap(bitmap1);
        imageButton2.setImageBitmap(bitmap2);
        imageButton3.setImageBitmap(bitmap3);
        imageButton4.setImageBitmap(bitmap4);


        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTextView.setText("ILLOJUAN");
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTextView.setText("KNEKRO");
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTextView.setText("IBAI");
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTextView.setText("DJMARIO");
            }
        });


    }
}

