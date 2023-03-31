package com.example.cis2208_assignment.ui.easy;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.cis2208_assignment.R;
import com.example.cis2208_assignment.backend.DbHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EasyFragment extends Fragment {

    private TextView score;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_easy, container, false);
        DbHelper helper = new DbHelper(view.getContext());
        score =  (TextView) view.findViewById(R.id.easy_score);
        score.setText("You guessed " + helper.getDifficultyScore("Easy") + " easy questions");
        return view;
    }
}
