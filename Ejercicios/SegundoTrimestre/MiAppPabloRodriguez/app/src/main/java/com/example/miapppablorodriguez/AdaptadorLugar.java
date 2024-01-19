package com.example.miapppablorodriguez;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

public class AdaptadorLugar extends ArrayAdapter<Lugar> {
    private final LayoutInflater inflater;
    private Lugar lugar;
    public AdaptadorLugar(@NonNull Context context, List<Lugar> lugares) {
        super(context, 0,lugares);
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){
            itemView=inflater.inflate(R.layout.lugar,parent,false);
        }
        Lugar lugar=getItem(position);


        ImageView imagen = itemView.findViewById(R.id.image);
        TextView nombre = itemView.findViewById(R.id.textViewNombre);
        TextView direccion = itemView.findViewById(R.id.textViewDireccion);

        if (lugar != null) {
            imagen.setImageResource(lugar.getFotoId());
            nombre.setText(lugar.getNombre());
            direccion.setText(String.valueOf(elemento.getPrecio()));
        }
//nombre,direcc,valoracion,img
        return itemView;
    }


}
