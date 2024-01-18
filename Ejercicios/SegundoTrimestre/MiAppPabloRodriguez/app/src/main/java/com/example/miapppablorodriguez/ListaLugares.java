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
        lugares = obtenerListaDeLugares();  // Implementa este método para obtener tus lugares
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lugares);

        // Configurar el ListView
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;
    }

    // Método para obtener la lista de lugares (debes implementarlo según tus necesidades)
    private ArrayList<Lugar> obtenerListaDeLugares() {
        // Implementa este método para obtener y devolver tu lista de lugares
        // Puedes cargar datos de un origen de datos, base de datos, etc.
        // Aquí solo se proporciona un ejemplo básico
        ArrayList<Lugar> lugares = new ArrayList<>();
        //lugares.add(new Lugar("Nombre1", "Dirección1", /* otros atributos */));
        //lugares.add(new Lugar("Nombre2", "Dirección2", /* otros atributos */));
        // Agrega más lugares según sea necesario
        return lugares;
    }
}
