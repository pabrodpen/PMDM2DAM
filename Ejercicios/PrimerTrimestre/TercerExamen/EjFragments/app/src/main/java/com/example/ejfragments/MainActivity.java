package com.example.ejfragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

public class MainActivity extends AppCompatActivity {
    // Define un booleano para determinar si es un tablet
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura el booleano según el tamaño de pantalla
        isTablet = getResources().getConfiguration().smallestScreenWidthDp >= 600;

        // Obtén el contenedor de fragments
        FragmentContainerView fragmentContainer = findViewById(R.id.fragmentContainerView);

        if (fragmentContainer != null) {
            // Es un tablet (tamaño grande)
            if (isTablet) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new FragmentListado())
                        .commit();
            } else {
                // Es un teléfono (tamaño normal)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new FragmentDetalle())
                        .commit();
            }
        }
    }
}
