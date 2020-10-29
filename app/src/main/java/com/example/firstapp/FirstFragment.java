package com.example.firstapp;

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
        Bundle bundle = this.getArguments();
        System.out.println("------------ bundle");
        System.out.println(bundle);
        if (bundle != null) {
            String mystr = bundle.getString("teste");
            System.out.println("------------ mystr");
            System.out.println(mystr);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* HANDLE PAINTING */
        GestureListener mGestureListener = new GestureListener();
        GestureDetector mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        PaintCanvas paintCanvas = new PaintCanvas(getContext(), null, mGestureDetector);
        mGestureListener.setCanvas(paintCanvas);
        /* END - HANDLE PAINTING */

        // Inflate the layout for this fragment
        return paintCanvas; //inflater.inflate(R.layout.fragment_first, container, false);
    }
}