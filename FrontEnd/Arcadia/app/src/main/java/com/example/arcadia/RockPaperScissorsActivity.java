package com.example.arcadia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class RockPaperScissorsActivity extends AppCompatActivity {
    TextView opponent;
    TextView Score;
    TextView opponentscore;
    int PlayerScore = 0;
    int OpponentScore = 0;
    private void Turn(int i){
        Random r = new Random();
        int j = r.nextInt(3);
        if (j == 0) {
            opponent.setText("Opponent Selects Rock");
            if (i == 1) PlayerScore++;
            if (i == 2) OpponentScore++;
        }
        if (j == 1){
            opponent.setText("Opponent Selects Paper");
            if (i == 2) PlayerScore++;
            if (i == 0) OpponentScore++;
        }
        if (j == 2){
            opponent.setText("Opponent selects Scissors");
            if (i == 0) PlayerScore++;
            if (i == 1) OpponentScore++;
        }
        Score.setText("Player Score " + PlayerScore);
        opponentscore.setText("Opponent Score " + OpponentScore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock_paper_scissors);
        Button Rock = (Button) findViewById(R.id.button26);
        Button Paper =  (Button) findViewById(R.id.button27);
        Button Scissors = (Button) findViewById(R.id.button28);
        Button Menu =  (Button) findViewById(R.id.button29);
        opponent = (TextView) findViewById(R.id.textView);
        Score = (TextView) findViewById(R.id.textView2);
        opponentscore = (TextView) findViewById(R.id.textView3);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RockPaperScissorsActivity.this, LibraryActivity.class);
                startActivity(i);
            }
        });
        Rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Turn(0);
            }
        });
        Paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Turn(1);
            }
        });
        Scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Turn(2);
            }
        });
    }
}
