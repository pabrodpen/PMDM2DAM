package com.example.gridview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridView);

        // Crear un arreglo de nombres para los botones
        String[] buttonNames = new String[16];
        for (int i = 0; i < 16; i++) {
            buttonNames[i] = "Dato" + (i + 1);
        }

        // Crear un ArrayAdapter para los botones
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.grid_button, R.id.gridView, buttonNames);
        gridView.setAdapter(adapter);

        // Establecer un listener para los clics en los botones
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String buttonText = ((Button) view).getText().toString();
                Toast.makeText(MainActivity.this, "Botón clicado: " + buttonText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
