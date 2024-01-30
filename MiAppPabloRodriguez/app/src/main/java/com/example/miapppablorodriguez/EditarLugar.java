package com.example.miapppablorodriguez;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;


public class EditarLugar extends AppCompatActivity implements DialogLista.OnTipoLugarSelectedListener {

    EditText editTextNombre, editTextTipo, editTextFecha, editTextUrl, editTextTfno, editTextUbicacion;
    RatingBar ratingBar; // Agregado

    Lugar lugar;

    private String datoTipoLugar, datoFecha, fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_actualizar);

        // Inicializar EditTexts
        editTextNombre = findViewById(R.id.editTextNombreEditar);
        editTextTipo = findViewById(R.id.editTextTipoEditar);
        editTextFecha = findViewById(R.id.editTextFechaEditar);
        editTextUrl = findViewById(R.id.editTextUrlEditar);
        editTextTfno = findViewById(R.id.editTextTfnoEditar);
        editTextUbicacion = findViewById(R.id.editTextDireccionEditar);

        /* Agregar RatingBar
        //ratingBar = findViewById(R.id.ratingBar2); // Agregado

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
            editTextTfno.setHint(tfno);
            editTextUbicacion.setHint(ubicacion);

            // Mostrar la valoración actual en el RatingBar
            float valoracion = extras.getFloat("valoracion");
            ratingBar.setRating(valoracion); // Agregado
        }

        Button buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para actualizar el lugar
                actualizarLugar();
            }
        });

        Button dialogLista = findViewById(R.id.buttonTipoEditar);
        dialogLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLista dialogLista = new DialogLista();
                dialogLista.show(getSupportFragmentManager(), "DialogLista");
            }
        });

        Button buttonFecha = findViewById(R.id.buttonFechaEditar);
        buttonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ... (código para mostrar el DatePickerDialog)
            }
        });
    }

    @Override
    public void onTipoLugarSelected(String tipoLugar) {
        // Actualizar el EditText de tipo con el tipo seleccionado
        editTextTipo.setText(tipoLugar);
    }

    // Método para actualizar un lugar
    private void actualizarLugar() {
        // Obtener los nuevos datos de los EditTexts
        String nombre = editTextNombre.getText().toString();
        String tipo = editTextTipo.getText().toString();
        String url = editTextUrl.getText().toString();
        String tfno = editTextTfno.getText().toString();
        String ubicacion = editTextUbicacion.getText().toString();

        // Validar que se haya ingresado el nombre (puedes agregar más validaciones según tus necesidades)
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Por favor, ingresa el nombre del lugar", Toast.LENGTH_SHORT).show();
            return;
        }



        // Crear un objeto Lugar con los nuevos datos
        //Lugar nuevoLugar = new Lugar(nombre, ubicacion, tfno, url, fechaSeleccionada, tipo, ratingBar.getRating());

        // Actualizar el lugar en la base de datos
        if (actualizarLugarEnBD(nuevoLugar)) {
            // Actualizar la lista en la actividad principal (ListLugares)
            ListLugares.actualizarLista();

            // Cerrar la actividad actual después de actualizar
            finish();
        } else {
            // Mostrar un mensaje de error si la actualización falla
            Toast.makeText(this, "Error al actualizar el lugar", Toast.LENGTH_SHORT).show();
        }
    }



    // Método para actualizar un lugar en la base de datos
    private boolean actualizarLugarEnBD(Lugar lugarActualizado) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE, lugarActualizado.getNombre());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO, lugarActualizado.getTipo());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION, lugarActualizado.getDireccion());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO, lugarActualizado.getTfno());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, lugarActualizado.getUrl());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, lugarActualizado.getFecha());


        // Puedes agregar la actualización de la imagen si es necesario


        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE + " LIKE ?";
        String[] selectionArgs = { lugarActualizado.getNombre() };


        int updatedRows = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);


        db.close();
        dbHelper.close();


        // Si se actualizó al menos una fila, se considera exitoso
        return updatedRows > 0;
    }*/
    }

    @Override
    public void onTipoLugarSelected(String tipoLugar) {

    }
}