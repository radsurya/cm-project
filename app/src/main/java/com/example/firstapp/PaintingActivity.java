package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class PaintingActivity extends AppCompatActivity implements SensorEventListener {

    // The following are used for the shake detection
    private TextView xTextView, yTextView, zTextView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean isAccelerometerSensorAvailable, itIsNotFirstTime = false;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ;
    private float xDifference, yDifference, zDifference;
    private float shakeThreshold = 5f;
    private Vibrator vibrator;
    private String toChangeCanvasBackground = "0";

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);

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
                isAccelerometerSensorAvailable = false;
            }

        }
        /* End - Handle accelerometer sensor on device */


        /* Handle Fragments */
        View mainFragment = findViewById(R.id.main_fragment);
        View mainFragment1 = findViewById(R.id.fragment_first_land);
        View mainFragment2 = findViewById(R.id.fragment_second_land);

        // Set the first fragment by default
        if (mainFragment != null) {
            FirstFragment firstFragment = new FirstFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, firstFragment).commit();
        }

        // Landscape view
        if (mainFragment1 != null) {
            FirstFragment firstFragmentLand = new FirstFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first_land, firstFragmentLand).commit();
        }

        if (mainFragment2 != null) {
            SecondFragment secondFragmentLand = new SecondFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second_land, secondFragmentLand).commit();
        }

        // Set the first fragment by clicking in canvas button
        final Button buttonF1 = (Button) findViewById(R.id.button_fragment1);
        if (buttonF1 != null) {
            buttonF1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FirstFragment firstFragment = new FirstFragment();
                    View mainFragment = findViewById(R.id.main_fragment);
                    View fragment2 = findViewById(R.id.fragment_second);

                    if (mainFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, firstFragment).commit();
                    } else if(fragment2 != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_second, firstFragment).commit();
                    }
                }
            });
        }

        // Set the first fragment by clicking in pallet button
        final Button buttonF2 = (Button) findViewById(R.id.button_fragment2);
        if (buttonF2 != null) {
            buttonF2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SecondFragment secondFragment = new SecondFragment();
                    View mainFragment = findViewById(R.id.main_fragment);
                    View fragment1 = findViewById(R.id.fragment_first);

                    if (mainFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, secondFragment).commit();
                    } else if (fragment1 != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_first, secondFragment).commit();
                    }
                }
            });
        }
        /* End - Handle Fragments */

        // Save paint data
        final Button buttonSavePaint = (Button) findViewById(R.id.button_save_paint);
        if (buttonSavePaint != null) {
            buttonSavePaint.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /* Get canvas from shared preferences */
                    SharedPreferences mPrefs = getSharedPreferences("paintDataList", MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = mPrefs.getString("data", "");
                    Type type = new TypeToken<List<List>>() {}.getType();
                    List<List> paintDataList = new Gson().fromJson(json, type);
                    /* End - Get canvas from shared preferences */

                    /* Sava canvas to Firebase database */
                    reff = FirebaseDatabase.getInstance().getReference();
                    reff.child("paintData").setValue(paintDataList);
                    /* End - Sava canvas to Firebase database */
                }
            });
        }
        /* End - Save paint data */
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
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


                // TODO Save at shared preferences, the use it in paint canvas and refresh it there

                // Set canvas background color by sensor
                SharedPreferences settings = this.getSharedPreferences("setBackgroundBySensor", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("setBackgroundBySensorTrue", "true");
                editor.commit();

                Toast.makeText(this, "Mobile is vibrating", Toast.LENGTH_SHORT).show();

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