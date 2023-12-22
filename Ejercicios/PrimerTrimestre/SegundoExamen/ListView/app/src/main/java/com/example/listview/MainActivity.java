package com.example.listview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        ImageView addButton = findViewById(R.id.addButton);
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_layout, R.id.itemText, itemList);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes abrir un cuadro de diálogo o una actividad para ingresar datos y agregar un nuevo elemento a itemList.
                // Supongamos que agregas un nuevo elemento con el texto "Nuevo Elemento".
                itemList.add("Nuevo Elemento");
                adapter.notifyDataSetChanged();

                // Mostrar un Snackbar para informar al usuario
                Snackbar.make(view, "Elemento agregado", Snackbar.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Estás seguro de eliminar este elemento?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            //metodo si decimos que si
                            public void onClick(DialogInterface dialog, int id) {
                                // Lógica para eliminar el elemento y actualizar la lista.
                                itemList.remove(position);
                                adapter.notifyDataSetChanged();
                                Snackbar.make(view, "Elemento eliminado", Snackbar.LENGTH_SHORT).show();                            }
                        })
                        //metodo si decimos que no
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });
    }
}