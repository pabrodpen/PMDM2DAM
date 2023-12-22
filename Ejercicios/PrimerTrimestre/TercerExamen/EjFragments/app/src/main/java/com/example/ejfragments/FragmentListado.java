package com.example.ejfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentListado extends Fragment {

    private RecyclerView lstListado;
    private List<Correo> datos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstListado = getView().findViewById(R.id.lstListado);
        datos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datos.add(new Correo("Persona " + i, "Asunto del correo " + i, "Texto del correo " + i));
        }

        AdaptadorCorreos adaptador = new AdaptadorCorreos(datos, new AdaptadorCorreos.OnItemClickListener() {
            @Override
            public void onItemClick(Correo correo) {
                // Acciones al hacer clic en un elemento
            }
        });

        lstListado.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        lstListado.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        lstListado.setAdapter(adaptador);
    }
}
