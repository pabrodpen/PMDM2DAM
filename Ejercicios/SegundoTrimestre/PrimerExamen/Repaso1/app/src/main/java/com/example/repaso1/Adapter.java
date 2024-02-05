package com.example.repaso1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Tarea> {

    private final LayoutInflater inflater;
    public Adapter(@NonNull Context context, ArrayList<Tarea> tareas) {
        super(context, 0,tareas);
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemview=convertView;

        if(itemview==null){
            itemview=inflater.inflate((R.layout.tarea),parent,false);
        }

        Tarea tarea=getItem(position);

        TextView tTitulo,tDescripcion,tFecha,tPrioridad;

        tTitulo=itemview.findViewById(R.id.text_view_titulo);
        tDescripcion=itemview.findViewById(R.id.text_view_descripcion);
        tFecha=itemview.findViewById(R.id.text_view_prioridad);
        tPrioridad=itemview.findViewById(R.id.text_view_prioridad);

        tTitulo.setText(tarea.getTitulo());
        tDescripcion.setText(tarea.getDescripcion());
        tFecha.setText(tarea.getFechaVencimiento());
        tPrioridad.setText(tarea.getPrioridad());

        return itemview;
    }
}
