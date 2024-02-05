package com.example.miprimergraficointeractivo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Grafico extends View {

    private float x = 50; // Coordenada x inicial
    private float y = 50; // Coordenada y inicial
    private float radio = 50; // Radio del círculo
    private Paint paint;


    public Grafico(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, radio, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Código correspondiente a ACTION_DOWN
                break;

            case MotionEvent.ACTION_UP:
                // Código correspondiente a ACTION_UP
                break;

            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                break;
        }

        invalidate(); // Vuelve a dibujar la pantalla
        return true;
    }
}

