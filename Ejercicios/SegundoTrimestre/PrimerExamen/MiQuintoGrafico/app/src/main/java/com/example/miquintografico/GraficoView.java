package com.example.miquintografico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraficoView extends View {

    public GraficoView(Context context) {
        super(context);
    }

    public GraficoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraficoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.RED);

        Paint squarePaint = new Paint();
        squarePaint.setStyle(Paint.Style.STROKE);
        squarePaint.setStrokeWidth(5);
        squarePaint.setColor(Color.BLUE);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int numCircles = 5;
        int numSquares = 5;

        float circleSpacing = width / (numCircles + 1);
        float squareSpacing = height / (numSquares + 1);

        // Dibujar círculos
        for (int i = 1; i <= numCircles; i++) {
            float x = i * circleSpacing;
            float y = height / 4; // Altura fija para los círculos
            canvas.drawCircle(x, y, 50, circlePaint);
        }

        // Dibujar cuadrados
        for (int i = 1; i <= numSquares; i++) {
            float x = width / 4; // Ancho fijo para los cuadrados
            float y = i * squareSpacing;
            canvas.drawRect(x - 50, y - 50, x + 50, y + 50, squarePaint);
        }
    }
}
