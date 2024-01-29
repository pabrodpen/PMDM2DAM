package com.example.miapppablorodriguez;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miapppablorodriguez.DialogLista;
import com.example.miapppablorodriguez.FeedReaderContract;
import com.example.miapppablorodriguez.FeedReaderDbHelper;
import com.example.miapppablorodriguez.ListLugares;
import com.example.miapppablorodriguez.Lugar;
import com.example.miapppablorodriguez.R;

import java.util.Calendar;
import java.util.Locale;

public class InsertarLugar extends AppCompatActivity implements DialogLista.OnTipoLugarSelectedListener {

    private static final int REQUEST_CODE_GALERIA = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;
    private static final int REQUEST_IMAGE_GALLERY = 1003;

    private EditText editTextNombre, editTextDirecc, editTextTfno, editTextURL;
    private String datoTipoLugar, datoFecha;
    private FeedReaderDbHelper dbHelper;
    private Lugar lugar;  // Utilizar la instancia global

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_insertar);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDirecc = findViewById(R.id.editTextTextPostalAddress);
        editTextTfno = findViewById(R.id.editTextPhone);
        editTextURL = findViewById(R.id.editTextUrl);

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
                                String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
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

        ImageButton buttonGaleria=findViewById(R.id.imageButtonGaleria);

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

            // Obtener la ruta de la foto
            if (lugar != null) {
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO, lugar.getRutaFoto());
            }

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




    // Métodos para seleccionar imágenes de la galería
    private void seleccionarImagenDeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALERIA);
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
    protected void onResume() {
        super.onResume();


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
