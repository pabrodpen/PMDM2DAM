package com.example.fragmentodinamico;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.fragmentodinamico.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    boolean cargarFragmento2=true;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.container);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            //modificar el fragmento que sale en la pantalla
            @Override
            public void onClick(View view) {

                if(cargarFragmento2){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new SecondFragment())
                            .commit();
                    cargarFragmento2=false;
                }else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new FirstFragment())
                            .commit();
                    cargarFragmento2=true;
                }
            }
        });

        // Rescatamos el contenedor y le vamos a cargar un fragmento
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new FirstFragment())
                .commit();

        getSupportActionBar().setTitle("Fragments Dinamicos");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
