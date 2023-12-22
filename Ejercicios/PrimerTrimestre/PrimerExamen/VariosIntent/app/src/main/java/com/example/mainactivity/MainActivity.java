package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {

    //IMP DECLARAR AQUI LOS EDITTEXT
    EditText datosNombre,datosEdad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datosNombre=findViewById(R.id.editTextTNombre);//IMP RELACIONARLOS CON EL ID
        datosEdad=findViewById(R.id.editTextEdad);

        String nombre=getIntent().getStringExtra("EtiquetaNombre");
        String edad=getIntent().getStringExtra("EtiquetaEdad");
        datosNombre.setText(nombre);
        datosEdad.setText(edad);


        Button bNombre=findViewById(R.id.botonNombre);//PONEMOS LOS METODOS EN LOS BOTONES

        bNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    metodoBotonNombre();
            }
        });

        Button bEdad=findViewById(R.id.botonEdad);

        bEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoBotonEdad();
            }
        });

    }

    public void metodoBotonNombre(){
        Intent intent=new Intent(MainActivity.this, MainActivity2.class);

        Bundle b=new Bundle();
        b.putString("EtiquetaNombre",datosNombre.getText().toString());//recogemos los datos
        /*Un Bundle es una estructura de datos utilizada para pasar datos entre componentes de una aplicación Android,
        como actividades, fragmentos e incluso entre distintas aplicaciones en ciertos casos.
        Un Bundle es un tipo de contenedor que almacena pares clave-valor de datos. Cada dato se asocia con una clave única,
        lo que facilita la recuperación de datos específico*/

        intent.putExtras(b);//los pasamos los datos
        startActivity(intent);

    }

    public void metodoBotonEdad(){
        Intent intent=new Intent(MainActivity.this, MainActivity3.class);

        Bundle b=new Bundle();
        b.putString("EtiquetaEdad",String.valueOf(datosEdad.getText()));//IMP COMO SE PONE PARA CONVERTIR
        //DE UN STRING A UN INT
        intent.putExtras(b);
        startActivity(intent);
    }

}