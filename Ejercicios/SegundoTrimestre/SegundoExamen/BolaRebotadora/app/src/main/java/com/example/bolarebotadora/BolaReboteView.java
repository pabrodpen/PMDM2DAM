package com.example.bolarebotadora;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class BolaReboteView extends View {
    private BolaRebote bolaRebote;
    private Paint paint;

    public BolaReboteView(Context context, BolaRebote bolaRebote) {
        super(context);
        this.bolaRebote = bolaRebote;
        paint = new Paint();
        paint.setColor(Color.RED); // Color de la bola
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Dibuja la bola en la posición actual
        canvas.drawCircle(bolaRebote.getXPos() * getWidth(), bolaRebote.getYPos() * getHeight(), 30, paint);
    }
}
