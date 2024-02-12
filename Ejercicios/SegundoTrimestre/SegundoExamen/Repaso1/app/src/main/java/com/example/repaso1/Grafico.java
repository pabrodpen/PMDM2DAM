package com.example.repaso1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class Grafico extends View {

    Paint circulo;
    float x=100,y=100,xmax,ymax;

    public Grafico(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        circulo=new Paint();
        circulo.setColor(Color.GREEN);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if(x>=0 && x<=xmax && y>=0 && y<=ymax){
            canvas.drawCircle(x,y,70,circulo);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x=event.getX();
        y=event.getY();


        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                    x=event.getX();
                    y=event.getY();
        }
        invalidate();

        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        xmax=w/2;
        ymax=h/2;


    }
}
