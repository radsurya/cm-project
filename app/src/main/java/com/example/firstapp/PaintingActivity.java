package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaintingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);

        // getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, firstFragment).commit();

        final Button buttonF1 = (Button) findViewById(R.id.button_fragment1);
        buttonF1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirstFragment firstFragment = new FirstFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, firstFragment).commit();

                // your handler code here
                System.out.println("Teste 1");

            }
        });
        final Button buttonF2 = (Button) findViewById(R.id.button_fragment2);
        buttonF2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                System.out.println("Teste 2");
            }
        });
    }
}