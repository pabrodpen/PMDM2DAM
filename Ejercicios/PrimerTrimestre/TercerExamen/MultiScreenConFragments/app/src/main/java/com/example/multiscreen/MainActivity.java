package com.example.multiscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutContenedor=(android.widget.LinearLayout) findViewById(R.id.contenedor);

        if(layoutContenedor!=null){
            //es una tablet(tiene minimo 600dp) y el layoutContenedor existe
            showSnackbar("Estas en una tablet");
        }else{
            //movil
            showSnackbar("Estas en un movil");
        }
    }
    public void showSnackbar(String m){
        Snackbar.make(findViewById(android.R.id.content),m,Snackbar.LENGTH_SHORT).show();
    }
}