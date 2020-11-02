package com.example.firstapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class FirstFragment extends Fragment {
    private View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Get clicked color from pallet */
        SharedPreferences settings = getActivity().getSharedPreferences("penColors", 0);
        String pencolor = settings.getString("penColor", null);
        System.out.println("------------ penColor");
        System.out.println(pencolor);
        /* End - Get clicked color from pallet */

        /* Handle painting */
        GestureListener mGestureListener = new GestureListener();
        GestureDetector mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        PaintCanvas paintCanvas = new PaintCanvas(getContext(), null, mGestureDetector, pencolor);
        mGestureListener.setCanvas(paintCanvas);
        /* End - Handle painting */

        // Inflate the layout (paint) for this fragment
        return paintCanvas; //inflater.inflate(R.layout.fragment_first, container, false);
    }
}