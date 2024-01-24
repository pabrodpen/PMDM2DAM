package com.example.miapppablorodriguez;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditarLugar extends AppCompatActivity {

    EditText editTextNombre, editTextTipo, editTextFecha, editTextUrl, editTextHora, editTextTfno, editTextUbicacion;

    private static final int REQUEST_CODE_GALERIA = 1001;
    Lugar lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editar);

        // Inicializar EditTexts
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTipo = findViewById(R.id.editTextTipo);
        editTextFecha = findViewById(R.id.editTextDate);
        editTextUrl = findViewById(R.id.editTextUrl);
        editTextTfno = findViewById(R.id.editTextPhone);
        editTextUbicacion = findViewById(R.id.editTextUbicacion);

        // Obtener datos del Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nombre = extras.getString("nombre");
            String tipo = extras.getString("tipo");
            String fecha = extras.getString("fecha");
            String url = extras.getString("url");
            String hora = extras.getString("hora");
            String tfno = extras.getString("tfno");
            String ubicacion = extras.getString("ubicacion");

            // Establecer los hints en los EditTexts
            editTextNombre.setHint(nombre);
            editTextTipo.setHint(tipo);
            editTextFecha.setHint(fecha);
            editTextUrl.setHint(url);
            editTextHora.setHint(hora);
            editTextTfno.setHint(tfno);
            editTextUbicacion.setHint(ubicacion);
        }
    }

    // Métodos para seleccionar imágenes de la galería
    private void seleccionarImagenDeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALERIA && resultCode == Activity.RESULT_OK && data != null) {
            Uri imagenUri = data.getData();

            // Actualizar la ruta de la foto en el objeto lugar
            String rutaFoto = obtenerRutaDesdeUri(imagenUri);
            if (lugar != null) {
                lugar.setRutaFoto(rutaFoto);
            }

            // Actualizar la imagen en el ImageView
            ImageView imageView = findViewById(R.id.image);
            if (rutaFoto != null) {
                imageView.setImageURI(imagenUri);
            } else {
                imageView.setImageResource(R.drawable.baseline_photo_24);
            }
        }
    }

    private String obtenerRutaDesdeUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    return cursor.getString(columnIndex);
                }
            } finally {
                cursor.close();
            }
        }

        return null;
    }
}

