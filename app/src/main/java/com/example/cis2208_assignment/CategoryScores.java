package com.example.cis2208_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CategoryScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_scores);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}