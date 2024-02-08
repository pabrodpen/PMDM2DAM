package com.example.bolarebotadora;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.bolarebotadora.BolaRebote;

public class BolaReboteView extends View {
    private BolaRebote bolaRebote;
    private Paint paint;
    private float tamañoBola; // Tamaño de la bola

    public BolaReboteView(Context context, BolaRebote bolaRebote, float tamañoBola) {
        super(context);
        this.bolaRebote = bolaRebote;
        this.tamañoBola = tamañoBola; // Establecer el tamaño de la bola
        paint = new Paint();
        paint.setColor(Color.RED); // Color de la bola
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Dibuja la bola en la posición actual con el tamaño especificado
        canvas.drawCircle(bolaRebote.getXPos() * getWidth(), bolaRebote.getYPos() * getHeight(), tamañoBola, paint);
    }
}
