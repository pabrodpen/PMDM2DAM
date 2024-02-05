package com.example.repaso1;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class Insertar extends Fragment {

    Button bEnviar,bPrioridad,bFecha;
    EditText tNombre,tDescripcion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_insertar,container,false);

        tNombre=view.findViewById(R.id.editTextName);
        tDescripcion=view.findViewById(R.id.editTextDescription);
        bEnviar=view.findViewById(R.id.buttonAdd);
        bPrioridad=view.findViewById(R.id.buttonPrioridad);
        bFecha=view.findViewById(R.id.buttonFecha);

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedReaderDbHelper feedReaderDbHelper=new FeedReaderDbHelper(getContext());
                insertarInformacion(feedReaderDbHelper);
                feedReaderDbHelper.close();
            }
        });

                bPrioridad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogLista dialogLista=new DialogLista();
                        dialogLista.show(getFragmentManager(),"DialogLista");
                    }
                });

        bFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Aquí puedes manejar la fecha seleccionada
                                String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                                Toast.makeText(MainActivity.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
                            }
                        }, anio, mes, day
                );
                datePickerDialog.show();
            }
        });


    }

    public void leerInformacion(FeedReaderDbHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DATE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PRIORITY
        };
        //ordenamos por prioridad
        String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_PRIORITY + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                null,  // Sin condición, recupera todos los registros
                null,
                null,
                null,
                sortOrder
        );

        // Agrega un mensaje de registro para ver los resultados
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIORITY));

            Log.d("READ_OPERATION", "ID: " + itemId + ", Titulo: " + title + ", Descripcion: " + description+", Fecha: " + date + ", Prioridad: " + priority);
        }
        cursor.close();
    }

    public void insertarInformacion(FeedReaderDbHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String titulo = tNombre.getText().toString();
        String descripcion = tDescripcion.getText().toString();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, titulo);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, descripcion);

        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        Log.d("INSERT_OPERATION", "Nueva fila insertada con ID: " + newRowId);

        limpiarCampos();
    }

    // Método para borrar información de la base de datos
    public void borrarInformacion(FeedReaderDbHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String titulo = tNombre.getText().toString();
        String descripcion=tDescripcion.getText().toString();

        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { titulo };

        int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
        Log.d("DELETE_OPERATION", "Filas eliminadas: " + deletedRows);

        limpiarCampos();
    }

    public void actLista(){
        FeedReaderDbHelper feedReaderDbHelper=new FeedReaderDbHelper(getContext());

        leerInformacion(feedReaderDbHelper);

        feedReaderDbHelper.close();
    }


    public void limpiarCampos(){
        tNombre.setText("");
        tDescripcion.setText("");
    }
}
