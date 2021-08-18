package com.example.a309experiment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    Button B1;
    Button B2;
    Button B3;
    Button B4;
    Button B5;
    Button B6;
    Button B7;
    Button B8;
    Button B9;
    Button B10;
    Button B11;
    Button B12;
    TextView T10;

    private boolean isPlayerX = true;
    private int board[][] = {
            {0,0,0},
            {0,0,0},
            {0,0,0}
    };

    String S;
    private void Winner(){
        if (isPlayerX) S = "Player X's turn";
        else S = "Player O's turn";
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != 0) ||
                (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[1][1] != 0)) {
            if (board[0][0] == 1) S = "Winner Player X";
            else S = "Winner Player O";
        }
        for (int i = 0; i<3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0){
                if (board[i][0] == 1) S = "Winner Player X";
                else S = "Winner Player O";
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0){
                if (board[0][i] == 1) S = "Winner Player X";
                else S = "Winner Player O";
            }
        }
        Toast.makeText(getApplicationContext(), S, Toast.LENGTH_LONG).show();
        if (S.contentEquals("Winner Player O") || S.contentEquals("Winner Player X")) {
            Intent i = new Intent(Main3Activity.this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        B1 = (Button) findViewById(R.id.button4);
        B2 = (Button) findViewById(R.id.button5);
        B3 = (Button) findViewById(R.id.button6);
        B4 = (Button) findViewById(R.id.button8);
        B5 = (Button) findViewById(R.id.button9);
        B6 = (Button) findViewById(R.id.button10);
        B7 = (Button) findViewById(R.id.button12);
        B8 = (Button) findViewById(R.id.button11);
        B9 = (Button) findViewById(R.id.button13);
        B10 = (Button) findViewById(R.id.button15);
        B11 = (Button) findViewById(R.id.button16);
        B12 = (Button) findViewById(R.id.button7);
        T10 = (TextView) findViewById(R.id.textView12);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B1.setText("X"); board[0][0] = 1;}
                if (!isPlayerX){B1.setText("0"); board[0][0] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B2.setText("X"); board[1][0] = 1;}
                if (!isPlayerX){B2.setText("0"); board[1][0] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B3.setText("X"); board[2][0] = 1;}
                if (!isPlayerX){B3.setText("0"); board[2][0] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B4.setText("X"); board[0][1] = 1;}
                if (!isPlayerX){B4.setText("0"); board[0][1] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B5.setText("X"); board[1][1] = 1;}
                if (!isPlayerX){B5.setText("0"); board[1][1] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B6.setText("X"); board[2][1] = 1;}
                if (!isPlayerX){B6.setText("0"); board[2][1] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B7.setText("X"); board[0][2] = 1;}
                if (!isPlayerX){B7.setText("0"); board[0][2] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B8.setText("X"); board[1][2] = 1;}
                if (!isPlayerX){B8.setText("0"); board[1][2] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isPlayerX) {B9.setText("X"); board[2][2] = 1;}
                if (!isPlayerX){B9.setText("0"); board[2][2] = 2;}
                isPlayerX = !isPlayerX;
                Winner();
                if (isPlayerX)T10.setText("Player X");
                if (!isPlayerX)T10.setText("Player O");
            }
        });
        B10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main3Activity.this, MainActivity.class);
                startActivity(i);
            }
        });
        B11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main3Activity.this, Main3Activity.class);
                startActivity(i);
            }
        });
        B12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Chat Coming Soon", Toast.LENGTH_LONG).show();
            }
        });

    }
}
