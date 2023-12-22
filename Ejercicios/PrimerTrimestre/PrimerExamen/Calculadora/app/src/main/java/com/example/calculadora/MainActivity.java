package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,bSum,bRes,bMult,bDiv,bC,bR,bResultado,bComa;
    EditText t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b0=findViewById(R.id.boton0);
        b1=findViewById(R.id.boton1);
        b2=findViewById(R.id.boton2);
        b3=findViewById(R.id.boton3);
        b4=findViewById(R.id.boton4);
        b5=findViewById(R.id.boton5);
        b6=findViewById(R.id.boton6);
        b7=findViewById(R.id.boton7);
        b8=findViewById(R.id.boton8);
        b9=findViewById(R.id.boton9);
        bSum=findViewById(R.id.botonSumar);
        bRes=findViewById(R.id.botonRestar);
        bMult=findViewById(R.id.botonMultiplicar);
        bDiv=findViewById(R.id.botonDivision);
        bResultado=findViewById(R.id.botonIgual);
        t=findViewById(R.id.pantalla);
        bR=findViewById(R.id.botonR);
        bC=findViewById(R.id.botonC);

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b0.getText().toString();
                t.setText(aux);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b1.getText().toString();
                t.setText(aux);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b2.getText().toString();
                t.setText(aux);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b3.getText().toString();
                t.setText(aux);
            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b4.getText().toString();
                t.setText(aux);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b5.getText().toString();
                t.setText(aux);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b6.getText().toString();
                t.setText(aux);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b7.getText().toString();
                t.setText(aux);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b8.getText().toString();
                t.setText(aux);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+b9.getText().toString();
                t.setText(aux);
            }
        });

        bSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+" "+bSum.getText().toString()+" ";
                t.setText(aux);
            }
        });

        bRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+" "+bRes.getText().toString()+" ";
                t.setText(aux);
            }
        });

        bMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+" "+bMult.getText().toString()+" ";
                t.setText(aux);
            }
        });

        bDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aux=t.getText().toString()+" "+bDiv.getText().toString()+" ";
                t.setText(aux);
            }
        });

        bR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String borrado=t.getText().toString().substring(0,t.length()-1);//cogemos
                //desde el principio hasta el penultimo caracter(eliminamos el ultimo)
                t.setText(borrado);
            }
        });

        bC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.setText("");
            }
        });

        bResultado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String[]elementos=t.getText().toString().split(" ");
                double resultado=0;

                String n1=elementos[0];
                String operador=elementos[1];
                String n2=elementos[2];

                if(n1.contains(",")){
                   n1= n1.replace(",",".");//imp el n1=
                }else if(n2.contains(",")){
                    n2=n2.replace(",",".");//imp en n2=
                }
                    if(operador.equals("+")){
                        resultado=Double.parseDouble(n1)+Double.parseDouble(n2);
                    }else if(operador.equals("-")){
                        resultado=Double.parseDouble(n1)-Double.parseDouble(n2);
                    }else if(operador.equals("x")){
                        resultado=Double.parseDouble(n1)*Double.parseDouble(n2);
                    }else if(operador.equals(":")){
                        resultado=Double.parseDouble(n1)/Double.parseDouble(n2);
                    }

                t.setText(String.valueOf(resultado));
            }
        });

        bComa=findViewById(R.id.botonComa);
        bComa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux=t.getText().toString()+bComa.getText().toString();
                t.setText(aux);
            }
        });

    }
}