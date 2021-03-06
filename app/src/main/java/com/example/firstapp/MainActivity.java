package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String color = intent.getStringExtra(SettingsActivity.BACKGORUND_NAME);
        View mainAppView = findViewById(R.id.main_app);

        // Change background color chosen in Settings screen
        if (color != null) {
            switch (color) {
                case "blue":
                    mainAppView.setBackgroundColor(Color.BLUE);
                    break;
                case "green":
                    mainAppView.setBackgroundColor(Color.GREEN);
                    break;
                case "red":
                    mainAppView.setBackgroundColor(Color.RED);
                    break;
                case "yellow":
                    mainAppView.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    mainAppView.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
    }

    /** Called when the user taps the start button */
    public void goToPage(View view) {
        Intent intent = new Intent(this, PaintingActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the sensor button */
    public void goToSensorPage(View view) {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Sensor Light button */
    public void goToPageSensorLight(View view) {
        Intent intent = new Intent(this, SensorLightActivity.class);
        startActivity(intent);
    }

    /**
     * Create a menu options on top of the main screen
     * @param menu - Current menu
     * @return always true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    /**
     * Handle menu items
     * @param item - Current item menu
     * @return always true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_item2:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.menu_item3:
                Intent intent3 = new Intent(this, MapsActivity.class);
                startActivity(intent3);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}