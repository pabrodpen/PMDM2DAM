package com.example.comunicacionfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements OnControlesFragmentListener{
    //IMP EL IMPLEMENTS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor,new ControlesFragment())
                .commit();
    }

    @Override
    public void botonColorClicked(String color) {
        showSnackbar("Color "+color);
    }

    @Override
    public void botonTextoClicked(String texto) {
        showSnackbar("Texto "+texto);
    }
    public void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

}