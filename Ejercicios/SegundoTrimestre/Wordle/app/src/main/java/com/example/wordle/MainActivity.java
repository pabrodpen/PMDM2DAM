package com.example.wordle;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid1, grid2;
    //vamos a usar 2 GridLayout, 1 para los botones y otro para los editText
    private StringBuilder palabra;
    private TextView resultadoTextView;
    int verde,naranja,gris,casillaActual,indiceLetra,blanco,indicePalabra;
    String[]palabras;
    Resources res;

    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid1 = findViewById(R.id.grid);
        grid2 = findViewById(R.id.grid2);
        t=findViewById(R.id.textView);
        verde = ContextCompat.getColor(this, R.color.green);
        naranja = ContextCompat.getColor(this, R.color.orange);
        gris = ContextCompat.getColor(this, R.color.grey);
        blanco=ContextCompat.getColor(this,R.color.white);

        //en values usamos un string-array para que la palabra escogida
        //sea al azar
        res=getResources();
        palabras=res.getStringArray(R.array.palabrasAleatorias);
        indicePalabra=(int)(Math.random()*palabras.length);
        palabra= new StringBuilder(palabras[indicePalabra]);




        // Crear 16 EditText iniciales
        for (int i = 0; i < 20; i++) {
            EditText editText = new EditText(this);
            editText.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
            editText.setId(View.generateViewId());
            grid1.addView(editText, i);
        }

        // 29 botones de teclado
        for (int i = 0; i < 29; i++) {
            Button b = new Button(this);
            b.setLayoutParams(new ViewGroup.LayoutParams(90, 100));

            switch (i) {
                case 0:
                    b.setText("Q");
                    break;
                case 1:
                    b.setText("W");
                    break;
                case 2:
                    b.setText("E");
                    break;
                case 3:
                    b.setText("R");
                    break;
                case 4:
                    b.setText("T");
                    break;
                case 5:
                    b.setText("Y");
                    break;
                case 6:
                    b.setText("U");
                    break;
                case 7:
                    b.setText("I");
                    break;
                case 8:
                    b.setText("O");
                    break;
                case 9:
                    b.setText("P");
                    break;
                case 10:
                    b.setText("A");
                    break;
                case 11:
                    b.setText("S");
                    break;
                case 12:
                    b.setText("D");
                    break;
                case 13:
                    b.setText("F");
                    break;
                case 14:
                    b.setText("G");
                    break;
                case 15:
                    b.setText("H");
                    break;
                case 16:
                    b.setText("J");
                    break;
                case 17:
                    b.setText("K");
                    break;
                case 18:
                    b.setText("L");
                    break;
                case 19:
                    b.setText("Ñ");
                    break;
                case 20:
                    b.setLayoutParams(new ViewGroup.LayoutParams(160, 100));
                    b.setText("ENVIAR");
                    break;
                case 21:
                    b.setText("Z");
                    break;
                case 22:
                    b.setText("X");
                    break;
                case 23:
                    b.setText("C");
                    break;
                case 24:
                    b.setText("V");
                    break;
                case 25:
                    b.setText("B");
                    break;
                case 26:
                    b.setText("N");
                    break;
                case 27:
                    b.setText("M");
                    break;
                case 28:
                    b.setLayoutParams(new ViewGroup.LayoutParams(160, 100));
                    b.setText("BORRAR");
                    break;
            }


            b.setId(View.generateViewId());

            grid2.addView(b, i);

            casillaActual=0;
            indiceLetra=0;

            //metodos de los botones:ENVIAR Y BORRAR
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (b.getText().equals("ENVIAR")) {//al darle al boton evaluamos la palabra
                        StringBuilder intento = new StringBuilder();
                        boolean encontrado = false;

                        // Buscamos el primer editText vacío para empezar
                        //el metodo en esa casilla
                        for (int i = 0; i < grid1.getChildCount() && !encontrado; i++) {
                            EditText editText = (EditText) grid1.getChildAt(i);
                            if (editText.getText().toString().equals(" ")) {
                                casillaActual = i;
                                encontrado = true;
                            }
                        }

                        //recogemos la letra de cada editText y lo concatenamos
                        //formando el String intento
                        for (int i = casillaActual; i < palabra.length() + casillaActual; i++) {
                            EditText editText = (EditText) grid1.getChildAt(i);
                            intento.append(editText.getText().toString());
                        }

                        //si acertamos la palabra
                        if (intento.toString().equals(palabra.toString())) {


                            t.setText("ENHORABUENA, HAS GANADO!");//hemos ganado
                            reiniciarJuego();


                        } else {//si no acertamos buscamos cada letra
                            for (int i = casillaActual; i < palabra.length() + casillaActual; i++) {
                                EditText editText = (EditText) grid1.getChildAt(i);

                                //en verde si la letra esta en el lugar de la palabra
                                if (intento.charAt(i - casillaActual) == palabra.charAt(i - casillaActual)) {
                                    editText.setBackgroundColor(verde);

                                    // Buscamos el button para cambiar a color verde
                                    for (int j = 0; j < grid2.getChildCount(); j++) {
                                        Button button = (Button) grid2.getChildAt(j);
                                        if (button.getText().toString().equals(editText.getText().toString())) {
                                            button.setBackgroundColor(verde);
                                        }
                                    }


                                    //si la letra esta en la palabra cambiamos a naranja
                                } else if (palabra.indexOf(String.valueOf(intento.charAt(i - casillaActual))) != -1) {
                                    editText.setBackgroundColor(naranja);

                                    // Buscamos el button para cambiar a color naranja
                                    for (int j = 0; j < grid2.getChildCount(); j++) {
                                        Button button = (Button) grid2.getChildAt(j);
                                        if (button.getText().toString().equals(editText.getText().toString())) {
                                            button.setBackgroundColor(naranja);
                                        }
                                    }
                                } else {//si la letra no esta en la palabra cambiamos a gris
                                    editText.setBackgroundColor(gris);

                                    // Buscamos el button para cambiar a color gris
                                    for (int j = 0; j < grid2.getChildCount(); j++) {
                                        Button button = (Button) grid2.getChildAt(j);
                                        if (button.getText().toString().equals(editText.getText().toString())) {
                                            button.setBackgroundColor(gris);
                                        }
                                    }
                                }
                            }
                        }
                        //despues de evaluar la fila de editText cambiamos la casilla actual para valorar la siguiente fila
                        casillaActual += palabra.length();
                      if (casillaActual >= 16) {//condicion para perder->superar el numero de editText posibles(ya se han completado todas las filas)
                            t.setText("LO SIENTO, HAS PERDIDO. LA PALABRA ERA " + palabra);
                            reiniciarJuego();
                        }



                    } else if (b.getText().equals("BORRAR")) {//si el boton es BORRAR cambiamos el text a ""
                        //mediante el indice de letra actual
                        EditText editText = (EditText) grid1.getChildAt(indiceLetra);
                        editText.setText("");
                        //editText.setBackgroundColor(blanco);
                        indiceLetra--;

                    } else {//si no es el boton ENVIAR ni BORRAR, es un boton normal de letra
                        //asi que al pulsar el boton asignamos el texto(la letra) al editText actual(indiceLetra)
                        indiceLetra = asignarLetraAEditText(b.getText().toString(), indiceLetra);
                        indiceLetra++;
                    }
                }
            });



        }


    }

    private int asignarLetraAEditText(String letra,int ind) {
        EditText editText = (EditText) grid1.getChildAt(ind);
        editText.setText(letra);
        return ind;
    }



    public void reiniciarJuego(){
        for(int i=0;i<20;i++){
            EditText editText= (EditText) grid1.getChildAt(i);
            editText.setEnabled(false);//hacemos que no sen puedan marcar
        }
        for(int i=0;i<29;i++){
            Button button= (Button) grid2.getChildAt(i);
            button.setEnabled(false);
        }
    }

}
