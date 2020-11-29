package com.example.firstapp;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class GestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnDoubleTapListener {
    private PaintCanvas canvas;

    void setCanvas(PaintCanvas canvas) {
        this.canvas = canvas;
    }

    ////////SimpleOnGestureListener
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        canvas.changeBackground();
    }

    /////////OnDoubleTapListener
    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        canvas.erase();
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        System.out.println("----------onSingleTapUp----");
        return false;
    }

    public void loadPainting(List<List<PaintData>> loadPaintData) {
        canvas.loadPainting(loadPaintData);
    }
}
