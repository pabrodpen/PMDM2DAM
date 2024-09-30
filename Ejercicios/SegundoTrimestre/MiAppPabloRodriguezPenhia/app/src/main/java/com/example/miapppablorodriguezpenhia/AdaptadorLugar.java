package com.example.miapppablorodriguezpenhia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.miapppablorodriguezpenhia.ListLugares;
import com.example.miapppablorodriguezpenhia.Lugar;
import com.example.miapppablorodriguezpenhia.R;

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
        Log.d("Adaptador", "getView llamado para la posición: " + position);

        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.lugar, parent, false);
        }

        Lugar lugar = getItem(position);

        TextView textViewNombre = itemView.findViewById(R.id.textViewNombre);
        TextView textViewDireccion = itemView.findViewById(R.id.textViewDireccion);
        ImageView imageView = itemView.findViewById(R.id.image);
        RatingBar ratingBar = itemView.findViewById(R.id.ratingBar); // Agregado

        // Configurar los valores de los elementos
        textViewNombre.setText(lugar.getNombre());
        textViewDireccion.setText(lugar.getDireccion());

        // Configurar la imagen desde la ruta
        if (lugar.getRutaFoto() != null && !lugar.getRutaFoto().isEmpty()) {
            Glide.with(getContext())
                    .load(lugar.getRutaFoto())
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.baseline_photo_24);
        }

        // Configurar la calificación y agregar el listener
        if (lugar != null) {
            ratingBar.setRating(lugar.getValoracion());
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        ListLugares.actualizarValoracionLugar(lugar.getNombre(), rating);
                    }
                }
            });
        }

        // Agregar un OnClickListener para abrir la actividad DetallesLugar
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetallesLugar.class);
                intent.putExtra("nombre", lugar.getNombre());
                intent.putExtra("direccion", lugar.getDireccion());
                intent.putExtra("tipo", lugar.getTipo()); // Asegúrate de que el objeto Lugar tiene este método
                intent.putExtra("fecha", lugar.getFecha()); // Asegúrate de que el objeto Lugar tiene este método
                intent.putExtra("url", lugar.getUrl()); // Asegúrate de que el objeto Lugar tiene este método
                intent.putExtra("tfno", lugar.getTfno()); // Asegúrate de que el objeto Lugar tiene este método
                intent.putExtra("valoracion", lugar.getValoracion());
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }

}
