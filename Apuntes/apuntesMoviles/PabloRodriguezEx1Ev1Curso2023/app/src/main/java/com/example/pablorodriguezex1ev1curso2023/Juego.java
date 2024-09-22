package com.example.pablorodriguezex1ev1curso2023;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Juego extends AppCompatActivity {

    TextView t;

    TableLayout tablero;
    Button bVolver,bReiniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);

        Log.d("Juego", "onCreate Called"); // Agrega esta línea

    String usuario=getIntent().getStringExtra( "EtiquetaUsuario" );
        Log.d("Juego", "Usuario: " + usuario); // Agrega esta línea



        t=findViewById(R.id.textView4);
        t.setText(usuario);






        bVolver=findViewById(R.id.buttonVolver);

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoBotonVolver();
            }
        });

        bReiniciar=findViewById(R.id.buttonReiniciar);
        bReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciar();
            }
        });
    }


    public void metodoBotonVolver(){
        Intent intent= new Intent(Juego. this , MainActivity. class );

        startActivity(intent);
    }

    public void reiniciar(){
        GridLayout g = findViewById(R.id.grid);

        for (int i = 0; i < g.getChildCount(); i++) {
            if (g.getChildAt(i) instanceof Button) {
                //recorre el GridLayout y si ve que es un boton
                //mediante el instaceof cambia el texto a _
                Button button = (Button) g.getChildAt(i);
                button.setText("___");
            }
        }

    }
}


