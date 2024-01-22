// FirstFragment.java
package com.example.miapppablorodriguezpenhia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FirstFragment extends Fragment  {

    private ListView listView;
    private ArrayAdapter<Lugar> adapter;
    private ArrayList<Lugar> lugares;
    boolean cargarFragemntoDetalles=true;

    private OnDataChangedListener onDataChangedListener;

    FirstFragment firstFragment;//instanciamos firstFragment para poder pasar datos de la BD(2 fr)al la list(1 fr)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);


        // Inicializar la lista de lugares y el adaptador
        lugares = obtenerListaDeLugaresDesdeBD();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lugares);

        // Configurar el ListView
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el lugar seleccionado
                Lugar lugarSeleccionado = lugares.get(position);

                // Obtener el NavController desde el NavHostFragment
                NavController navController = NavHostFragment.findNavController(FirstFragment.this);

                /*Navegar al fragmento DetallesLugar y pasar el lugar como argumento
                Bundle bundle = new Bundle();
                bundle.putParcelable("lugar", lugarSeleccionado);*/
                navController.navigate(R.id.action_first_to_detalles);
            }
        });


        return view;
    }

    // Método para obtener la lista de lugares desde la base de datos
    private ArrayList<Lugar> obtenerListaDeLugaresDesdeBD() {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(requireContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_URL,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO
        };


        String selection = "1";
        String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE + " ASC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<Lugar> lugaresList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE));
            String direccion = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION));
            String tfno=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TFNO));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_URL));
    //no hace falta el tipo xq no lo ponemos cuando se muestra en el listview, sino en los detalles
            SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
            String fechaInsercion=formato1.format(new Date());

            SimpleDateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
            String horaInsercion=formato2.format(new Date());


            lugaresList.add(new Lugar(nombre, direccion,tfno, url,fechaInsercion,horaInsercion));
        }

        cursor.close();
        dbHelper.close();

        return lugaresList;
    }

    public void actualizarListaDesdeBD() {
        lugares.clear();  // Limpiar la lista actual
        lugares.addAll(obtenerListaDeLugaresDesdeBD());  // Actualizar la lista con los nuevos datos
        adapter.notifyDataSetChanged();  // Notificar al adaptador sobre los cambios
    }

    public void setOnDataChangedListener(OnDataChangedListener listener){
        this.onDataChangedListener=listener;
    }

    public void onDataChanged() {
             actualizarListaDesdeBD();
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnDataChangedListener) {
            onDataChangedListener = (OnDataChangedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDataChangedListener=null;
    }
}
