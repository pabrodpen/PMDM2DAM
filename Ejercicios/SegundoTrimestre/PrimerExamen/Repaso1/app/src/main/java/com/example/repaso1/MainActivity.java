package com.example.repaso1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    /*
    * Enunciado: Aplicación de Gestión de Tareas

Desarrolla una aplicación Android en Java para la gestión de tareas personales que incorpore las siguientes funcionalidades:

Pantalla de Preferencias:

Implementa una pantalla de preferencias utilizando PreferencesActivity donde el usuario pueda configurar ajustes como la notificación de recordatorios, el color de fondo, y la preferencia de ordenación de tareas.
Gestión de Tareas:

Crea una interfaz de usuario que permita al usuario agregar nuevas tareas con un título, descripción, fecha de vencimiento y prioridad.
Implementa la capacidad de editar y actualizar la información de una tarea existente.
Permite al usuario eliminar tareas.
Almacenamiento Persistente:

Utiliza SharedPreferences para almacenar las preferencias de la aplicación, como el estado de la notificación y las preferencias de ordenación.
Implementa una base de datos SQLite para almacenar y gestionar las tareas. La base de datos debe contener al menos las siguientes columnas: id, titulo, descripcion, fecha_vencimiento y prioridad.
Listado de Tareas:

Muestra una lista de tareas en la interfaz de usuario, permitiendo al usuario ordenarlas según su preferencia (por fecha de vencimiento, prioridad, etc.).
Al hacer clic en una tarea, se debe mostrar su información detallada, y permitir la edición y eliminación de la tarea.
Notificaciones:

Si la opción de notificaciones está habilitada en las preferencias, muestra recordatorios de tareas pendientes mediante notificaciones push en el dispositivo.
Asegúrate de seguir las mejores prácticas de desarrollo de Android, como la separación de la lógica de negocios y la interfaz de usuario, y documenta adecuadamente tu código. La aplicación debe ser intuitiva y fácil de usar.


    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.container);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new FragmentPreferencias())
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.btnFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Detalles())
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}