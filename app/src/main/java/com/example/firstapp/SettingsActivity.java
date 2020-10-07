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

    View screenView;
    Button clickMe;
    int[] color;
    public static final String BACKGORUND_NAME = "background_color";
    String BACKGORUND_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    // Handle radio button
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        SharedPreferences settings = getSharedPreferences("colors", 0); // first argument is just a name of your SharedPreferences which you want to use. It's up to you how you will name it, but you have to use the same name later when you want to retrieve data.
        SharedPreferences.Editor editor = settings.edit();


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.color_red:
                editor.putBoolean("color_red", true); // first argument is a name of a data that you will later use to retrieve it and the second argument is a value that will be stored
                editor.putBoolean("color_blue", false);
                break;
            case R.id.color_blue:
                editor.putBoolean("color_blue", true);
                editor.putBoolean("color_red", false);
                break;
        }

        editor.commit(); // Commit the changes
    }

    // Save selected color and go to main page
    public void saveSettings(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences settings = getSharedPreferences("colors", 0);
        boolean color_red = settings.getBoolean("color_red", false); // The second argument is a default value, if value with name "questionA" will not be found
        boolean color_blue = settings.getBoolean("color_blue", false);
        BACKGORUND_COLOR = color_red ? "red" : (color_blue ? "blue" : "white");

        System.out.println("color_red");
        System.out.println(color_red);

        System.out.println("color_blue");
        System.out.println(color_blue);

        System.out.println("BACKGORUND_COLOR");
        System.out.println(BACKGORUND_COLOR);
        intent.putExtra(BACKGORUND_NAME, BACKGORUND_COLOR);
        startActivity(intent);
    }
}