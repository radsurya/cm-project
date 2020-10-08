package com.example.firstapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ImageView;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {

    public static final String BACKGORUND_NAME = "background_color";
    String BACKGORUND_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    // Handle radio button
    public void onRadioButtonClicked(View view) {
        SharedPreferences settings = getSharedPreferences("colors", 0); // first argument is just a name of your SharedPreferences which you want to use. It's up to you how you will name it, but you have to use the same name later when you want to retrieve data.
        SharedPreferences.Editor editor = settings.edit();

        // Check which radio button was clicked and save the boolean value
        switch(view.getId()) {
            case R.id.color_blue:
                editor.putBoolean("color_blue", true); // first argument is a name of a data that you will later use to retrieve it and the second argument is a value that will be stored
                editor.putBoolean("color_green", false);
                editor.putBoolean("color_red", false);
                editor.putBoolean("color_yellow", false);
                break;
            case R.id.color_green:
                editor.putBoolean("color_blue", false);
                editor.putBoolean("color_green", true);
                editor.putBoolean("color_red", false);
                editor.putBoolean("color_yellow", false);
                break;
            case R.id.color_red:
                editor.putBoolean("color_blue", false);
                editor.putBoolean("color_green", false);
                editor.putBoolean("color_red", true);
                editor.putBoolean("color_yellow", false);
                break;
            case R.id.color_yellow:
                editor.putBoolean("color_blue", false);
                editor.putBoolean("color_green", false);
                editor.putBoolean("color_red", false);
                editor.putBoolean("color_yellow", true);
                break;
        }

        editor.commit(); // Commit the changes
    }

    // Save selected color and go to main page
    public void saveSettings(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        // Get radio button values
        SharedPreferences settings = getSharedPreferences("colors", 0);
        boolean color_blue = settings.getBoolean("color_blue", false); // The second argument is a default value, if value with name "questionA" will not be found
        boolean color_green = settings.getBoolean("color_green", false);
        boolean color_red = settings.getBoolean("color_red", false);
        boolean color_yellow = settings.getBoolean("color_yellow", false);

        // Set selected color
        if (color_blue) { BACKGORUND_COLOR = "blue"; }
        if (color_green) { BACKGORUND_COLOR = "green"; }
        if (color_red) { BACKGORUND_COLOR = "red"; }
        if (color_yellow) { BACKGORUND_COLOR = "yellow"; }

        intent.putExtra(BACKGORUND_NAME, BACKGORUND_COLOR);
        startActivity(intent);
    }
}