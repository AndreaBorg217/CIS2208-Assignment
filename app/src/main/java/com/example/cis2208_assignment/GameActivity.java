package com.example.cis2208_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        setContentView(R.layout.activity_game);

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
        // a function which populates UI elements with current question
        Question q = questions.get(i);

        question.setText(q.question);

        index.setText((i+1) + "/" + questions.size());

        Resources res = getResources();
        int blue = res.getColor(R.color.blue);
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

            // if the pressed button contains the correct answer the score is incremented
            Button b = (Button) view;
            if(b.getText() == questions.get(i).correctAnswer){
                helper.setCorrectAnswered(questions.get(i).questionId);
                score++;
            }
            // the colours of the buttons are updated to mark which answers are correct and incorrect
            switchButtonColours();

            // after 2secs the next question/exit screen is shown to the user
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run(){
                    i++;
                    if (i < 10) {
                        QuestionRound(questions);
                    } else {
                        //CHECK HIGHSCORE
                        Intent intent = new Intent(GameActivity.this, ExitActivity.class);
                        intent.putExtra("SCORE", score);
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

    protected void switchButtonColour(Button b){
        int red = res.getColor(R.color.red);
        int green = res.getColor(R.color.green);
        if (questions.get(i).correctAnswer.equals(b.getText())) {
            b.setBackgroundTintList(ColorStateList.valueOf(green));
        }
        else{
            b.setBackgroundTintList(ColorStateList.valueOf(red));
        }
    }
}