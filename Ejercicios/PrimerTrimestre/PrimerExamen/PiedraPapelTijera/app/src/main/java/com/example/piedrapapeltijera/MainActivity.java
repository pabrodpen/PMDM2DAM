package com.example.piedrapapeltijera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button b1,b2,b3,bJ;
    int nJugador=0,nMaquina=-1;
    TextView tInicio;
    ImageView imgJugador,imgMaquina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgJugador=findViewById(R.id.imageView);
        imgMaquina=findViewById(R.id.imageView3);
        tInicio=findViewById(R.id.textViewInicio);

        b1=findViewById(R.id.buttonPiedra);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgJugador.setImageResource(R.drawable.piedra);
                nJugador=1;
            }
        });

        b2=findViewById((R.id.buttonPapel));
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgJugador.setImageResource(R.drawable.papel);
                nJugador=2;
            }
        });

        b3=findViewById(R.id.buttonTijeras);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgJugador.setImageResource(R.drawable.tijera);
                nJugador=3;
            }
        });

        bJ=findViewById(R.id.buttonJugar);
        bJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nMaquina=(int)(Math.random()*(3-1+1)-1);
                if(nMaquina==1){//piedra
                    imgMaquina.setImageResource(R.drawable.piedra_maquina);
                }else if(nMaquina==2){//papel
                    imgMaquina.setImageResource(R.drawable.papel_maquina);
                }else{
                    imgMaquina.setImageResource(R.drawable.tijera_maquina);
                }

                //logica

                if(nJugador==nMaquina){
                    tInicio.setText("HEMOS EMPATADO");
                }else if((nJugador==1 && nMaquina==3) ||(nJugador==2 && nMaquina==1) ||  (nJugador==3 && nMaquina==2)){
                    tInicio.setText("HAS GANADO!");
                }else if(((nMaquina==1 && nJugador==3) ||(nMaquina==2 && nJugador==1) ||  (nMaquina==3 && nJugador==2))){
                    tInicio.setText("HE GANADO!");
                }
            }
        });





    }
}