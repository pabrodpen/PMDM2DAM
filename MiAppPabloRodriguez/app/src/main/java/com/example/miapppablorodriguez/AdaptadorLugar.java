package com.example.miapppablorodriguez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorLugar extends ArrayAdapter<Lugar> {

    public AdaptadorLugar(Context context, ArrayList<Lugar> lugares) {
        super(context, 0, lugares);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el lugar en la posición actual
        Lugar lugar = getItem(position);

        // Verificar si la vista actual está siendo reutilizada, de lo contrario, inflarla
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lugar, parent, false);
        }

        // Obtener referencias a los elementos de la vista
        TextView tvNombre = convertView.findViewById(R.id.textViewNombre);
        TextView tvDireccion = convertView.findViewById(R.id.textViewDireccion);
        ImageView imageView = convertView.findViewById(R.id.image);

        // Asignar los valores del lugar a los elementos de la vista
        tvNombre.setText(lugar.getNombre());
        tvDireccion.setText(lugar.getTipo().toString());

        // Cargar la imagen utilizando Glide
        String rutaFoto = lugar.getRutaFoto();
        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            Glide.with(getContext())
                    .load(rutaFoto)
                    .into(imageView);
        } else {
            // Si no hay una ruta de foto válida, puedes establecer una imagen predeterminada o dejarla vacía.
            imageView.setImageResource(R.drawable.baseline_photo_24); // Cambia esto según tus necesidades
        }

        return convertView;
    }
}
