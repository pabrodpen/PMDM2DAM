package com.example.ex1ev1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText t;
    Button bReglas,bAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t=findViewById(R.id.editTextText);
        bReglas=findViewById(R.id.buttonReglas);
        bAcceder=findViewById(R.id.buttonAcceder);

        bReglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Reglas.class);
                startActivity(intent);
            }
        });


        bAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("NOMBRE",String.valueOf(t.getText()));
                Intent intent=new Intent(MainActivity.this,Juego.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}