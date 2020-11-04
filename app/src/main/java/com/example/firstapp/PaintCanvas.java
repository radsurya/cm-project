package com.example.firstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import java.util.Random;

public class PaintCanvas extends View implements View.OnTouchListener {
    private Paint paint = new Paint();
    private Path path = new Path();
    private int backGroundColor = Color.WHITE;
    private String penColor;
    private GestureDetector mGestureDetector;

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        paint.setColor(Color.BLACK);
        initPaint();
    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector, String penColor) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        this.penColor = penColor;
        setOnTouchListener(this);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);// draws the path with the paint
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);// updates the path initial point
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public void changeBackground(){
        Random r = new Random();
        backGroundColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        setBackgroundColor(backGroundColor);
    }

    public void erase(){
        paint.setColor(Color.WHITE);
        setBackgroundColor(Color.WHITE);

        // paint.setColor(backGroundColor);
    }

    private void initPaint(){
        setBackgroundColor(backGroundColor);
        setPenColor(penColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * Set pen color by given string of color
     * @param penColorStr
     */
    public void setPenColor(String penColorStr) {
        penColor = penColorStr;
        if (penColorStr != null && penColorStr != "") {
            switch (penColorStr) {
                case "black":
                    paint.setColor(Color.BLACK);
                    break;
                case "red":
                    paint.setColor(Color.RED);
                    break;
                case "green":
                    paint.setColor(Color.GREEN);
                    break;
                case "blue":
                    paint.setColor(Color.BLUE);
                    break;
                default:
                    paint.setColor(Color.BLACK);
                    break;
            }
        } else {
            paint.setColor(Color.BLACK);
        }
    }

}
