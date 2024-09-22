package com.example.pablorodriguezex1ev1curso2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button bReglas,bJuego;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bReglas=findViewById(R.id.buttonReglas);
        bJuego=findViewById(R.id.buttonAcceder);
        text=findViewById(R.id.editTextText);

        bReglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoBotonReglas();
            }
        });

        bJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoBotonAcceder();
            }
        });


    }
    public void metodoBotonReglas(){
        Intent intent= new Intent(MainActivity. this , Reglas. class );

        startActivity(intent);
    }

   public void metodoBotonAcceder(){
        Intent intent= new Intent(MainActivity. this , Juego. class );
       Bundle b= new Bundle();
       intent.putExtra( "EtiquetaUsuario" ,text.getText().toString());
        startActivity(intent);
    }


}