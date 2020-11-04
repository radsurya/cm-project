package com.example.firstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

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
        setPenColor();
        setBackgroudBySensor();
        initPaint();
    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        setOnTouchListener(this);
        setBackgroudBySensor();
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("------------ onDraw");
        canvas.drawPath(path, paint);// draws the path with the paint
    }

    @Override
    public boolean performClick(){
        System.out.println("------------ performClick");
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("------------ onTouch");
        setPenColor();
        mGestureDetector.onTouchEvent(event);
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("------------ onTouchEvent");
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
        paint.setColor(backGroundColor);
    }

    private void initPaint(){
        setBackgroundColor(backGroundColor);
        setPenColor();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * Set pen color
     */
    public void setPenColor() {
        /* Get clicked color from pallet */
        SharedPreferences settings = getContext().getSharedPreferences("penColors", 0);
        penColor = settings.getString("penColor", null);
        /* End - Get clicked color from pallet */

        if (penColor != null && penColor != "") {
            switch (penColor) {
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

    /**
     * Set random background color of canvas from accelerometer sensor
     */
    public void setBackgroudBySensor() {
        /* Get sensor warning to change background from shared preferences */
        // SharedPreferences settings = getContext().getSharedPreferences("setBackgroundBySensor", 0);
        // String warning = settings.getString("setBackgroundBySensorTrue", null);
        /* End - Get sensor warning to change background from shared preferences */
/*
        if(warning !=null && warning.equals("true")) {
            changeBackground();
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("setBackgroundBySensorTrue", null);
            editor.commit();
        }*/
    }

}
