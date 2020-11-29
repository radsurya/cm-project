package com.example.firstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.Path.Op.INTERSECT;

public class PaintCanvas extends View implements View.OnTouchListener {
    private Paint paint = new Paint();
    private Path path = new Path();
    private int backGroundColor = Color.WHITE;
    private String penColor;
    private GestureDetector mGestureDetector;
    private float initialX, initialY;
    private List<List> paintDataList= new ArrayList<List>();
    private List<PaintData> paintDataLine = new ArrayList<PaintData>();


    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        setPenColor(null);
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
        setPenColor(null);
        mGestureDetector.onTouchEvent(event);
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("------------ onTouchEvent");
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = xPos;
                initialY = yPos;
                path.moveTo(xPos, yPos); // updates the path initial point
                return true;
            case MotionEvent.ACTION_MOVE:
                /* Save canvas data movement */
                PaintData paintData = new PaintData();
                paintData.setInitialX(initialX);
                paintData.setInitialY(initialY);
                paintData.setFinalX(xPos);
                paintData.setFinalY(yPos);
                paintData.setBackgroundColor(backGroundColor);
                paintData.setPenColor(penColor);
                paintDataLine.add(paintData);
                /* End - Save canvas data movement */

                path.lineTo(xPos, yPos);// makes a line to the point each time this event is fired
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();

                paintDataList.add(paintDataLine);

                /* Save canvas data to shared preferences */
                SharedPreferences mPrefs = getContext().getSharedPreferences("paintDataList", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(paintDataList);
                prefsEditor.putString("data", json);
                prefsEditor.commit();
                /* End - Save canvas data to shared preferences */

                // Empty list
                paintDataLine.clear();
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
        setPenColor(null);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * Set pen color
     */
    public void setPenColor(String penColorAux) {
        /* Get clicked color from pallet */
        SharedPreferences settings = getContext().getSharedPreferences("penColors", 0);
        penColor = penColorAux != null ? penColorAux : settings.getString("penColor", null);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loadPainting(List<List<PaintData>> loadPaintData) {
        System.out.println("---------- loadPaintData Func: " + loadPaintData);
        if (loadPaintData != null) {
            Canvas canvas = new Canvas();

            for (int i = 0; i < loadPaintData.size(); i++) {
                List<PaintData> currentLine = loadPaintData.get(i);
                path = new Path();
                for (int j = 0; j < currentLine.size(); j++) {
                    PaintData currentPaint = currentLine.get(j);
                    setPenColor(currentPaint.getPenColor());

                    if (j == 0) {
                        path.moveTo(currentPaint.getInitialX(), currentPaint.getInitialY());
                    } else {
                        path.lineTo(currentPaint.getFinalX(), currentPaint.getFinalY());
                    }

                }
                canvas.drawPath(path, paint);
                // path.close();
                // Schedules a repaint.
                invalidate();

            }
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
