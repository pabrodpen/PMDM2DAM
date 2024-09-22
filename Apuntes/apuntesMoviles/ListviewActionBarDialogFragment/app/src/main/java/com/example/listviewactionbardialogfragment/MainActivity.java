package com.example.listviewactionbardialogfragment;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.listviewactionbardialogfragment.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
    LA DIFERENCIA DE CODIGO RESPECTO A LISTVIEW CON UN BOTON NORMAL


    COGEMOS EL BASIC VIEWS ACTIVITY

    *BORRAMOS LAS 2 CLASES Y XML DE LOS FRAGMENTOS, ADEMAS DEL CONTENTMAIN
    * EL CONTENT MAIN LO UNICO QUE HACE ES CAMBIAR LOS FRAGMENTOS

    *RESPECTO A LA DECLARACION EN EL .JAVA, DEJAMOS COMO ESTA EL BINDING Y EL APPBAR Y AÑADIMOS
    EL LISTVIEW, EL ELEMENTO Y ADAPTADOR

    *QUITAMOS TODO LO RELACIONADO CON NAVCONTROLLER Y NAVIGATION(YA QUE ESO TIENE QUE VER CON LOS FRAGMENT)
    *INCLUIDO EL ULTIMO METODO ONSUPPORTNAVIGATEUP

    *QUITAMOS TODO LO RELACIONADO C0N BINDING Y FAB(FAB es el boton flotante)
    *
    * EN EL ONCREATE RELACIONAMOS EL LISTVIEW,EL ELEMENTO Y EL ADAPTADOR(INCLUIDO EL SETADAPTER)
    *
    * EN EL OPTIONITEMSELECTED LO UNICO QUE HACEMOS ES EN EL IF ES AÑADIR EL ELEMENTO Y
    * TODO LO CORRESPODIENTE


    EN EL MENUMAIN LO UNICO QUE HACEMOS ES AÑADIR UN ICONO DE +
    * */

    private ListView listView;
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//creamos el toolbar

        // Obtener el color desde el recurso de colores de manera compatible
        int color = ContextCompat.getColor(this, R.color.blue);

        // Cambiar el color del Toolbar usando el recurso de colores
        toolbar.setBackgroundColor(color);

        listView = findViewById(R.id.listView);
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_layout, R.id.itemText, itemList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override//al poner el setOnItemClickListener se nos pone este metodo por defecto
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                                dialog.cancel();
                            }
                        });

                builder.create().show();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//este metodo esta por defecto
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
        if (id == R.id.action_add) {//si pulsamos el boton +

            // Aquí puedes abrir un cuadro de diálogo o una actividad para ingresar datos y agregar un nuevo elemento a itemList.
            // Supongamos que agregas un nuevo elemento con el texto "Nuevo Elemento".
            itemList.add("Nuevo Elemento");
            adapter.notifyDataSetChanged();

            // Mostrar un Snackbar para informar al usuario
            Snackbar.make(findViewById(android.R.id.content), "Elemento agregado", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}