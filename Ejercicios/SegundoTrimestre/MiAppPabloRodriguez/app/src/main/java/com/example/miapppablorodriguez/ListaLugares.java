package com.example.miapppablorodriguez;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.miapppablorodriguez.Lugar;
import com.example.miapppablorodriguez.R;

import java.util.ArrayList;

public class ListaLugares extends Fragment {

    private ListView listView;
    private ArrayAdapter<Lugar> adapter;
    private ArrayList<Lugar> lugares;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_lugares, container, false);

        // Inicializar la lista de lugares y el adaptador
        //lugares = obtenerListaDeLugares();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lugares);

        // Configurar el ListView
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;
    }

    // Método para obtener la lista de lugares
    //private ArrayList<Lugar> obtenerListaDeLugares() {

    //}
}
