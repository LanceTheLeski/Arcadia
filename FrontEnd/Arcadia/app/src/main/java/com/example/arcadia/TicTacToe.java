package com.example.arcadia;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;

public class TicTacToe extends AppCompatActivity implements View.OnClickListener{
    TextView T10;
    private enum t{
        X, O, Winner;
    }
    private t turn = t.X;
    char Turn;
    private Button buttons[] = new Button[9];
    boolean isPlayingAi = false;
    boolean HardAi = false;
    String S;
    private void Winner(){
        if (((buttons[0].getText().toString().equals(buttons[4].getText().toString()) &&
                buttons[4].getText().toString().equals(buttons[8].getText().toString())) ||
                (buttons[2].getText().toString().equals(buttons[4].getText().toString()) &&
                        buttons[6].getText().toString().equals(buttons[4].getText().toString()))) &&
                !buttons[4].getText().toString().equals(" ")) {
            if (buttons[0].getText().toString().equals("X")) S = "Winner Player X";
            else S = "Winner Player O";
            turn = t.Winner;
            Toast.makeText(getApplicationContext(), S, Toast.LENGTH_LONG).show();
        }
        for (int i = 0; i < 6; i += 3) {
            if ((buttons[i].getText().toString().equals(buttons[i+1].getText()) &&
                    buttons[i+1].getText().equals(buttons[i+2].getText())) &&
                    !buttons[i+1].getText().equals(" ")){
                if (buttons[i].getText().toString().equals("X")) S = "Winner Player X";
                else S = "Winner Player O";
                turn = t.Winner;
                Toast.makeText(getApplicationContext(), S, Toast.LENGTH_LONG).show();
            }
        }
        for (int i = 0; i < 3; i++) {
            if ((buttons[i].getText().toString().equals(buttons[i+3].getText()) &&
                    buttons[i+6].getText().toString().equals(buttons[i+3].getText())) &&
                    !buttons[i+3].getText().toString().equals(" ")){
                if (buttons[i].getText().toString().equals("X")) S = "Winner Player X";
                else S = "Winner Player O";
                turn = t.Winner;
                Toast.makeText(getApplicationContext(), S, Toast.LENGTH_LONG).show();
            }
        }
    }
    private void AiMove(){
        Random r = new Random();
        int i = r.nextInt(9);
        if (!HardAi) {
            if (buttons[i].getText().toString().equals(" ") && !HardAi) {
                buttons[i].setText("O");
            } else AiMove();
        }else {
            if (buttons[4].getText().toString().equals(" ")) {
                buttons[4].setText("O");
            } else if (buttons[i].getText().toString().equals(" ")) {
                buttons[i].setText("O");
            } else AiMove();
        }
        Winner();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        buttons[0] = (Button) findViewById(R.id.button1);
        buttons[1] = (Button) findViewById(R.id.button2);
        buttons[2] = (Button) findViewById(R.id.button3);
        buttons[3] = (Button) findViewById(R.id.button4);
        buttons[4] = (Button) findViewById(R.id.button5);
        buttons[5] = (Button) findViewById(R.id.button6);
        buttons[6] = (Button) findViewById(R.id.button7);
        buttons[7] = (Button) findViewById(R.id.button8);
        buttons[8] = (Button) findViewById(R.id.button9);
        Button B10 = (Button) findViewById(R.id.button10);
        Button B11 = (Button) findViewById(R.id.button11);
        Button B12 = (Button) findViewById(R.id.button12);
        Button B13 = (Button) findViewById(R.id.button13);
        Button B14 = (Button) findViewById(R.id.button14);
        T10 = (TextView) findViewById(R.id.textView10);

        for (int i = 0; i < 9; i++) {
            buttons[i].setOnClickListener(this);
        }
        B10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TicTacToe.this, MainActivity.class);
                startActivity(i);
            }
        });
        B11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TicTacToe.this, TicTacToe.class);
                startActivity(i);
            }
        });
        B12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Chat Coming Soon", Toast.LENGTH_LONG).show();
            }
        });
        B13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Playing Ai", Toast.LENGTH_LONG).show();
                for (int i = 0; i < 9; i++){
                    buttons[i].setText(" ");
                }
                isPlayingAi = true;
                turn = t.X;
            }
        });
        B14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HardAi = !HardAi;
                if (HardAi){
                    Toast.makeText(getApplicationContext(), "playing hard Ai", Toast.LENGTH_LONG).show();
                }
                if (!HardAi){
                    Toast.makeText(getApplicationContext(), "playing easy Ai", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (turn.equals(t.X)) {
            Turn = 'X';
        }
        if (turn.equals(t.O)) {
            Turn = 'O';
        }
        if (turn.equals(t.Winner)) {
            return;
        } else {
            if (((Button) v).getText().toString().equals(" ") && !isPlayingAi) {
                ((Button) v).setText("" + Turn);
                if (turn.equals(t.X)) {
                    Turn = 'O';
                    turn = t.O;
                } else {
                    Turn = 'X';
                    turn = t.X;
                }
                Winner();
            }
            if (((Button) v).getText().toString().equals(" ") && isPlayingAi) {
                ((Button) v).setText("" + Turn);
                AiMove();
                Turn = 'X';
                turn = t.X;
                Winner();
            }
        }
        T10.setText("Player" + Turn);
        }
    }

