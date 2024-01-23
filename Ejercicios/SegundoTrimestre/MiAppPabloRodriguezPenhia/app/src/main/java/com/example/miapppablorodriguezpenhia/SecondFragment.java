// SecondFragment.java
package com.example.miapppablorodriguezpenhia;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SecondFragment extends Fragment implements DialogLista.OnTipoLugarSelectedListener {

    EditText editTextNombre, editTextDirecc, editTextTfno, editTextURL;
    String datoTipoLugar;
    Button dialogLista;
    FeedReaderDbHelper dbHelper;

    private static final int REQUEST_CODE_GALERIA = 1001;  // Puedes usar cualquier número


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDirecc = view.findViewById(R.id.editTextTextPostalAddress);
        editTextTfno = view.findViewById(R.id.editTextPhone);
        dialogLista = view.findViewById(R.id.buttonDialogo);
        editTextURL = view.findViewById(R.id.editTextUrl);

        dbHelper = new FeedReaderDbHelper(getActivity());

        dialogLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLista dialogLista = new DialogLista();
                dialogLista.setTargetFragment(SecondFragment.this, 0);
                dialogLista.show(getFragmentManager(), "DialogLista");
            }
        });

        Button buttonSeleccionarImagen = view.findViewById(R.id.buttonImg);
        buttonSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la galería para seleccionar una imagen
                seleccionarImagenDeGaleria();
            }
        });



        Button buttonInsertar = view.findViewById(R.id.buttonInsertar);
        buttonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper != null) {
                    insertarInformacion(dbHelper);
                    // Llamada para actualizar la lista en FirstFragment
                    actualizarListaEnFirstFragment();
                    dbHelper.close();
                }
            }
        });

        return view;
    }

    public void insertarInformacion(FeedReaderDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nombre = editTextNombre.getText().toString();
        String direccion = editTextDirecc.getText().toString();
        String telefono = editTextTfno.getText().toString();
        String url = editTextURL.getText().toString();

        //para coger la ruta de la img a partir de la seleccio de la galeria
        // Obtener el objeto Lugar del bundle
        Bundle bundle = getArguments();
        Lugar lugar = (Lugar) bundle.getSerializable("lugar");


        Log.d("INSERT_OPERATION", "Insertando datos:");
        Log.d("INSERT_OPERATION", "Nombre: " + nombre);
        Log.d("INSERT_OPERATION", "Dirección: " + direccion);
        Log.d("INSERT_OPERATION", "Teléfono: " + telefono);
        Log.d("INSERT_OPERATION", "URL: " + url);

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIPO, datoTipoLugar);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION, direccion);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO, telefono);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, url);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RUTA_FOTO, lugar.getRutaFoto());


        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        Log.d("INSERT_OPERATION", "Nueva fila insertada con ID: " + newRowId);


        limpiarCampos();
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextDirecc.setText("");
        editTextTfno.setText("");
        editTextURL.setText("");
    }

    public void onTipoLugarSelected(String tipoLugar) {
        datoTipoLugar = tipoLugar;
        }

    private void actualizarListaEnFirstFragment() {
        // Obtén el FragmentManager de la actividad
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Encuentra el FirstFragment por su etiqueta
        FirstFragment firstFragment = (FirstFragment) fragmentManager.findFragmentByTag("FirstFragmentTag");

        // Verifica si el fragmento no es nulo y si implementa la interfaz
        if (firstFragment != null) {
            // Llamada para actualizar la lista en FirstFragment
            firstFragment.actualizarListaDesdeBD();
        }
    }

    //para iniciar la actividad de selección de imágenes:
    private void seleccionarImagenDeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALERIA);
    }

    // para manejar el resultado de la selección de imágenes:
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Lugar lugar = new Lugar(); // Asegúrate de importar la clase Lugar


        if (requestCode == REQUEST_CODE_GALERIA && resultCode == Activity.RESULT_OK && data != null) {
            // La imagen se seleccionó exitosamente
            Uri imagenUri = data.getData();

            // Actualiza tu objeto Lugar con la ruta de la imagen seleccionada
            String rutaFoto = obtenerRutaDesdeUri(imagenUri);
            lugar.setRutaFoto(rutaFoto);

            // Actualiza la vista previa de la imagen en tu interfaz de usuario si es necesario
            ImageView imageView = requireView().findViewById(R.id.image);
            if (rutaFoto != null) {
                imageView.setImageURI(imagenUri);
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }

    //para convertir la URI de la imagen seleccionada en la ruta del archivo real:
    private String obtenerRutaDesdeUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String ruta = cursor.getString(columnIndex);
            cursor.close();
            return ruta;
        }

        return null;
    }


}
