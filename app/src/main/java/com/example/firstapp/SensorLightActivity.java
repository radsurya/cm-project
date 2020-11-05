package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SensorLightActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private float mLightQuantity;
    //Variable to store brightness value
    private float brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;
    private TextView view, view2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_light);
        view = findViewById(R.id.teste_sensor_light);
        view2 = findViewById(R.id.teste_sensor_light2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {

                // Obtain references to the SensorManager and the Light Sensor
                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

                // Implement a listener to receive updates
                SensorEventListener listener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        if (view != null) {
                            view.setText("Light: " + event.values[0]);
                        }
                        mLightQuantity = event.values[0];
                        getBrightness(mLightQuantity);

                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                };

                // Register the listener with the light sensor -- choosing
                // one of the SensorManager.SENSOR_DELAY_* constants.
                mSensorManager.registerListener(
                        listener, mLightSensor, SensorManager.SENSOR_DELAY_UI);

                //Get the content resolver
                cResolver = getContentResolver();

                //Get the current window
                window = getWindow();

/*
            try
            {
                // To handle the auto
                Settings.System.putInt(cResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                //Get the current system brightness
                brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);

                if (view2 != null) {
                    view2.setText("Brightness: " + brightness);
                }
            }
            catch (Settings.SettingNotFoundException e)
            {
                //Throw an error case it couldn't be retrieved
                Log.e("Error", "Cannot access system brightness");
                e.printStackTrace();
            }

            //Set the system brightness using the brightness variable value
            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
            //Get the current window attributes
            WindowManager.LayoutParams layoutpars = window.getAttributes();
            //Set the brightness of this window
            layoutpars.screenBrightness = brightness / (float)255;
            //Apply attribute changes to this window
            window.setAttributes(layoutpars);

            }
            else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } */

            }
        }
    }

    public float getBrightness(float light) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.System.canWrite(this)) {
                // Enable write permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                this.startActivity(intent);
            } else {
                // Get system brightness
                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC); // enable auto brightness
                brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, -1);  // in the range [0, 255]

                brightness = light * 255 / 40000;
                //Set the system brightness using the brightness variable value
                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int)brightness);
                //Get the current window attributes
                WindowManager.LayoutParams layoutpars = window.getAttributes();
                //Set the brightness of this window
                // layoutpars.screenBrightness = brightness / (float)255;
                layoutpars.screenBrightness = light * 255 / 40000;
                //Apply attribute changes to this window
                window.setAttributes(layoutpars);

                if (view2 != null) {
                    view2.setText("Brightness: " + brightness);
                }

            }
        }

        return brightness;
    }


}