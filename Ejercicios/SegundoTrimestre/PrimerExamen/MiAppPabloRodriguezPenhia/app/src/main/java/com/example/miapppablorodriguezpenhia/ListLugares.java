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
        setContentView(R.layout.fragment_list); // Cambiado a lugar.xml

        instance = this;
        dbHelper = new FeedReaderDbHelper(this); // Initialize dbHelper here

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

    // Método para cargar imágenes de manera asíncrona
    private void cargarImagenesAsync() {
        new Thread(() -> {
            for (int i = 0; i < listView.getCount(); i++) {
                View itemView = listView.getChildAt(i);
                if (itemView != null) {
                    ImageView imageView = itemView.findViewById(R.id.image);
                    Lugar lugar = lugares.get(i);
                    cargarInformacionLugar(lugar, imageView);
                }
            }
        }).start();
    }

    // Método para cargar información de lugar en un ImageView
    private void cargarInformacionLugar(Lugar lugar, ImageView imageView) {
        String rutaFoto = lugar.getRutaFoto();

        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            // Utiliza Glide para cargar la imagen desde la ruta almacenada en la base de datos
            runOnUiThread(() -> Glide.with(this)
                    .load(rutaFoto)
                    .into(imageView));
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cambiarAInsertar) {
            // Iniciar la actividad de inserción
            Intent insertarIntent = new Intent(this, InsertarLugar.class);
            startActivity(insertarIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public static void actualizarValoracionLugar(String nombre, float nuevaValoracion) {
        // Obtener la instancia actual de ListLugares y actualizar la valoración en la lista
        if (instance != null) {
            instance.actualizarValoracionEnBD(nombre, nuevaValoracion);
        }
    }

    private void actualizarValoracionEnBD(String nombre, float nuevaValoracion) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_VALORACION, nuevaValoracion);

        String whereClause = FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE + "=?";
        String[] whereArgs = {nombre};

        // Actualizar la valoración en la base de datos
        db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, whereClause, whereArgs);

        // Cerrar la conexión con la base de datos
        db.close();
        dbHelper.close();
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

    private boolean esTablet() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int anchoPantalla = displayMetrics.widthPixels / displayMetrics.densityDpi;
        int altoPantalla = displayMetrics.heightPixels / displayMetrics.densityDpi;

        double tamanioPantalla = Math.sqrt(Math.pow(anchoPantalla, 2) + Math.pow(altoPantalla, 2));

        // Si el tamaño de la pantalla es mayor o igual a 7 pulgadas, se considera una tablet
        return tamanioPantalla >= 7.0;
    }

    private void abrirDetallesLugar(int position) {
        Log.d("CLICK", "Abrir DetallesLugar para la posición: " + position);

        Lugar lugarSeleccionado = lugares.get(position);

        // Crear una instancia de DetallesLugar y pasar los detalles del lugar como extras en el Intent
        Intent detallesIntent = new Intent(ListLugares.this, DetallesLugar.class);
        detallesIntent.putExtra("nombre", lugarSeleccionado.getNombre());
        detallesIntent.putExtra("tipo", lugarSeleccionado.getTipo().toString());
        detallesIntent.putExtra("fecha", lugarSeleccionado.getFecha());
        detallesIntent.putExtra("url", lugarSeleccionado.getUrl());
        detallesIntent.putExtra("tfno", lugarSeleccionado.getTfno());
        detallesIntent.putExtra("direccion", lugarSeleccionado.getDireccion());
        detallesIntent.putExtra("rutaFoto", lugarSeleccionado.getRutaFoto());
        detallesIntent.putExtra("valoracion", lugarSeleccionado.getValoracion());

        startActivity(detallesIntent);
    }

    // Método para cargar la lista de lugares desde la base de datos de manera asíncrona


}