package com.example.cis2208_assignment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Choosing the layout which matches the orientation of the device
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_category_scores_landscape);
        } else{
            setContentView(R.layout.activity_category_scores_portrait);
        }

        // Removing the app bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    @Override
    // Changing the layout when the orientation of the device changes
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Reload the appropriate layout when device orientation changes
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_category_scores_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_category_scores_portrait);
        }
    }
}