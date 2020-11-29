package com.example.firstapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FirstFragment extends Fragment {
    private View v;
    private GestureListener mGestureListener;
    DatabaseReference reff;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Handle painting */
        mGestureListener = new GestureListener();
        GestureDetector mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        PaintCanvas paintCanvas = new PaintCanvas(getContext(), null, mGestureDetector);
        mGestureListener.setCanvas(paintCanvas);
        /* End - Handle painting */


        Button view = (Button) getActivity().findViewById(R.id.button_load_paint);
        if(view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    reff = FirebaseDatabase.getInstance().getReference();
                    reff.child("paintData").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<List<List<PaintData>>> t = new GenericTypeIndicator<List<List<PaintData>>>() {};
                            List<List<PaintData>> loadPaintData = dataSnapshot.getValue(t);
                            if (loadPaintData != null) {
                                mGestureListener.loadPainting(loadPaintData);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            System.out.println("Failed to read value. " + error.toException());
                        }

                    });
                }
            });
        }


        // Inflate the layout (paint) for this fragment
        return paintCanvas; //inflater.inflate(R.layout.fragment_first, container, false);
    }


}