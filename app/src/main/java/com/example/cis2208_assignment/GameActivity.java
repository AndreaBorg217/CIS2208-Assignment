package com.example.cis2208_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cis2208_assignment.backend.DbHelper;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Intent fetch =  getIntent();
        String difficulty = fetch.getStringExtra("DIFFICULTY");
        String category = fetch.getStringExtra("CATEGORY");
        System.out.println(difficulty);
        System.out.println(category);
        DbHelper helper = new DbHelper(this);
        int categoryID = helper.getCategoryID(category);
        helper.getTenQuestions(difficulty, categoryID);
    }
}