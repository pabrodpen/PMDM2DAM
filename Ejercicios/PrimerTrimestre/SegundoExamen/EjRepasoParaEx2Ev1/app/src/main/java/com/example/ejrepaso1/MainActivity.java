package com.example.ejrepaso1;
/*-REPETIR ACT LISTVIEW Y HACER LO MISMO PERO CON EL BOTON FLOTANTE
 */
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ListView l;
    ArrayList<Elemento> itemList;
    ArrayAdapter<Elemento> adapter;
    int nElemento=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar t=findViewById(R.id.toolbar);
        setSupportActionBar(t);


        l=findViewById(R.id.listView);
        itemList=new ArrayList<>();
        adapter = new ElementoAdapter(this,itemList);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Snackbar.make(view,"Elemento "+i+ " seleccionado",Snackbar.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Estás seguro de eliminar este elemento?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            //metodo si decimos que si
                            public void onClick(DialogInterface dialog, int id) {
                                // Lógica para eliminar el elemento y actualizar la lista.
                                itemList.remove(i);
                                adapter.notifyDataSetChanged();
                                Snackbar.make(view, "Elemento eliminado", Snackbar.LENGTH_SHORT).show();                            }

                        })
                        //metodo si decimos que no
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder.create().show();
            }
        });

        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                itemList.add(new Elemento("Elemento",R.drawable.paisaje));
                adapter.notifyDataSetChanged();
                nElemento++;
                Snackbar.make(view,"Elemento "+nElemento+" agregado",Snackbar.LENGTH_SHORT).show();
            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSnackbar("Se ha seleccionado el boton cloud");
            return true;
        }else if(id==R.id.action_remove){
            itemList.clear();
            adapter.notifyDataSetChanged();
            showSnackbar("Se han borrado los elementos");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar(String s) {
        Snackbar.make(findViewById(android.R.id.content),s,Snackbar.LENGTH_SHORT).show();
    }


}