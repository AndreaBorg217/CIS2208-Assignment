package com.example.cis2208_assignment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Picking layout which matches the screen's orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_exit_landscape);
        } else{
            setContentView(R.layout.activity_exit_portrait);
        }

        // Hiding the app bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView left = (ImageView)findViewById(R.id.leftBulb);
        ImageView centre = (ImageView)findViewById(R.id.centerBulb);
        ImageView  right = (ImageView)findViewById(R.id.rightBulb);
        TextView scoreText = (TextView) findViewById(R.id.scoreText);

        // Fetching the score obtained during the round
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);

        // Set images to lit bulbs according to how many correct answers were obtained
        scoreText.setText("You guessed " + score + "/10!");
        if(score > 0){
                left.setImageResource(R.drawable.lit_bulb);
        }
        if(score > 4){
            centre.setImageResource(R.drawable.lit_bulb);

        }
        if(score > 7){
            right.setImageResource(R.drawable.lit_bulb);
        }
    }

    public void goToDifficultySelectionScreen(View view){
        Intent intent = new Intent(this, DifficultySelection.class);
        startActivity(intent);
    }


    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    // Change layout when device's orientation changes
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_exit_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_exit_portrait);
        }
    }
}