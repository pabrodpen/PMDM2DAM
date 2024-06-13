package com.example.miapppablorodriguezpenhia;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListLugares extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Lugar> adapter;
    private static ArrayList<Lugar> lugares=new ArrayList<>();
    private static ListLugares instance;

    private static FeedReaderDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        instance = this;
        dbHelper = new FeedReaderDbHelper(this);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializar la lista de lugares y el adaptador
        lugares = new ArrayList<>();
        listView = findViewById(R.id.listView);
        adapter = new AdaptadorLugar(this, lugares);

        if (adapter != null && listView != null) {
            listView.setAdapter(adapter);
            consultarBaseDeDatosAsync();
        } else {
            Log.e("ERROR", "Adapter o ListView es nulo");
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Lugar lugarSeleccionado = lugares.get(position);
            Intent detallesIntent = new Intent(ListLugares.this, DetallesLugar.class);
            detallesIntent.putExtra("nombre", lugarSeleccionado.getNombre());
            detallesIntent.putExtra("tipo", lugarSeleccionado.getTipo() != null ? lugarSeleccionado.getTipo().toString() : "");
            detallesIntent.putExtra("fecha", lugarSeleccionado.getFecha());
            detallesIntent.putExtra("url", lugarSeleccionado.getUrl());
            detallesIntent.putExtra("tfno", lugarSeleccionado.getTfno());
            detallesIntent.putExtra("direccion", lugarSeleccionado.getDireccion());
            detallesIntent.putExtra("rutaFoto", lugarSeleccionado.getRutaFoto());
            detallesIntent.putExtra("valoracion", lugarSeleccionado.getValoracion());

            Toast.makeText(getApplicationContext(), "Seleccionado: " + lugarSeleccionado.getNombre(), Toast.LENGTH_SHORT).show();
            startActivity(detallesIntent);
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configurar FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Iniciar la actividad de inserción cuando se presione el botón flotante
            Intent insertarIntent = new Intent(ListLugares.this, InsertarLugar.class);
            startActivity(insertarIntent);
        });
    }


    // Método para cargar la lista de lugares desde la base de datos de manera asíncrona
    private void consultarBaseDeDatosAsync() {
        new Thread(() -> {
            ArrayList<Lugar> lugaresList = obtenerListaDeLugaresDesdeBD();
            runOnUiThread(() -> {
                // Limpiar la lista actual y agregar los nuevos datos
                lugares.clear();
                lugares.addAll(lugaresList);
                // Notificar al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            });
        }).start();
    }




    // Método para obtener la lista de lugares desde la base de datos
    private ArrayList<Lugar> obtenerListaDeLugaresDesdeBD() {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_URL,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DATE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO,
                FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO,
                FeedReaderContract.FeedEntry.COLUMN_NAME_VALORACION
        };

        String selection = "1";
        String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE + " ASC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<Lugar> lugaresList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE));
            String direccion = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION));
            String tfno = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_URL));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE));
            String tipoString = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO));
            String rutaFoto = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO));
            float valoracion = cursor.getFloat(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_VALORACION));

            Log.d("TIPO_LUGAR", "Tipo de lugar seleccionado: " + valoracion);

            lugaresList.add(new Lugar(nombre, direccion, tfno, url, fecha, tipoString, rutaFoto, valoracion));
        }

        cursor.close();
        dbHelper.close();

        return lugaresList;
    }




    public static void actualizarLista() {
        // Obtener la instancia actual de ListLugares y actualizar la lista
        if (instance != null && instance.adapter != null) {
            // Obtener la lista actualizada desde la base de datos
            ArrayList<Lugar> lugaresActualizados = instance.obtenerListaDeLugaresDesdeBD();

            // Limpiar la lista actual y agregar los nuevos datos
            instance.lugares.clear();
            instance.lugares.addAll(lugaresActualizados);

            // Notificar al adaptador que los datos han cambiado
            instance.adapter.notifyDataSetChanged();
        }
    }



    public static void eliminarLugarPorNombre(String nombreLugar) {
        for (int i = 0; i < lugares.size(); i++) {
            if (lugares.get(i).getNombre().equals(nombreLugar)) {
                lugares.remove(i);
                break;
            }
        }
        dbHelper.eliminarLugarPorNombre(nombreLugar);

        // Actualizar la interfaz de usuario
        if (instance != null) {
            instance.runOnUiThread(() -> {
                instance.adapter.notifyDataSetChanged();
            });
        }
    }



}