package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String color = intent.getStringExtra(SettingsActivity.BACKGORUND_NAME);
        View mainAppView = findViewById(R.id.main_app);
        View mainAppTextView = findViewById(R.id.button_send);

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

    /** Called when the user taps the Send button */
    public void goToPage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }
}