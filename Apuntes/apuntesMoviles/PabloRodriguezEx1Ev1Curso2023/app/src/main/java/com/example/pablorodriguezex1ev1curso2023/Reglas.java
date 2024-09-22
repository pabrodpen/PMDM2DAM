package com.example.pablorodriguezex1ev1curso2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Reglas extends AppCompatActivity {


    Button bVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reglas);

        bVolver=findViewById(R.id.buttonVolverInicio);

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoBotonVolver();
            }
        });
    }

    public void metodoBotonVolver(){
        Intent intent= new Intent(Reglas. this , MainActivity. class );

        startActivity(intent);
    }
}
