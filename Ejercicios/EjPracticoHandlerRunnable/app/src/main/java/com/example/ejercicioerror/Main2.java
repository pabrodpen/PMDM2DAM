package com.example.ejercicioerror;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2 extends AppCompatActivity {

    private ImageView mImageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        mImageView2=findViewById(R.id.imageView2);
        mImageView2.setImageResource(R.drawable.sevilla);




    }
}