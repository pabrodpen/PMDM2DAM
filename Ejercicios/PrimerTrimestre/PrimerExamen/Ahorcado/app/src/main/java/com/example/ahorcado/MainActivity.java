package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    TextView textoAciertos;
    EditText editText;
    int fallos=0;
    TextView textoIntro;
    ImageView imagen;
    Resources res;
    String[] palabras;


    int indice;

    String letraIntroducida;

    String palabraSeleccionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textoAciertos=findViewById(R.id.textAciertos);
        editText=findViewById(R.id.editTextPalabra);
        textoIntro=findViewById(R.id.textView);
        imagen=findViewById(R.id.imgMunieco);
        res = getResources();
        palabras = res.getStringArray(R.array.palabrasAleatorias);//cogemos el string[]
        indice=(int)(Math.random()*palabras.length);
        palabraSeleccionada=palabras[indice];//elegimos la palabra del string[]


        //el dato es la palabra que se coge random del string array
        //para poner al arrancar la aplicacion todas las letras con guiones
        String aux="";
        for(int i=0;i<palabraSeleccionada.length();i++){
            aux+="?";
        }

        textoAciertos.setText(aux);



        Button b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder palabraActual=new StringBuilder(textoAciertos.getText().toString());//IMP EL GETTEXT
                //cogemos la letra que hemos escrito-->para facilitar el proceso hacemos que sea
                //string y siempre cogemos la primera letra con el charAt
                letraIntroducida=String.valueOf(editText.getText().charAt(0));
                if(palabraSeleccionada.contains(letraIntroducida)) {//si acertamos

                    for (int i = 0; i < palabraSeleccionada.length(); i++) {
                        //recorremos cada letra de la palabra
                        if (palabraSeleccionada.charAt(i)==letraIntroducida.charAt(0)) {
                            //ej: palabra y a-->contiene 3 veces a-->sustituimos 3 veces
                            //la palabra con guiones por la letra introducida
                            //cambiamos el guion que tenemos en palabraActual por la letra
                            palabraActual.setCharAt(i,letraIntroducida.charAt(0));

                        }
                    }

                    //cambiamos el textView para que se muestre en la aplicacion
                 textoAciertos.setText(palabraActual.toString());
                }else{
                    fallos++;
                    if(fallos==1) {
                        imagen.setImageResource(R.drawable.hangman1);
                    }else if(fallos==2) {
                        imagen.setImageResource(R.drawable.hangman2);
                    }else if(fallos==3){
                        imagen.setImageResource(R.drawable.hangman3);
                    }else if(fallos==4){
                        imagen.setImageResource(R.drawable.hangman4);
                    }else if(fallos==5){
                        imagen.setImageResource(R.drawable.hangman5);
                    }else if(fallos==6){
                        imagen.setImageResource(R.drawable.hangman6);
                    }else if(fallos==7){
                        imagen.setImageResource(R.drawable.hangman7);
                    }else if(fallos==8){
                        imagen.setImageResource(R.drawable.hangman8);
                        textoIntro.setText("LO SIENTO, HAS PERDIDO");
                    }
                }
                String palabraCambiada=String.valueOf(palabraActual);//volvemos a pasar de char[] a string
                textoAciertos.setText(palabraCambiada);//actualizamos el editText
                    //cogera lo que tengamos escrito y comprobara si hemos acertado o no
                    //IMP-->EN EL CLICK TMB TIENE QUE ESTAR
                    //TODA LA LOGICA DE LA APP(aciertos y fallos)
                    if(palabraActual.toString().equals(palabraSeleccionada)){
                        //COMO PALABRAACTUAL ES UN STRING BUILDER HAY QUE PÒNER .TOSTRING
                        //PARA PODER COMPARAR CON PALABRASELECCIONADA
                    textoIntro.setText("ENHORABUENA, HAS GANADO!");
                    }
                }

        });


    }


}