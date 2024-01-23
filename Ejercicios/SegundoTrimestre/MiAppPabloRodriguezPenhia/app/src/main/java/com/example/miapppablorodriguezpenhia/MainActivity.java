package com.example.miapppablorodriguezpenhia;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements DialogLista.OnTipoLugarSelectedListener{

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;


    boolean cargarFragmento2 = true;
    boolean cargarFragmento3=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar el NavHostFragment y NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        navController = navHostFragment.getNavController();

        // Configurar la barra de acciones con el NavController
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_switch_fragment) {
            // Verifica si debemos cargar el fragmento 1 o el fragmento 2
            if (cargarFragmento2) {
                // Navega al fragmento SecondFragment utilizando NavController
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment);
            } else {
                // Navega al fragmento FirstFragment utilizando NavController
                navController.navigate(R.id.action_SecondFragment_to_FirstFragment);
            }

            // Invierte el valor de cargarFragmento2
            cargarFragmento2 = !cargarFragmento2;

            return true;
        }

        if (id == R.id.action_switch2_fragment) {
            if (cargarFragmento3) {
                // Navega al fragmento DetallesLugar utilizando NavController
                navController.navigate(R.id.action_first_to_detalles);
            } else {
                // Navega al fragmento FirstFragment utilizando NavController
                navController.navigate(R.id.action_detalles_to_first);
            }
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onTipoLugarSelected(String tipoLugar) {

    }
}
