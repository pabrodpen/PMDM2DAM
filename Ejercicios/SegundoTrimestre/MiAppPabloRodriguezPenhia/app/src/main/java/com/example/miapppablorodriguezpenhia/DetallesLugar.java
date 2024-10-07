package com.example.miapppablorodriguezpenhia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetallesLugar extends AppCompatActivity {

    TextView tNombre, tTipo, tFecha, tUrl, tTfno, tUbicacion;

    String direccion;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_IMAGE_GALLERY = 3;
    private Uri imagenUri;

    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (esTablet()) {
            setContentView(R.layout.activity_detalles_tablet);
        } else {
            setContentView(R.layout.fragment_detalles_lugar);
        }

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);



        RatingBar ratingBar = findViewById(R.id.ratingBar2);

        tNombre = findViewById(R.id.editTextNombre);
        tTipo = findViewById(R.id.editTextTipoEditar);
        tFecha = findViewById(R.id.editTextFechaEditar);
        tUrl = findViewById(R.id.editTextUrl);
        tTfno = findViewById(R.id.ediTextTfno);
        tUbicacion = findViewById(R.id.editTextUbicacion);
        ratingBar = findViewById(R.id.ratingBar2);

        // Configurar la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Leer los extras del Intent
        Intent intent = getIntent();




        if (intent != null) {
            String nombre = intent.getStringExtra("nombre");
            String direccion = getIntent().getStringExtra("direccion");
            String tipo = intent.getStringExtra("tipo");
            String fecha = intent.getStringExtra("fecha");
            String url = intent.getStringExtra("url");
            String tfno = intent.getStringExtra("tfno");
            float valoracion = intent.getFloatExtra("valoracion",0);

            // Actualizar los EditText con los valores
            tNombre.setText(nombre);
            tTipo.setText(tipo);
            tFecha.setText(fecha);
            tUrl.setText(url);
            tTfno.setText(tfno);
            tUbicacion.setText(direccion);
            ratingBar.setRating(valoracion);

            // Determinar la imagen según el tipo de lugar
            if ("Cafetería".equals(tipo)) {
                // Es un restaurante, establecer la imagen correspondiente
                ImageView imageView4 = findViewById(R.id.imageView4);
                imageView4.setImageResource(R.drawable.baseline_coffee_24);
            } else if ("Gourmet".equals(tipo)) {
                // Es un parque, establecer la imagen correspondiente
                ImageView imageView4 = findViewById(R.id.imageView4);
                imageView4.setImageResource(R.drawable.baseline_workspace_premium_24);
            } else if ("Pescados Y Mariscos".equals(tipo)) {
                // Es un parque, establecer la imagen correspondiente
                ImageView imageView4 = findViewById(R.id.imageView4);
                imageView4.setImageResource(R.drawable.baseline_set_meal_24);
            } else if ("Asador".equals(tipo)) {
                // Es un parque, establecer la imagen correspondiente
                ImageView imageView4 = findViewById(R.id.imageView4);
                imageView4.setImageResource(R.drawable.baseline_outdoor_grill_24);
            } else if ("Tapas".equals(tipo)) {
                // Es un parque, establecer la imagen correspondiente
                ImageView imageView4 = findViewById(R.id.imageView4);
                imageView4.setImageResource(R.drawable.baseline_tapas_24);
            } else if ("Fast Food".equals(tipo)) {
                // Es un parque, establecer la imagen correspondiente
                ImageView imageView4 = findViewById(R.id.imageView4);
                imageView4.setImageResource(R.drawable.baseline_fastfood_24);
            } else if ("Copas".equals(tipo)) {
                // Es un parque, establecer la imagen correspondiente
                ImageView imageView4 = findViewById(R.id.imageView4);
                imageView4.setImageResource(R.drawable.baseline_local_bar_24);
            }
        }



        // En el método onCreate o donde configuras tus vistas
        tUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén la URL del EditText
                String url = tUrl.getText().toString();

                // Verifica que la URL no esté vacía
                if (!TextUtils.isEmpty(url)) {
                    try {
                        // Crea un Intent para abrir la URL en un navegador web
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                        // Maneja cualquier excepción que pueda surgir al abrir la URL
                        Toast.makeText(DetallesLugar.this, "Error al abrir la URL", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

        tTfno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el número de teléfono del EditText
                String tfno = tTfno.getText().toString();

                // Verifica que el número de teléfono no esté vacío
                if (!TextUtils.isEmpty(tfno)) {
                    try {
                        // Crea un Intent para ver el contacto con el número de teléfono
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + tfno));
                        startActivity(intent);
                    } catch (Exception e) {
                        // Maneja cualquier excepción que pueda surgir al abrir la aplicación de contactos
                        Toast.makeText(DetallesLugar.this, "Error al abrir la aplicación de contactos", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

        tUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String direccion = tUbicacion.getText().toString();

                // Verifica que la dirección no esté vacía o sea nula
                if (!TextUtils.isEmpty(direccion)) {
                    // La dirección tiene un valor, llamar a mostrarGeolocalizacion
                    boolean geolocalizacionExitosa = mostrarGeolocalizacion(direccion);

                    if (!geolocalizacionExitosa) {
                        // Muestra un mensaje indicando que no se encontraron resultados
                        Toast.makeText(DetallesLugar.this, "No se encontraron resultados para la dirección", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // La dirección está vacía, muestra un mensaje o realiza alguna acción adecuada
                    Toast.makeText(DetallesLugar.this, "La dirección está vacía", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalles_lugar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.volverAList) {
            Intent intent = new Intent(DetallesLugar.this, ListLugares.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.hacerFoto) {
            tomarFoto();
            return true;
        } else if (id == R.id.fotoGaleria) {
            seleccionarFotoDeGaleria();
            return true;
        } else if (id == R.id.eliminar) {
            String nombreLugar = tNombre.getText().toString();
            ListLugares.eliminarLugarPorNombre(nombreLugar);
            finish();
            return true;
        } else if (id == R.id.editar) {
            // Obtener el nombre del lugar desde el TextView
            String nombreLugar = String.valueOf(tNombre.getText()); // Obtener el nombre del lugar

            // Aquí está la clave para pasar el nombre del lugar
            Intent intent = new Intent(DetallesLugar.this, EditarLugar.class);
            intent.putExtra("nombre", nombreLugar);  // Asegúrate de que esta clave sea la misma en EditarLugar
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean mostrarGeolocalizacion(String direccion) {
        if (direccion == null || direccion.isEmpty()) {
            // La dirección está vacía, muestra un mensaje o realiza alguna acción adecuada
            Toast.makeText(DetallesLugar.this, "La dirección está vacía", Toast.LENGTH_SHORT).show();
            return false;  // Retorna false indicando que la geolocalización no fue exitosa
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(direccion, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitud = address.getLatitude();
                double longitud = address.getLongitude();



                // Ahora puedes utilizar geoPunto.getLongitud() y geoPunto.getLatitud() como desees
                // Por ejemplo, puedes abrir un mapa con estas coordenadas
                Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?z=15");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Asegura que se abra en Google Maps
                startActivity(mapIntent);
                return true;  // Retorna true indicando que la geolocalización fue exitosa
            } else {
                // No se encontraron resultados para la dirección
                // Maneja esta situación según tus necesidades
                Toast.makeText(DetallesLugar.this, "No se encontraron resultados para la dirección", Toast.LENGTH_SHORT).show();
                return false;  // Retorna false indicando que la geolocalización no fue exitosa
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Maneja el error de geocodificación según tus necesidades
            Toast.makeText(DetallesLugar.this, "Error de geocodificación", Toast.LENGTH_SHORT).show();
            return false;  // Retorna false indicando que la geolocalización no fue exitosa
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // Maneja el error de argumento no válido según tus necesidades
            Toast.makeText(DetallesLugar.this, "Argumento no válido", Toast.LENGTH_SHORT).show();
            return false;  // Retorna false indicando que la geolocalización no fue exitosa
        }
    }

    public void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void seleccionarFotoDeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Foto tomada con éxito

            // Obtén la imagen de la cámara desde el objeto Intent
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Actualiza la imagen en el ImageView
            ImageView imageView4 = findViewById(R.id.imageView4);
            imageView4.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null) {
            // Foto seleccionada de la galería
            imagenUri = data.getData();

            // Actualiza la imagen en el ImageView
            ImageView imageView4 = findViewById(R.id.imageView4);
            imageView4.setImageURI(imagenUri);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


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

}