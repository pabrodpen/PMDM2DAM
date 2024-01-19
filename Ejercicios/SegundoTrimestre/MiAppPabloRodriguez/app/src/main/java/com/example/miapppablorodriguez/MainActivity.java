package com.example.miapppablorodriguez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    boolean cargarFragmento2=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        NavController navController = Navigation.findNavController(this, R.id.container);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);




        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ListaLugares())
                .commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int color = ContextCompat.getColor(this, R.color.colorToolbar);
        toolbar.setBackgroundColor(color);

        // Inflar el menú del Toolbar
        toolbar.inflateMenu(R.menu.menu_main);
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
            if(cargarFragmento2){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new DetallesLugar())
                        .commit();
                cargarFragmento2=false;
            }else{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ListaLugares())
                        .commit();
                cargarFragmento2=true;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}