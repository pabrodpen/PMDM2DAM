package com.example.pousaltarin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        Button restartButton = findViewById(R.id.restartButton);
        Button mainMenuButton = findViewById(R.id.mainMenuButton);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reiniciar el juego
                Intent intent = new Intent(GameOver.this, GameActivity.class);
                startActivity(intent);
                finish(); // Finalizar esta actividad para evitar que vuelva al presionar el botón Atrás
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir al menú principal
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finalizar esta actividad para evitar que vuelva al presionar el botón Atrás
            }
        });
    }
}
