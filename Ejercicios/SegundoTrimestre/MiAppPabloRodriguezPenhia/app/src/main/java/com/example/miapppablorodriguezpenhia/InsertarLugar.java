package com.example.miapppablorodriguezpenhia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

public class InsertarLugar extends AppCompatActivity implements DialogLista.OnTipoLugarSelectedListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1002;
    private static final int REQUEST_IMAGE_GALLERY = 1003;

    private EditText editTextNombre, editTextDirecc, editTextTfno, editTextURL;
    private String datoTipoLugar, datoFecha, rutaFoto;
    private FeedReaderDbHelper dbHelper;
    private Lugar lugar;  // Utilizar la instancia global
    RatingBar  ratingBarInsertar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_insertar);


        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDirecc = findViewById(R.id.editTextTextPostalAddress);
        editTextTfno = findViewById(R.id.editTextPhone);
        editTextURL = findViewById(R.id.editTextUrl);
        ratingBarInsertar = findViewById(R.id.ratingBarInsertar);

        dbHelper = new FeedReaderDbHelper(this);

        // Intenta obtener el objeto Lugar del Intent
        Intent intent = getIntent();
        lugar = (Lugar) intent.getSerializableExtra("lugar");

        Button dialogLista = findViewById(R.id.buttonDialogo);
        dialogLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLista dialogLista = new DialogLista();
                dialogLista.show(getSupportFragmentManager(), "DialogLista");
            }
        });

        Button buttonInsertar = findViewById(R.id.buttonInsertar);
        buttonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper != null) {
                    insertarInformacion();

                    // Actualizar la lista en la actividad principal (ListLugares)
                    ListLugares.actualizarLista();

                    // Cerrar la actividad actual después de insertar
                    finish();
                }
            }
        });

        Button buttonFecha = findViewById(R.id.buttonDate);
        buttonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        InsertarLugar.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Formatear la fecha en el formato deseado (yyyy-MM-dd)
                                String fechaSeleccionada = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                                datoFecha = fechaSeleccionada;

                                // Mostrar la fecha en un Toast (opcional)
                                Toast.makeText(InsertarLugar.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
                            }
                        }, anio, mes, day
                );
                datePickerDialog.show();
            }
        });

        ImageButton buttonCamara = findViewById(R.id.imageButtonCamera);
        buttonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });

        ImageButton buttonGaleria = findViewById(R.id.imageButtonGaleria);
        buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFotoDeGaleria();
            }
        });


    }

    private void insertarInformacion() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nombre = editTextNombre.getText().toString();
        String direccion = editTextDirecc.getText().toString();
        String telefono = editTextTfno.getText().toString();
        String url = editTextURL.getText().toString();
        float valoracion = ratingBarInsertar.getRating();

        try {
            Log.d("INSERT_OPERATION", "Insertando datos:");
            Log.d("INSERT_OPERATION", "Nombre: " + nombre);
            Log.d("INSERT_OPERATION", "Dirección: " + direccion);
            Log.d("INSERT_OPERATION", "Teléfono: " + telefono);
            Log.d("INSERT_OPERATION", "URL: " + url);
            Log.d("INSERT_OPERATION", "Fecha: " + datoFecha);
            Log.d("INSERT_OPERATION", "Tipo: " + datoTipoLugar);

            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO, datoTipoLugar);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION, direccion);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO, telefono);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, url);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, datoFecha);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_VALORACION, valoracion);


            // Nueva columna para almacenar la ruta de la foto
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO, rutaFoto);

            long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

            Log.d("INSERT_OPERATION", "Nueva fila insertada con ID: " + newRowId);

            // Actualizar directamente la lista en la actividad actual (InsertarLugar)
            ListLugares.actualizarLista();

            limpiarCampos();
        } catch (Exception e) {
            Log.e("INSERT_OPERATION", "Error al insertar en la base de datos: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextDirecc.setText("");
        editTextTfno.setText("");
        editTextURL.setText("");
    }

    @Override
    public void onTipoLugarSelected(String tipoLugar) {
        datoTipoLugar = tipoLugar;
        Log.d("TIPO_LUGAR", "Tipo de lugar seleccionado: " + tipoLugar);
    }

    // Métodos para la gestión de imágenes
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                // Obtener la imagen capturada
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // Guardar la imagen en la galería y obtener la ruta
                rutaFoto = guardarImagenEnGaleria(imageBitmap);


            }
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri imagenUri = data.getData();

            // Obtener la ruta de la foto desde la galería
            rutaFoto = obtenerRutaDesdeUri(imagenUri);


        }
    }

    private String guardarImagenEnGaleria(Bitmap imageBitmap) {
        String rutaImagen = null;
        String imageFileName = "JPEG_" + System.currentTimeMillis() + ".jpg";
        ContentResolver resolver = getContentResolver();

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, imageFileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        // Guardar la imagen en la galería
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream out = resolver.openOutputStream(uri);
            if (out != null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.close();
                rutaImagen = uri.toString();
            }
        } catch (IOException e) {
            Log.e("SAVE_IMAGE_ERROR", "Error al guardar la imagen en la galería: " + e.getMessage());
        }

        return rutaImagen;
    }

    private String obtenerRutaDesdeUri(Uri uri) {
        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
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
        } else if (uri.getScheme() != null && uri.getScheme().equals("file")) {
            // Si la URI utiliza el esquema "file", simplemente devuelve la ruta directamente
            return uri.getPath();
        }

        return null;
    }


    private boolean esTablet() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int anchoPantalla = displayMetrics.widthPixels / displayMetrics.densityDpi;
        int altoPantalla = displayMetrics.heightPixels / displayMetrics.densityDpi;

        double tamanioPantalla = Math.sqrt(Math.pow(anchoPantalla, 2) + Math.pow(altoPantalla, 2));

        // Si el tamaño de la pantalla es mayor o igual a 7 pulgadas, se considera una tablet
        return tamanioPantalla >= 7.0;
    }
}
