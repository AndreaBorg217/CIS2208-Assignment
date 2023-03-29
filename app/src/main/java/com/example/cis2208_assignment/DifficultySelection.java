package com.example.cis2208_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cis2208_assignment.backend.DbHelper;


public class DifficultySelection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection_portrait);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}