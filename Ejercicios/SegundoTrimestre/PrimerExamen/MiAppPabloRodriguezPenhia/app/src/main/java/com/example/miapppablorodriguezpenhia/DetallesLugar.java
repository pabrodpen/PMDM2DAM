package com.example.miapppablorodriguezpenhia;

import android.Manifest;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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

    FeedReaderDbHelper dbHelper;
    private ListLugares listLugares;

    private int lugarId;  // Variable de instancia para almacenar el ID del lugar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar FeedReaderDbHelper
        dbHelper = new FeedReaderDbHelper(this);
        listLugares = new ListLugares();

        if (esTablet()) {
            setContentView(R.layout.activity_detalles_tablet);
        } else {
            setContentView(R.layout.fragment_detalles_lugar);
        }

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ratingBar = findViewById(R.id.ratingBar2);

        tNombre = findViewById(R.id.editTextNombre);
        tTipo = findViewById(R.id.editTextTipoEditar);
        tFecha = findViewById(R.id.editTextFechaEditar);
        tUrl = findViewById(R.id.editTextUrl);
        tTfno = findViewById(R.id.ediTextTfno);
        tUbicacion = findViewById(R.id.editTextUbicacion);

        // Configurar la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Leer los extras del Intent
        Intent intent = getIntent();

        if (intent != null) {
            // Obtener instancia de ListLugares
            listLugares = (ListLugares) intent.getSerializableExtra("listLugares");

            lugarId = intent.getIntExtra("id", -1);  // Obtener el ID del lugar desde el Intent

            String nombre = intent.getStringExtra("nombre");
            direccion = intent.getStringExtra("direccion");
            String tipo = intent.getStringExtra("tipo");
            String fecha = intent.getStringExtra("fecha");
            String url = intent.getStringExtra("url");
            String tfno = intent.getStringExtra("tfno");
            float valoracion = intent.getFloatExtra("valoracion", 0);

            // Actualizar los EditText con los valores
            tNombre.setText(nombre);
            tTipo.setText(tipo);
            tFecha.setText(fecha);
            tUrl.setText(url);
            tTfno.setText(tfno);
            tUbicacion.setText(direccion);
            ratingBar.setRating(valoracion);

            // Determinar la imagen según el tipo de lugar
            ImageView imageView4 = findViewById(R.id.imageView4);
            switch (tipo) {
                case "Cafetería":
                    imageView4.setImageResource(R.drawable.baseline_coffee_24);
                    break;
                case "Gourmet":
                    imageView4.setImageResource(R.drawable.baseline_workspace_premium_24);
                    break;
                case "Pescados Y Mariscos":
                    imageView4.setImageResource(R.drawable.baseline_set_meal_24);
                    break;
                case "Asador":
                    imageView4.setImageResource(R.drawable.baseline_outdoor_grill_24);
                    break;
                case "Tapas":
                    imageView4.setImageResource(R.drawable.baseline_tapas_24);
                    break;
                case "Fast Food":
                    imageView4.setImageResource(R.drawable.baseline_fastfood_24);
                    break;
                case "Copas":
                    imageView4.setImageResource(R.drawable.baseline_local_bar_24);
                    break;
            }
        }

        // Configurar los listeners para URL, teléfono y ubicación
        tUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = tUrl.getText().toString();
                if (!TextUtils.isEmpty(url)) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                        Toast.makeText(DetallesLugar.this, "Error al abrir la URL", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

        tTfno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tfno = tTfno.getText().toString();
                if (!TextUtils.isEmpty(tfno)) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + tfno));
                        startActivity(intent);
                    } catch (Exception e) {
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
                if (!TextUtils.isEmpty(direccion)) {
                    boolean geolocalizacionExitosa = mostrarGeolocalizacion(direccion);
                    if (!geolocalizacionExitosa) {
                        Toast.makeText(DetallesLugar.this, "No se encontraron resultados para la dirección", Toast.LENGTH_SHORT).show();
                    }
                } else {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmar eliminación")
                    .setMessage("¿Estás seguro de que deseas eliminar este elemento?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String nombreLugar = tNombre.getText().toString();
                            ListLugares.eliminarLugarPorNombre(nombreLugar);
                            Toast.makeText(DetallesLugar.this, "Lugar eliminado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        } else if (id == R.id.editar) {
            int lugarId = getLugarId(); // Obtener el ID del lugar actual
            Intent intent = new Intent(DetallesLugar.this, EditarLugar.class);
            intent.putExtra("id", lugarId);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean mostrarGeolocalizacion(String direccion) {
        if (direccion == null || direccion.isEmpty()) {
            Toast.makeText(DetallesLugar.this, "La dirección está vacía", Toast.LENGTH_SHORT).show();
            return false;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(direccion, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitud = address.getLatitude();
                double longitud = address.getLongitude();

                Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?z=15");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                return true;
            } else {
                Toast.makeText(DetallesLugar.this, "No se encontraron resultados para la dirección", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(DetallesLugar.this, "Error al obtener la geolocalización", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private int getLugarId() {
        return lugarId;
    }

    private boolean esTablet() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt((widthInches * widthInches) + (heightInches * heightInches));
        return diagonalInches >= 7.0;
    }

    private void tomarFoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            abrirCamara();
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void seleccionarFotoDeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView imageView = findViewById(R.id.imageView4);
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            imagenUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagenUri);
                ImageView imageView = findViewById(R.id.imageView4);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(this, "Permiso denegado para escribir en el almacenamiento externo", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
