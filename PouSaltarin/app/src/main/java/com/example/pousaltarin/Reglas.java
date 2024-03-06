package com.example.pousaltarin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Reglas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglas);

        // Aquí puedes configurar el texto de las reglas del juego
        TextView tvReglas = findViewById(R.id.rulesContentTextView);
        tvReglas.setText("Reglas del juego:\n\n1. Salta sobre las plataformas para subir.\n2. Evita caer al vacío.\n3. ¡Diviértete!");

        Button bVolver=findViewById(R.id.backButton);

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Reglas.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
