package com.example.imagenes3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

public class Img extends View {

    private Drawable imagen;
    private Paint paint;
    private Rect rect;

    public Img(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Obtener la imagen del recurso drawable
        int indiceImagen = R.drawable.osvaldo; // Reemplaza "tu_imagen" con el nombre de tu imagen en res/drawable
        imagen = ContextCompat.getDrawable(context, indiceImagen);

        // Configurar Paint para el texto
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(20); // Tamaño del texto

        // Crear un rectángulo para el área de dibujo de la imagen
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Obtener las dimensiones intrínsecas de la imagen
        int anchuraImagen = imagen.getIntrinsicWidth();
        int alturaImagen = imagen.getIntrinsicHeight();

        // Obtener las dimensiones del View
        int anchuraView = getWidth();
        int alturaView = getHeight();

        // Calcular la escala necesaria para ajustar la imagen al View
        float escalaX = (float) anchuraView / anchuraImagen;
        float escalaY = (float) alturaView / alturaImagen;

        // Calcular la escala mínima para asegurarse de que la imagen se ajuste completamente al View
        float escala = Math.min(escalaX, escalaY);

        // Calcular las nuevas dimensiones de la imagen
        int nuevaAnchura = (int) (anchuraImagen * escala);
        int nuevaAltura = (int) (alturaImagen * escala);

        // Establecer el área de dibujo de la imagen
        rect.set(0, 0, nuevaAnchura, nuevaAltura);

        // Dibujar un rectángulo de fondo en color rojo
        canvas.drawColor(Color.RED);

        // Dibujar la imagen en el canvas
        imagen.setBounds(rect);
        imagen.draw(canvas);

        // Mostrar las dimensiones y la densidad en rojo
        String dimensiones = "Anchura: " + nuevaAnchura + ", Altura: " + nuevaAltura + ", Densidad: " + getResources().getDisplayMetrics().density;
        canvas.drawText(dimensiones, 10, getHeight() - 10, paint);
    }
}
