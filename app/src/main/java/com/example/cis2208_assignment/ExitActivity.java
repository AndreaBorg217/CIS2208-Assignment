package com.example.cis2208_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView left = (ImageView)findViewById(R.id.leftBulb);
        ImageView centre = (ImageView)findViewById(R.id.centerBulb);
        ImageView  right = (ImageView)findViewById(R.id.rightBulb);
        TextView scoreText = (TextView) findViewById(R.id.scoreText);

        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);

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
}