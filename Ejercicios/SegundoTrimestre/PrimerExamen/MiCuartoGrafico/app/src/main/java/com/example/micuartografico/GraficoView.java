package com.example.micuartografico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.YELLOW);
        canvas.drawPaint(backgroundPaint);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int right = getWidth();
        int bottom = getHeight();

        float s = getResources().getDisplayMetrics().scaledDensity;

        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(2 * s);
        linePaint.setColor(Color.BLUE);

        canvas.drawLine(0, 0, right, bottom, linePaint);
        canvas.drawLine(0, bottom, right, 0, linePaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20 * s);
        textPaint.setAntiAlias(true);

        canvas.drawText("width: " + width + " height: " + height, 20 * s, 40 * s, textPaint);
        canvas.drawText("Escala: " + s, 20 * s, 65 * s, textPaint);
        canvas.drawText("right: " + right + " bottom: " + bottom, 20 * s, 90 * s, textPaint);
    }
}
