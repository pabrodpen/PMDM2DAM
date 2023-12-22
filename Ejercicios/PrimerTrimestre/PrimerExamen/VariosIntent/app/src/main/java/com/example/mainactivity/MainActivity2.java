package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    EditText dNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);//IMP EL ACTIVITYMAIN2

        dNombre=findViewById(R.id.editTextNombre2);

        Button aceptar=findViewById(R.id.b1Aceptar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoBotonAceptar1();
            }
        });

        Button cancelar=findViewById(R.id.b1Cancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoBotonCancelar1();
            }
        });

    }

    public void metodoBotonAceptar1(){
        Intent intent=new Intent(this, MainActivity.class);
        Bundle b=new Bundle();

        b.putString("EtiquetaNombre",dNombre.getText().toString());


        intent.putExtras(b);
        startActivity(intent);
    }

    public void metodoBotonCancelar1(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
