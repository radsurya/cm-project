package com.example.firstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        configureImageButton(v);
        return v;
    }

    private void configureImageButton(View v) {
        if (v != null) {
            final ImageButton imgBtnColor1 = (ImageButton) v.findViewById(R.id.color1);
            final ImageButton imgBtnColor2 = (ImageButton) v.findViewById(R.id.color2);
            final ImageButton imgBtnColor3 = (ImageButton) v.findViewById(R.id.color3);
            final ImageButton imgBtnColor4 = (ImageButton) v.findViewById(R.id.color4);

            if (imgBtnColor1 != null) {
                imgBtnColor1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "You clicked color black!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (imgBtnColor2 != null) {
                imgBtnColor2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        // TODO Save clicked color


                        Toast.makeText(getActivity(), "You clicked color red!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (imgBtnColor3 != null) {
                imgBtnColor3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "You clicked color green!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (imgBtnColor4 != null) {
                imgBtnColor4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "You clicked color blue!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}