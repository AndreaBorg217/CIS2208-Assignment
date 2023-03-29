package com.example.cis2208_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Toolbar extends Fragment {

    private ImageView back;
    private ImageView profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toolbar, container, false);

        // Find the buttons in the layout
        back =  (ImageView) view.findViewById(R.id.back_button);
        profile = (ImageView) view.findViewById(R.id.profile_button);

        // Set click listeners for the buttons
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity a  = (Activity) v.getContext();
                a.onBackPressed();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity) v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
