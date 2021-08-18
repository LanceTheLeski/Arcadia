package com.example.arcadia;


import android.os.Bundle;
        import android.app.Activity;
        import android.view.Menu;
        import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Game2048Activity extends AppCompatActivity {

    private TextView tvScore;
    private static Game2048Activity game2048Activity = null;
    private int score = 0;

    public Game2048Activity(){
        game2048Activity = this;
    }

    public static Game2048Activity getGame2048Activity() {
        return game2048Activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);
        //setContentView(new GameView(this));
        tvScore = (TextView) findViewById(R.id.tvScore);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.game2048, menu);
        return true;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void addScore(int s){
        score+=s;
        showScore();
    }

    public void showScore(){
        tvScore.setText(" : "+score+"");
    }


}




