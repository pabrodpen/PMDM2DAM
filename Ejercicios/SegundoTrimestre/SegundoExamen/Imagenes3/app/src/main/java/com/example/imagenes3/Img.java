package com.example.imagenes3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

public class Img extends View {

    private Drawable imagen;
    private Paint paint;

    public Img(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Obtener la imagen del recurso drawable
        int indiceImagen = R.drawable.sevilla; // Reemplaza "tu_imagen" con el nombre de tu imagen en res/drawable
        imagen = ContextCompat.getDrawable(context, indiceImagen);

        // Configurar Paint para el texto
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(20); // Tamaño del texto
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Obtener las dimensiones intrínsecas de la imagen
        int anchura = imagen.getIntrinsicWidth();
        int altura = imagen.getIntrinsicHeight();

        // Dibujar un rectángulo de fondo en color rojo
        canvas.drawColor(Color.RED);

        // Establecer las dimensiones de la imagen
        imagen.setBounds(0, 0, anchura, altura);

        // Dibujar la imagen en el canvas
        imagen.draw(canvas);

        // Mostrar las dimensiones y la densidad en rojo
        String dimensiones = "Anchura: " + anchura + ", Altura: " + altura + ", Densidad: " + getResources().getDisplayMetrics().density;
        canvas.drawText(dimensiones, 10, getHeight() - 10, paint);
    }
}
