package com.example.pousaltarin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button rulesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        rulesButton = findViewById(R.id.rulesButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar el juego
                startGame();
            }
        });

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ver las reglas del juego
                showRules();
            }
        });
    }

    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class); // Cambiado de GameView a GameActivity
        startActivity(intent);
    }

    private void showRules() {
        Intent intent = new Intent(this, Reglas.class);
        startActivity(intent);
    }
}
