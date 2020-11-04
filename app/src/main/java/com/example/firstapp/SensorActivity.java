package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    // The following are used for the shake detection
    private TextView xTextView, yTextView, zTextView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean isAccelerometerSensorAvailable, itIsNotFirstTime = false;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ;
    private float xDifference, yDifference, zDifference;
    private float shakeThreshold = 5f;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        /* Get a listing of every sensor on device */
        xTextView = findViewById(R.id.teste1);
        yTextView = findViewById(R.id.teste2);
        zTextView = findViewById(R.id.teste3);
        if (xTextView != null) {
            xTextView.setText("Olá");
        }


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
            if (deviceSensors != null) {
                for(Sensor s : deviceSensors) {
                    // System.out.println("ALL TYPES OF SENSORES");

                    // System.out.println(s.getName());
                }
            }

            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if (mAccelerometer != null) {
                System.out.println("ACELARADOR");
                System.out.println(mAccelerometer.getName());
                isAccelerometerSensorAvailable = true;
            } else {
                xTextView.setText("Accelerometer is not available");
                isAccelerometerSensorAvailable = false;
            }

        }
        /* End - Get a listing of every sensor on device */
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println("----- X Value");
        System.out.println(event.values[0]);
        System.out.println("----- Y Value");
        System.out.println(event.values[1]);
        System.out.println("----- Z Value");
        System.out.println(event.values[2]);

        if (xTextView != null && yTextView != null  && zTextView != null ) {
            xTextView.setText(event.values[0] + " m/s2");
            yTextView.setText(event.values[1] + " m/s2");
            zTextView.setText(event.values[2] + " m/s2");
        }

        currentX = event.values[0];
        currentY = event.values[1];
        currentZ = event.values[2];

        if (itIsNotFirstTime) {
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if ((xDifference > shakeThreshold && yDifference > shakeThreshold)
                    || (xDifference > shakeThreshold && zDifference > shakeThreshold)
                    || (yDifference > shakeThreshold && zDifference > shakeThreshold)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    if (xTextView != null && yTextView != null  && zTextView != null ) {
                        xTextView.setTextColor(Color.RED);
                        yTextView.setTextColor(Color.RED);
                        zTextView.setTextColor(Color.RED);
                    }
                } else {
                    vibrator.vibrate(500);
                }
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        itIsNotFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAccelerometerSensorAvailable) {
            System.out.println("Sensor in onResume:");
            System.out.println(mSensorManager);
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isAccelerometerSensorAvailable) {
            System.out.println("Sensor in on onPause:");
            System.out.println(mSensorManager);
            mSensorManager.unregisterListener(this);
        }
    }
}