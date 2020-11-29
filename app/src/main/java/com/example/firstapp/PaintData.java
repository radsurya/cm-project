package com.example.firstapp;

public class PaintData {
    private float initialX;
    private float initialY;
    private float finalX;
    private float finalY;
    private int backgroundColor;
    private String penColor;

    public PaintData() {
    }

    public float getInitialX() {
        return initialX;
    }

    public void setInitialX(float initialX) {
        this.initialX = initialX;
    }

    public float getInitialY() {
        return initialY;
    }

    public void setInitialY(float initialY) {
        this.initialY = initialY;
    }

    public float getFinalX() {
        return finalX;
    }

    public void setFinalX(float finalX) {
        this.finalX = finalX;
    }

    public float getFinalY() {
        return finalY;
    }

    public void setFinalY(float finalY) {
        this.finalY = finalY;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getPenColor() {
        return penColor;
    }

    public void setPenColor(String penColor) {
        this.penColor = penColor;
    }
}
