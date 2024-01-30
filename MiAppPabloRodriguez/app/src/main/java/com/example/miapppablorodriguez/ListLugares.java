package com.example.miapppablorodriguez;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListLugares extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Lugar> adapter;
    private ArrayList<Lugar> lugares;
    private static ListLugares instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        // Asignar la instancia actual a la variable estática
        instance = this;

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializar la lista de lugares y el adaptador
        lugares = obtenerListaDeLugaresDesdeBD();
        adapter = new AdaptadorLugar(this, lugares);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Log.d("LIST_DEBUG", "Número de elementos en la lista: " + lugares.size());

        // Notificar al adaptador que los datos han cambiado
        adapter.notifyDataSetChanged();

        // Configurar el listener para el clic en un elemento de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

                startActivity(detallesIntent);
            }
        });

        // Habilitar opciones de menú
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargar imágenes en los elementos de la lista
        for (int i = 0; i < listView.getCount(); i++) {
            View itemView = listView.getChildAt(i);
            if (itemView != null) {
                ImageView imageView = itemView.findViewById(R.id.image);
                Lugar lugar = (Lugar) listView.getItemAtPosition(i);
                cargarInformacionLugar(lugar, imageView);
            }
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
                FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO // Nuevo atributo para la ruta de la foto
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

            // Nuevo atributo para la ruta de la foto
            String rutaFoto = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO));

            lugaresList.add(new Lugar(nombre, direccion, tfno, url, fecha, tipoString, rutaFoto));
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

    private void eliminarTodosLosLugares() {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Eliminar todos los registros de la tabla
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null);

        // Cerrar la conexión con la base de datos
        db.close();
        dbHelper.close();
    }

    public static void eliminarLugarPorNombre(String nombre) {
        if (instance != null && instance.adapter != null) {
            // Buscar el lugar en la lista por su nombre
            for (Lugar lugar : instance.lugares) {
                if (lugar.getNombre().equals(nombre)) {
                    // Remover el lugar de la lista
                    instance.lugares.remove(lugar);
                    // Notificar al adaptador que los datos han cambiado
                    instance.adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    private void cargarInformacionLugar(Lugar lugar, ImageView imageView) {
        String rutaFoto = lugar.getRutaFoto();

        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            // Utiliza Glide para cargar la imagen desde la ruta almacenada en la base de datos
            Glide.with(this)
                    .load(rutaFoto)
                    .into(imageView);
        }
    }
}
