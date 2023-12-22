package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity3 extends AppCompatActivity {

    EditText dEdad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);//IMP EL ACTIVITYMAIN2

        dEdad=findViewById(R.id.editTextEdad2);

        Button aceptar2=findViewById(R.id.b2Aceptar);
        aceptar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoAceptar2();
            }
        });

        Button cancelar2=findViewById(R.id.b2Cancelar);
        cancelar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoCancelar2();
            }
        });
    }

    public void metodoAceptar2(){
        Intent intent=new Intent(this, MainActivity.class);

        Bundle b=new Bundle();
        b.putString("EtiquetaEdad",String.valueOf(dEdad.getText()));//IMP COMO SE PONE PARA CONVERTIR
        //DE UN STRING A UN INT

        intent.putExtras(b);
        startActivity(intent);
    }

    public void metodoCancelar2(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
