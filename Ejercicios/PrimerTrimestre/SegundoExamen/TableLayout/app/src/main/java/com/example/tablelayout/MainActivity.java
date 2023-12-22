package com.example.tablelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

Button bCambiar,bRestaurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        añadeHijos();


    }

    public void añadeHijos(){
        GridLayout g = (GridLayout) findViewById(R.id.grid1);
        Button b;
        for(int i=0;i<18;i++) {
            b = new Button(this);
            b.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setText("btn" + i);
            b.setId(View.generateViewId());

            if (i == 17) {
                b.setText("btnReset");
                b.setBackgroundColor(Color.rgb(i * 10, i * 50, i * 30));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recreate();
                    }
                });
            }
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.setBackgroundColor(Color.GRAY);
                }
            });
            g.addView(b, i);

        }
    }

}