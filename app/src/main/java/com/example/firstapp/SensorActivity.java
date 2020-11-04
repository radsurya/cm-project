package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;
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

        /* Handle accelerometer sensor on device */
        xTextView = findViewById(R.id.teste1);
        yTextView = findViewById(R.id.teste2);
        zTextView = findViewById(R.id.teste3);

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
        /* End - Handle accelerometer sensor on device */
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
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

                if (xTextView != null && yTextView != null  && zTextView != null ) {
                    xTextView.setTextColor(Color.RED);
                    yTextView.setTextColor(Color.RED);
                    zTextView.setTextColor(Color.RED);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
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
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isAccelerometerSensorAvailable) {
            mSensorManager.unregisterListener(this);
        }
    }
}