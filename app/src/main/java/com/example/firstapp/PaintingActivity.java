package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PaintingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);

        /* HANDLE FRAGMENTS */
        View mainFragment = findViewById(R.id.main_fragment);
        View mainFragment1 = findViewById(R.id.fragment_first_land);
        View mainFragment2 = findViewById(R.id.fragment_second_land);

        // Set the first fragment by default
        if (mainFragment != null) {
            FirstFragment firstFragment = new FirstFragment();
            Bundle bundle = new Bundle();
            bundle.putString("teste", "teste valor");
            firstFragment.setArguments(bundle);
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
        /* END - HANDLE FRAGMENTS */

    }
}