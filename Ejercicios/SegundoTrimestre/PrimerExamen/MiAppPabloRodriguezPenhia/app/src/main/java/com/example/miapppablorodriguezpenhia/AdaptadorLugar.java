package com.example.miapppablorodriguezpenhia;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorLugar extends ArrayAdapter<Lugar> {

    private Context context;
    private ArrayList<Lugar> lugares;

    public AdaptadorLugar(Context context, ArrayList<Lugar> lugares) {
        super(context, 0, lugares);
        this.context = context;
        this.lugares = lugares;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Lugar lugarActual = lugares.get(position);

        // Creamos un contenedor horizontal para la imagen y el texto
        LinearLayout horizontalLayout = new LinearLayout(context);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Creamos un contenedor vertical para el texto
        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setGravity(Gravity.CENTER_VERTICAL);

        // Configuramos la imagen
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(
                350, // Ancho
                300  // Alto
        );
        imageView.setLayoutParams(paramsImage);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // Asegura que la imagen se recorta y escala

        if (lugarActual.getRutaFoto() != null && !lugarActual.getRutaFoto().isEmpty()) {
            Glide.with(context)
                    .load(lugarActual.getRutaFoto())
                    .centerCrop()
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.baseline_photo_24); // Imagen por defecto
        }

        // Configuramos el nombre del lugar
        TextView textViewNombre = new TextView(context);
        textViewNombre.setText(lugarActual.getNombre());
        textViewNombre.setTextSize(24); // Tamaño de texto más grande
        textViewNombre.setGravity(Gravity.CENTER); // Centrar el texto

        // Configuramos la dirección del lugar
        TextView textViewDireccion = new TextView(context);
        textViewDireccion.setText(lugarActual.getDireccion());
        textViewDireccion.setTextSize(16); // Tamaño de texto más grande
        textViewDireccion.setGravity(Gravity.CENTER); // Centrar el texto

        // Configuramos la valoración del lugar dentro de un contenedor horizontal
        LinearLayout ratingBarLayout = new LinearLayout(context);
        ratingBarLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ratingBarLayout.setOrientation(LinearLayout.HORIZONTAL);

        RatingBar ratingBar = new RatingBar(context);
        LinearLayout.LayoutParams paramsRatingBar = new LinearLayout.LayoutParams(
              700,200
        );
        ratingBar.setLayoutParams(paramsRatingBar);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1.0f);
        ratingBar.setRating(lugarActual.getValoracion());
        ratingBar.setFocusable(false);
        ratingBar.setClickable(false);

        // Ajustamos el tamaño de las estrellas
        ratingBar.setScaleX(0.7f); // Ajusta el tamaño en el eje X (ancho)
        ratingBar.setScaleY(0.7f); // Ajusta el tamaño en el eje Y (alto)

        // Añadimos la RatingBar al contenedor horizontal
        ratingBarLayout.addView(ratingBar);

        // Añadimos los textos al contenedor de texto
        textLayout.addView(textViewNombre);
        textLayout.addView(textViewDireccion);
        textLayout.addView(ratingBarLayout);

        // Añadimos la imagen y el contenedor de texto al contenedor horizontal
        horizontalLayout.addView(imageView);
        horizontalLayout.addView(textLayout);

        return horizontalLayout;
    }
}
