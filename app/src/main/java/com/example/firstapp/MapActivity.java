package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    /** Called when the user taps the Sensor Light button */
    public void changeBtn(View view) {
        Button mapBtn = findViewById(R.id.map_drawing_btn);
        if (mapBtn != null) {
            String btnText = (String)mapBtn.getText();
            if (btnText.equals("Start Drawing")) {
                mapBtn.setText("Stop Drawing");
            } else {
                mapBtn.setText("Start Drawing");
            }
        }
    }
}