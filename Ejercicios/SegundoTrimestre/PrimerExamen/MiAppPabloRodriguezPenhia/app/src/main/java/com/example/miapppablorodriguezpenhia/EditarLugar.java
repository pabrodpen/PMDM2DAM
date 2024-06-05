package com.example.miapppablorodriguezpenhia;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class EditarLugar extends AppCompatActivity implements DialogLista.OnTipoLugarSelectedListener {

    EditText editTextNombre, editTextUrl, editTextTfno, editTextUbicacion;
    Button buttonTipoEditar, buttonFechaEditar;
    private FeedReaderDbHelper dbHelper;

    private String datoTipoLugar, datoFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (esTablet()) {
            setContentView(R.layout.activity_actualizar_tablet);
        } else {
            setContentView(R.layout.fragment_actualizar);
        }

        dbHelper = new FeedReaderDbHelper(this);
        // Inicializar EditTexts y Buttons
        editTextNombre = findViewById(R.id.editTextNombreEditar);
        editTextUrl = findViewById(R.id.editTextUrlEditar);
        editTextTfno = findViewById(R.id.editTextTfnoEditar);
        editTextUbicacion = findViewById(R.id.editTextDireccionEditar);
        buttonTipoEditar = findViewById(R.id.buttonTipoEditar);
        buttonFechaEditar = findViewById(R.id.buttonFechaEditar);

        // Obtener datos del Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nombre = extras.getString("nombre");
            String tipo = extras.getString("tipo");
            String fecha = extras.getString("fecha");
            String url = extras.getString("url");
            String tfno = extras.getString("tfno");
            String ubicacion = extras.getString("ubicacion");

            // Establecer los hints en los EditTexts
            editTextNombre.setHint(nombre);
            buttonTipoEditar.setText(tipo);
            buttonFechaEditar.setText(fecha);
            editTextUrl.setHint(url);
            editTextTfno.setHint(tfno);
            editTextUbicacion.setHint(ubicacion);
        }

        Button buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarInformacion();
            }
        });

        buttonTipoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLista dialogLista = new DialogLista();
                dialogLista.show(getSupportFragmentManager(), "DialogLista");
            }
        });

        buttonFechaEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditarLugar.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String fechaSeleccionada = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                                datoFecha = fechaSeleccionada;
                                buttonFechaEditar.setText(fechaSeleccionada);
                            }
                        }, anio, mes, dia
                );
                datePickerDialog.show();
            }
        });
    }

    private void actualizarInformacion() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nombre = editTextNombre.getText().toString();
        String direccion = editTextUbicacion.getText().toString();
        String telefono = editTextTfno.getText().toString();
        String url = editTextUrl.getText().toString();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(direccion) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(url) || TextUtils.isEmpty(datoFecha) || TextUtils.isEmpty(datoTipoLugar)) {
            Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Log.d("UPDATE_OPERATION", "Actualizando datos:");
            Log.d("UPDATE_OPERATION", "Nombre: " + nombre);
            Log.d("UPDATE_OPERATION", "Dirección: " + direccion);
            Log.d("UPDATE_OPERATION", "Teléfono: " + telefono);
            Log.d("UPDATE_OPERATION", "URL: " + url);
            Log.d("UPDATE_OPERATION", "Fecha: " + datoFecha);
            Log.d("UPDATE_OPERATION", "Tipo: " + datoTipoLugar);

            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO, datoTipoLugar);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION, direccion);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO, telefono);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, url);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, datoFecha);

            // Usa el ID para identificar el registro
            int lugarId = getIntent().getIntExtra("id", -1);
            if (lugarId == -1) {
                Toast.makeText(this, "Error: ID de lugar no válido", Toast.LENGTH_SHORT).show();
                return;
            }

            String selection = FeedReaderContract.FeedEntry._ID + " = ?";
            String[] selectionArgs = {String.valueOf(lugarId)};

            int count = db.update(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            Log.d("UPDATE_OPERATION", "Número de filas actualizadas: " + count);

            if (count > 0) {
                Toast.makeText(this, "Lugar actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar el lugar", Toast.LENGTH_SHORT).show();
            }

            // Actualizar directamente la lista en la actividad actual (ListLugares)
            ListLugares.actualizarLista();

            limpiarCampos();
        } catch (Exception e) {
            Log.e("UPDATE_OPERATION", "Error al actualizar en la base de datos: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextUbicacion.setText("");
        editTextTfno.setText("");
        editTextUrl.setText("");
    }

    @Override
    public void onTipoLugarSelected(String tipoLugar) {
        datoTipoLugar = tipoLugar;
        buttonTipoEditar.setText(tipoLugar);
    }

    private boolean esTablet() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int anchoPantalla = displayMetrics.widthPixels / displayMetrics.densityDpi;
        int altoPantalla = displayMetrics.heightPixels / displayMetrics.densityDpi;

        double tamanioPantalla = Math.sqrt(Math.pow(anchoPantalla, 2) + Math.pow(altoPantalla, 2));

        return tamanioPantalla >= 7.0;
    }
}
