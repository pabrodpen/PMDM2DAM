package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        añadeHijos();
    }
    public void añadeHijos() {
        GridLayout g = (GridLayout) findViewById(R.id.gridLayout);

        // Configura la cuadrícula como 6 filas y 3 columnas
        g.setRowCount(6);
        g.setColumnCount(3);

        // Botón reset
        final Button btnReset = new Button(this);
        btnReset.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        btnReset.setText("btnReset");
        btnReset.setBackgroundColor(Color.GRAY);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarColor();
            }
        });

        for (int i = 0; i < 17; i++) {
            final Button b = new Button(this);
            b.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setText("btn" + i);
            b.setId(View.generateViewId());
            g.addView(b, i);
            b.setBackgroundColor(Color.rgb(i * 10, i * 50, i * 30));

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.setBackgroundColor(Color.GRAY);
                    Toast.makeText(MainActivity.this, "Se ha pulsado el Botón " + b.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Agrega el botón reset al final
        g.addView(btnReset);
    }


    public void cambiarColor(){
        GridLayout grid= findViewById(R.id.gridLayout);
        Button boton;
        for(int i=0;i<grid.getChildCount();i++){
            View view;
            view=grid.getChildAt(i);
            if(view.getClass().getSimpleName().equals("Button")){
                boton=(Button)view;
                boton.setBackgroundColor(Color.rgb(i*10,i*50,i*30));
            }
        }
    }


}