package com.example.imagenes1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnPrevious, btnNext;

    private int[] imageIds = {R.drawable.walter, R.drawable.anuel, R.drawable.messi};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        updateImage();

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreviousImage();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextImage();
            }
        });
    }

    private void updateImage() {
        imageView.setImageResource(imageIds[currentIndex]);
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + imageIds.length) % imageIds.length;
        updateImage();
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % imageIds.length;
        updateImage();
    }
}
