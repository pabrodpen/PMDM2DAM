package com.example.miquintografico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GraficoView extends View {

    // Definir las coordenadas y el radio de los círculos
    private float circle1X = 200;
    private float circle1Y = 200;
    private float circle2X = 400;
    private float circle2Y = 400;
    private float circleRadius = 100;

    // Definir las coordenadas y el tamaño de los cuadrados
    private float square1X = 100;
    private float square1Y = 300;
    private float square2X = 300;
    private float square2Y = 100;
    private float squareSize = 200;

    // Paints para los círculos y cuadrados
    private Paint circlePaint;
    private Paint squarePaint;

    // Variables para detectar el toque
    private boolean draggingCircle1 = false;
    private boolean draggingCircle2 = false;
    private boolean draggingSquare1 = false;
    private boolean draggingSquare2 = false;

    public GraficoView(Context context) {
        super(context);
        init();
    }

    public GraficoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraficoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // Inicializar Paints y otros objetos
    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);

        squarePaint = new Paint();
        squarePaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar círculos
        canvas.drawCircle(circle1X, circle1Y, circleRadius, circlePaint);
        canvas.drawCircle(circle2X, circle2Y, circleRadius, circlePaint);

        // Dibujar cuadrados
        canvas.drawRect(square1X, square1Y, square1X + squareSize, square1Y + squareSize, squarePaint);
        canvas.drawRect(square2X, square2Y, square2X + squareSize, square2Y + squareSize, squarePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Verificar si se está tocando algún objeto
                if (isInsideCircle(touchX, touchY, circle1X, circle1Y, circleRadius)) {
                    draggingCircle1 = true;
                } else if (isInsideCircle(touchX, touchY, circle2X, circle2Y, circleRadius)) {
                    draggingCircle2 = true;
                } else if (isInsideSquare(touchX, touchY, square1X, square1Y, squareSize)) {
                    draggingSquare1 = true;
                } else if (isInsideSquare(touchX, touchY, square2X, square2Y, squareSize)) {
                    draggingSquare2 = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // Mover el objeto que se está arrastrando
                if (draggingCircle1) {
                    circle1X = touchX;
                    circle1Y = touchY;
                } else if (draggingCircle2) {
                    circle2X = touchX;
                    circle2Y = touchY;
                } else if (draggingSquare1) {
                    square1X = touchX - squareSize / 2;
                    square1Y = touchY - squareSize / 2;
                } else if (draggingSquare2) {
                    square2X = touchX - squareSize / 2;
                    square2Y = touchY - squareSize / 2;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // Se levantó el dedo, detener el arrastre
                draggingCircle1 = false;
                draggingCircle2 = false;
                draggingSquare1 = false;
                draggingSquare2 = false;
                break;
        }
        return true;
    }

    // Método para verificar si el toque está dentro de un círculo
    private boolean isInsideCircle(float touchX, float touchY, float circleX, float circleY, float radius) {
        float dx = touchX - circleX;
        float dy = touchY - circleY;
        return dx * dx + dy * dy <= radius * radius;
    }

    // Método para verificar si el toque está dentro de un cuadrado
    private boolean isInsideSquare(float touchX, float touchY, float squareX, float squareY, float size) {
        return touchX >= squareX && touchX <= squareX + size && touchY >= squareY && touchY <= squareY + size;
    }
}
