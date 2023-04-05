package com.example.cis2208_assignment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cis2208_assignment.backend.DbHelper;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    protected int i = 0;
    protected int score = 0;
    protected ArrayList<Question> questions = new ArrayList<Question>();
    protected TextView question;
    protected TextView index;
    protected Button option_1;
    protected Button option_2;
    protected Button option_3;
    protected Button option_4;
    protected Resources res;
    DbHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_game_landscape);
        } else{
            setContentView(R.layout.activity_game_portrait);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent fetch =  getIntent();
        String difficulty = fetch.getStringExtra("DIFFICULTY");
        String category = fetch.getStringExtra("CATEGORY");

        helper = new DbHelper(this);
        int categoryID = helper.getCategoryID(category);
        questions = helper.getTenQuestions(difficulty, categoryID);

        question = (TextView)findViewById(R.id.question);
        index = (TextView)findViewById(R.id.question_index);
        option_1 = (Button) findViewById(R.id.button1);
        option_2 = (Button) findViewById(R.id.button2);
        option_3 = (Button) findViewById(R.id.button3);
        option_4 = (Button) findViewById(R.id.button4);

        res = getResources();

        QuestionRound(questions);
    }

    protected void QuestionRound(@NonNull ArrayList<Question> questions){
        System.out.println("SCORE" + score);
        // a function which populates UI elements with current question
        Question q = questions.get(i);

        question.setText(q.question);

        index.setText((i+1) + "/" + questions.size());

        Resources res = getResources();
        int blue = res.getColor(R.color.blue);

        enableButtons();
        option_1.setBackgroundTintList(ColorStateList.valueOf(blue));
        option_1.setText(q.answers[0]);
        option_1.setOnClickListener(checkAnswer);

        option_2.setBackgroundTintList(ColorStateList.valueOf(blue));
        option_2.setText(q.answers[1]);
        option_2.setOnClickListener(checkAnswer);

        option_3.setBackgroundTintList(ColorStateList.valueOf(blue));
        option_3.setText(q.answers[2]);
        option_3.setOnClickListener(checkAnswer);

        option_4.setBackgroundTintList(ColorStateList.valueOf(blue));
        option_4.setText(q.answers[3]);
        option_4.setOnClickListener(checkAnswer);
    }

    View.OnClickListener checkAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Buttons are disabled to not register further clicks during 2 sec pause
            disableButtons();

            // If the pressed button contains the correct answer the score is incremented
            Button b = (Button) view;
            if(b.getText() == questions.get(i).correctAnswer){
                helper.setCorrectAnswered(questions.get(i).questionId);
                score++;
            }

            // The colours of the buttons are updated to mark which answers are correct and incorrect
            switchButtonColours();

            // After 2secs the next question/exit screen is shown to the user
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run(){
                    i++;
                    if (i < 10) {
                        QuestionRound(questions);
                    } else {
                        // If the round's score is greater than the highscore, the local value is updated
                        if(score > helper.getHighScore()){
                            helper.updateHighScore(score);
                        }
                        Intent intent = new Intent(GameActivity.this, ExitActivity.class);
                        intent.putExtra("SCORE", score); // the score is passed to the Exit Screen
                        startActivity(intent);
                    }
                }
            }, 2000); //
        }
    };

    protected void switchButtonColours(){
        switchButtonColour(option_1);
        switchButtonColour(option_2);
        switchButtonColour(option_3);
        switchButtonColour(option_4);

    }

    protected void disableButtons(){
        option_1.setEnabled(false);
        option_2.setEnabled(false);
        option_3.setEnabled(false);
        option_4.setEnabled(false);
    }

    protected void enableButtons(){
        option_1.setEnabled(true);
        option_2.setEnabled(true);
        option_3.setEnabled(true);
        option_4.setEnabled(true);
    }

    protected void switchButtonColour(Button b){
        int red = res.getColor(R.color.red);
        int green = res.getColor(R.color.green);
        if (questions.get(i).correctAnswer.equals(b.getText())) {
            b.setBackgroundTintList(ColorStateList.valueOf(green));
        }
        else{
            b.setBackgroundTintList(ColorStateList.valueOf(red));
        }
        b.setTextColor(res.getColor(R.color.white));
    }
    @Override
    // Change the layout when the device's orientation changes
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_game_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_game_portrait);
        }
    }

}