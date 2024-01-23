package com.example.miapppablorodriguezpenhia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetallesLugar extends Fragment {

    TextView tNombre,tTipo,tDireccion,tUrl,tTfno,tFecha,tHora;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalles_lugar, container, false);


        tNombre=view.findViewById(R.id.textViewNombre);
        tTipo=view.findViewById(R.id.textViewTipo);
        tDireccion=view.findViewById(R.id.textViewUbicacion);
        tTfno=view.findViewById(R.id.textViewTfno);
        tUrl=view.findViewById(R.id.textViewUrl);
        tFecha=view.findViewById(R.id.textViewFecha);
        tHora=view.findViewById(R.id.textViewHora);


        // Obtener el Bundle de argumentos
        //REPASAR
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Obtener el objeto Lugar del Bundle
            Lugar lugarSeleccionado = (Lugar) bundle.getSerializable("lugarSeleccionado");
            // Llamar al método para actualizar la vista con los datos del lugar
            obtenerDatosListView(lugarSeleccionado);
        }

        return view;
    }

    public void obtenerDatosListView(Lugar l){
        tNombre.setText(l.getNombre());
        tTipo.setText(l.getTipo().toString());
        tDireccion.setText(l.getDireccion());
        tTfno.setText(l.getTfno());
        tUrl.setText(l.getUrl());
        tFecha.setText(l.getFecha());
        tHora.setText(l.getHora());
    }
}
