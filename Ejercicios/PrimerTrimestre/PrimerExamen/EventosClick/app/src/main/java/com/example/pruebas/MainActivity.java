package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton boton2;
    private  TextView texto1,texto2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        texto1=(TextView) findViewById(R.id.TextView1);
        texto2=(TextView) findViewById(R.id.TextView2);

        boton2=(ImageButton) findViewById(R.id.bot2);


        Button button= (Button) findViewById(R.id.boton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiaColor();
            }
        });



        // Establece el fondo del diseño principal
        //findViewById(android.R.id.content).setBackgroundResource(R.drawable.escudo_sevilla);


        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    cambiaMayus();
            }
        });
    }
    private void cambiaColor(){
        texto1.setTextColor(Color.RED);
        texto2.setTextColor(Color.BLUE);
    }

    private void cambiaMayus(){
        String aux1=texto1.getText().toString();
        String aux2=texto2.getText().toString();
        //cogemos los valores de los textos con 2 aux
        aux1=aux1.toUpperCase();
        aux2=aux2.toUpperCase();
        //convertimos los aux a mayusculas
        texto1.setText(aux1);
        texto2.setText(aux2);
        //cambiamos los textos a los aux
    }

}