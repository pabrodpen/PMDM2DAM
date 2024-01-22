// SecondFragment.java
package com.example.miapppablorodriguezpenhia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SecondFragment extends Fragment implements DialogLista.OnTipoLugarSelectedListener {

    EditText editTextNombre, editTextDirecc, editTextTfno, editTextURL;
    String datoTipoLugar;
    Button dialogLista;
    FeedReaderDbHelper dbHelper;

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
}
