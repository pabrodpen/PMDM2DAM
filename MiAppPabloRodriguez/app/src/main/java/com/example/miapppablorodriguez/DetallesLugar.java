package com.example.miapppablorodriguez;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetallesLugar extends AppCompatActivity {

    EditText tNombre, tTipo, tFecha, tUrl, tTfno, tUbicacion;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_IMAGE_GALLERY = 3;
    private Uri imagenUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalles_lugar);

        // Primero, verifica si ya tienes el permiso
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Si no tienes el permiso, solicítalo al usuario
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        tNombre = findViewById(R.id.editTextNombre);
        tTipo = findViewById(R.id.editTextTipo);
        tFecha = findViewById(R.id.editTextFecha);
        tUrl = findViewById(R.id.editTextUrl);
        tTfno = findViewById(R.id.ediTextTfno);
        tUbicacion = findViewById(R.id.editTextUbicacion);

        // Configurar la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Leer los extras del Intent
        Intent intent = getIntent();
        if (intent != null) {
            String nombre = intent.getStringExtra("nombre");
            String direccion = getIntent().getStringExtra("direccion");
            String tipo = intent.getStringExtra("tipo");
            String fecha = intent.getStringExtra("fecha");
            String url = intent.getStringExtra("url");
            String tfno = intent.getStringExtra("tfno");

            // Actualizar los EditText con los valores
            tNombre.setText(nombre);
            tTipo.setText(tipo);
            tFecha.setText(fecha);
            tUrl.setText(url);
            tTfno.setText(tfno);
            tUbicacion.setText(direccion);
            mostrarGeolocalizacion(direccion);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalles_lugar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.volverAList) {
            Intent intent = new Intent(DetallesLugar.this, ListLugares.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.hacerFoto) {
            tomarFoto();
        } else if (id == R.id.fotoGaleria) {
            seleccionarFotoDeGaleria();
        }

        return super.onOptionsItemSelected(item);
    }

    private void mostrarGeolocalizacion(String direccion) {
        if (direccion == null || direccion.isEmpty()) {
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(direccion, 1);

            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitud = address.getLatitude();
                double longitud = address.getLongitude();

                // Ahora puedes utilizar latitud y longitud como desees
                // Puedes mostrarlos en un mapa, etc.
            } else {
                // No se encontraron resultados para la dirección
                // Maneja esta situación según tus necesidades
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Maneja el error de geocodificación según tus necesidades
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // Maneja el error de argumento no válido según tus necesidades
        }
    }

    public void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void seleccionarFotoDeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Foto tomada con éxito
            // ...
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null) {
            // Foto seleccionada de la galería
            imagenUri = data.getData();
            // Actualizar la imagen en el ImageView o hacer lo que necesites con la URI
            // ...
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Aquí puedes agregar cualquier lógica que necesites cuando la actividad se reanude
        // Por ejemplo, podrías verificar si la imagenUri no es nula y realizar alguna acción
    }
}



