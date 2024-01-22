package com.example.sqliteejpropio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextSubtitle = findViewById(R.id.editTextSubtitle);

        Button btnInsert = findViewById(R.id.btnInsert);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnShow = findViewById(R.id.btnShow);

        final FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarInformacion(dbHelper);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarInformacion(dbHelper);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerInformacion(dbHelper);
            }
        });
    }

    // Método para insertar información en la base de datos
    public void insertarInformacion(FeedReaderDbHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String titulo = editTextTitle.getText().toString();
        String subtitulo = editTextSubtitle.getText().toString();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, titulo);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitulo);

        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        Log.d("INSERT_OPERATION", "Nueva fila insertada con ID: " + newRowId);

        limpiarCampos();
    }

    // Método para borrar información de la base de datos
    public void borrarInformacion(FeedReaderDbHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String titulo = editTextTitle.getText().toString();

        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { titulo };

        int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
        Log.d("DELETE_OPERATION", "Filas eliminadas: " + deletedRows);

        limpiarCampos();
    }

    // Método para limpiar los campos de EditText
    private void limpiarCampos() {
        editTextTitle.setText("");
        editTextSubtitle.setText("");
    }


    public void actualizarBD(FeedReaderDbHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Nuevo valor para una columna
        String title = "Mi Nuevo Título";
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);

        // Qué fila actualizar, basado en el título
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { "Título existente que quieres actualizar" };

        int count = db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
        // Agrega un mensaje de registro para ver los resultados
        Log.d("UPDATE_OPERATION", "Filas actualizadas: " + count);
    }


    // Método para leer toda la información de la base de datos
    public void leerInformacion(FeedReaderDbHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };
        String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

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
            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));

            Log.d("READ_OPERATION", "ID: " + itemId + ", Title: " + title + ", Subtitle: " + subtitle);
        }
        cursor.close();
    }

}
