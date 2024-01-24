package com.example.miapppablorodriguez;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdaptadorLugar extends ArrayAdapter<Lugar> {
    private final LayoutInflater inflater;

    public AdaptadorLugar(@NonNull Context context, List<Lugar> lugares) {
        super(context, 0, lugares);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.lugar, parent, false);
        }

        Lugar lugar = getItem(position);

        TextView textViewNombre = itemView.findViewById(R.id.textViewNombre);
        TextView textViewDireccion = itemView.findViewById(R.id.textViewDireccion);
        ImageView imageView = itemView.findViewById(R.id.image);

        // Configurar los valores de los elementos
        textViewNombre.setText(lugar.getNombre());
        textViewDireccion.setText(lugar.getDireccion());

        // Configurar la imagen desde la ruta
        if (lugar.getRutaFoto() != null) {
            // Cargar la imagen desde la ruta
            Uri uri = Uri.parse(lugar.getRutaFoto());
            imageView.setImageURI(uri);
        } else {
            // Si no hay ruta de foto, mostrar una imagen de marcador de posición
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }

        return itemView;
    }
}

