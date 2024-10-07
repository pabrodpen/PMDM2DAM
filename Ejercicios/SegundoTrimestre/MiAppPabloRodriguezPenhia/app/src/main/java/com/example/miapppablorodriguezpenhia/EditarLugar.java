package com.example.miapppablorodriguezpenhia;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class EditarLugar extends AppCompatActivity {

    private EditText editTextNombre, editTextDirecc, editTextTfno, editTextURL, editTextFecha, editTextTipo;  // Añadir campos para fecha y tipo
    private RatingBar ratingBarEditar;
    private ImageView imageViewEditar; // Para mostrar la imagen
    private String nombreLugarSeleccionado;
    private FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editar);

        // Inicializar la base de datos
        dbHelper = new FeedReaderDbHelper(this);

        // Obtener referencias a los elementos de la interfaz
        editTextNombre = findViewById(R.id.nombreEditText);
        editTextDirecc = findViewById(R.id.direccionEditText);
        editTextTfno = findViewById(R.id.tfnoEditText);
        editTextURL = findViewById(R.id.urlEditText);
        editTextFecha = findViewById(R.id.fechaEditText);  // Referenciar EditText para la fecha
        editTextTipo = findViewById(R.id.tipoEditText);    // Referenciar EditText para el tipo
        ratingBarEditar = findViewById(R.id.valoracionRatingBar);
        imageViewEditar = findViewById(R.id.fotoImageView);

        // Obtener el nombre del lugar a editar desde el Intent
        Intent intent = getIntent();
        nombreLugarSeleccionado = intent.getStringExtra("nombre");

        // Mostrar un Toast con el nombre del lugar recibido
        Toast.makeText(this, "Nombre recibido: " + nombreLugarSeleccionado, Toast.LENGTH_LONG).show();

        // Verificar si el nombreLugarSeleccionado es null
        if (nombreLugarSeleccionado == null || nombreLugarSeleccionado.isEmpty()) {
            Toast.makeText(this, "El nombre del lugar es nulo o vacío", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si no se recibe un nombre válido
            return;
        }

        // Obtener los datos del lugar a editar desde la base de datos
        Lugar lugar = obtenerLugarPorNombre(nombreLugarSeleccionado);

        if (lugar != null) {
            // Rellenar los campos con los datos del lugar
            editTextNombre.setText(lugar.getNombre());
            editTextDirecc.setText(lugar.getDireccion());
            editTextTfno.setText(lugar.getTfno());
            editTextURL.setText(lugar.getUrl());
            editTextFecha.setText(lugar.getFecha());  // Mostrar la fecha obtenida
            editTextTipo.setText(lugar.getTipo());    // Mostrar el tipo obtenido
            ratingBarEditar.setRating(lugar.getValoracion());

            // Cargar la imagen si existe
            if (lugar.getRutaFoto() != null && !lugar.getRutaFoto().isEmpty()) {
                Glide.with(this)
                        .load(lugar.getRutaFoto())
                        .into(imageViewEditar);
            }
        } else {
            // Manejar el caso en que no se encuentre el lugar
            Toast.makeText(this, "No se encontró el lugar", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Configurar el botón de actualizar
        Button buttonActualizar = findViewById(R.id.guardarButton);
        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos valores de los campos
                String nuevoNombre = editTextNombre.getText().toString();
                String nuevaDireccion = editTextDirecc.getText().toString();
                String nuevoTfno = editTextTfno.getText().toString();
                String nuevaURL = editTextURL.getText().toString();
                String nuevaFecha = editTextFecha.getText().toString();  // Obtener nueva fecha
                String nuevoTipo = editTextTipo.getText().toString();    // Obtener nuevo tipo
                float nuevaValoracion = ratingBarEditar.getRating();

                // Actualizar la base de datos
                actualizarLugar(nombreLugarSeleccionado, nuevoNombre, nuevaDireccion, nuevoTfno, nuevaURL, nuevaFecha, nuevoTipo, nuevaValoracion);

                // Actualizar la lista en la actividad principal
                ListLugares.actualizarLista();

                // Cerrar la actividad actual
                finish();
            }
        });
    }

    // Método para obtener los datos de un lugar por su nombre
    private Lugar obtenerLugarPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO,
                FeedReaderContract.FeedEntry.COLUMN_NAME_URL,
                FeedReaderContract.FeedEntry.COLUMN_NAME_VALORACION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DATE,  // Añadir columna para fecha
                FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO    // Añadir columna para tipo
        };

        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE + " = ?";
        String[] selectionArgs = { nombre };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Lugar lugar = null;
        if (cursor.moveToFirst()) {
            String nombreLugar = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE));
            String direccion = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION));
            String tfno = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_URL));
            float valoracion = cursor.getFloat(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_VALORACION));
            String rutaFoto = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE));  // Obtener fecha
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO));    // Obtener tipo

            lugar = new Lugar(nombreLugar, direccion, tfno, url, fecha, tipo, rutaFoto, valoracion);
        }
        cursor.close();
        return lugar;
    }

    // Método para actualizar los datos de un lugar en la base de datos
    private void actualizarLugar(String nombreAnterior, String nuevoNombre, String nuevaDireccion, String nuevoTfno, String nuevaURL, String nuevaFecha, String nuevoTipo, float nuevaValoracion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Crear un nuevo mapa de valores donde las columnas son las claves
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE, nuevoNombre);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION, nuevaDireccion);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO, nuevoTfno);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, nuevaURL);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, nuevaFecha);  // Añadir nueva fecha
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO, nuevoTipo);    // Añadir nuevo tipo
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_VALORACION, nuevaValoracion);

        // Definir la condición WHERE para identificar el lugar a actualizar
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE + " = ?";
        String[] selectionArgs = { nombreAnterior };

        // Actualizar la fila en la base de datos
        db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
