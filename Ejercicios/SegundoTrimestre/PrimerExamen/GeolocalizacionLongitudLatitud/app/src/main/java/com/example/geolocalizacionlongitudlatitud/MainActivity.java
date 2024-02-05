package com.example.geolocalizacionlongitudlatitud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private EditText latitudEditText, longitudEditText;
    private Button mostrarMapaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        latitudEditText = findViewById(R.id.latitudEditText);
        longitudEditText = findViewById(R.id.longitudEditText);
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
        double latitud = Double.parseDouble(latitudEditText.getText().toString());
        double longitud = Double.parseDouble(longitudEditText.getText().toString());
        LatLng ubicacion = new LatLng(latitud, longitud);

        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicación"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap=map;
    }
}