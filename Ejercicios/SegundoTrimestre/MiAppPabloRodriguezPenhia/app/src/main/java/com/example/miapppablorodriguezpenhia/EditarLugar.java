package com.example.miapppablorodriguezpenhia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class EditarLugar extends AppCompatActivity {

    private Lugar lugar;
    private EditText nombreEditText;
    private EditText direccionEditText;
    private EditText tfnoEditText;
    private EditText urlEditText;
    private ImageView fotoImageView;
    private Button guardarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editar);

        // Recibir el objeto Lugar del Intent
        lugar = (Lugar) getIntent().getSerializableExtra("lugar");

        if (lugar != null) {
            inicializarVistas();
            cargarDatosEnVistas();
            configurarBotonGuardar();
        } else {
            Log.e("ERROR", "No se recibieron datos en EditarLugar");
        }
    }

    private void inicializarVistas() {
        nombreEditText = findViewById(R.id.nombreEditText);
        direccionEditText = findViewById(R.id.direccionEditText);
        tfnoEditText = findViewById(R.id.tfnoEditText);
        urlEditText = findViewById(R.id.urlEditText);
        //fotoImageView = findViewById(R.id.fotoImageView);
        guardarButton = findViewById(R.id.guardarButton);
    }

    private void cargarDatosEnVistas() {
        nombreEditText.setText(lugar.getNombre());
        direccionEditText.setText(lugar.getDireccion());
        tfnoEditText.setText(lugar.getTfno());
        urlEditText.setText(lugar.getUrl());

        // Cargar la imagen en el ImageView
        if (lugar.getRutaFoto() != null && !lugar.getRutaFoto().isEmpty()) {
            Glide.with(this)
                    .load(lugar.getRutaFoto())
                    .into(fotoImageView);
        }
    }

    private void configurarBotonGuardar() {
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });
    }

    private void guardarCambios() {
        // Obtener los nuevos datos de los EditText
        String nuevoNombre = nombreEditText.getText().toString();
        String nuevaDireccion = direccionEditText.getText().toString();
        String nuevoTfno = tfnoEditText.getText().toString();
        String nuevaUrl = urlEditText.getText().toString();

        // Actualizar en la base de datos solo los campos permitidos
        if (!nuevoNombre.isEmpty() || !nuevaDireccion.isEmpty() || !nuevoTfno.isEmpty() || !nuevaUrl.isEmpty()) {
            actualizarLugarEnBD(nuevoNombre, nuevaDireccion, nuevoTfno, nuevaUrl);
        }
        // Finalizar la actividad después de guardar
        finish();
    }

    private void actualizarLugarEnBD(String nombre, String direccion, String tfno, String url) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Solo se actualizan los campos que no están vacíos
        if (!nombre.isEmpty()) {
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
        }
        if (!direccion.isEmpty()) {
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION, direccion);
        }
        if (!tfno.isEmpty()) {
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO, tfno);
        }
        if (!url.isEmpty()) {
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, url);
        }

        String whereClause = FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE + "=?";
        String[] whereArgs = {lugar.getNombre()}; // Se usa el nombre original para identificar el lugar a actualizar.

        // Actualizar la fila en la base de datos
        int rowsAffected = db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, whereClause, whereArgs);
        Log.d("ACTUALIZACION", "Filas afectadas: " + rowsAffected);

        // Cerrar la base de datos
        db.close();
        dbHelper.close();
    }
}
