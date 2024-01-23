package com.example.geolocalizaciondireccion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private EditText direccionEditText;
    private Button mostrarMapaButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        direccionEditText = findViewById(R.id.direccionEditText);
        mostrarMapaButton = findViewById(R.id.mostrarMapaButton);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mostrarMapaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMapa();
            }
        });
    }

    private void mostrarMapa() {
        String direccion = direccionEditText.getText().toString();

        // Aquí deberías implementar la lógica para convertir la dirección en coordenadas (geocodificación).
        // Por simplicidad, asumiré que obtienes las coordenadas de manera ficticia.
        double latitud = 37.7749;  // Coordenadas ficticias de San Francisco
        double longitud = -122.4194;

        LatLng ubicacion = new LatLng(latitud, longitud);

        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicación"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
    }
}
