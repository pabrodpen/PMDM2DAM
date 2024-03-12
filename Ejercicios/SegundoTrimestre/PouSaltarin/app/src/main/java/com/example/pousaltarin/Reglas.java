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
        tvReglas.setText("Reglas del juego:\n\n" +
                "1. Salta sobre las plataformas para subir.\n" +
                "2. Para saltar a un lado o a otro, gira el móvil.\n" +
                "3. Evita caer al vacío.\n" +
                "4. Suma todos los puntos posibles.\n" +
                "5. Cuidado con las nubes, al tocarlas desaparecen.\n" +
                "6. ¡Diviértete!");

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
