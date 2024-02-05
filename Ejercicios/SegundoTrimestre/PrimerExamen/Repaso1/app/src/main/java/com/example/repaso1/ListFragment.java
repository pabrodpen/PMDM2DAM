package com.example.repaso1;

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

import java.util.ArrayList;

public class ListFragment extends Fragment {

    ArrayAdapter<Tarea> adapter;
    ListView listView;
    ArrayList<Tarea> tareas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list,container,false);

        tareas=new ArrayList<>();//imp inicializarlo

        adapter=new Adapter(requireContext(),tareas);
        listView.setAdapter(adapter);

        tareas.add(new Tarea("Tarea 1", "Descripción de la tarea 1", "2022-12-31", 1));
        tareas.add(new Tarea("Tarea 2", "Descripción de la tarea 2", "2022-12-30", 2));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Detalles detalles=new Detalles();
                Bundle args=new Bundle();
                args.putInt("position",i);
                detalles.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,detalles)
                        .commit();

            }
        });
        return view;
    }




}
