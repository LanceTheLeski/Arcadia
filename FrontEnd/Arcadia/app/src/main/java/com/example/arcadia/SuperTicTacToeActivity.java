package com.example.arcadia;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.*;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuperTicTacToeActivity extends AppCompatActivity {

    private int board[][] = {
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
    };

    private WebSocketClient cc;
    private String username = MainActivity.getUsername ();
    private int group = MainActivity.getGroupNumber ();
    private int turn = 0;
    private int playerTurn;
    String S = "Winner ";

    private void Winner() {
        int max = 0;
        for(int i=0 ; i<board.length ; i++){
            for(int j=0 ; j<board[i].length ; j++){
                if(board[i][j] > max) max = board[i][j];
            }
        }
        for(int h=1 ; h<=max ; h++) {
            for (int i = 1; i < board.length - 1; i++) {
                if (board[i - 1][0] == h && board[i][0] == h && board[i + 1][0] == h) {
                    S += h;
                    Win();
                }
                if (board[i - 1][board.length - 1] == h && board[i][board.length - 1] == h && board[i + 1][board.length - 1] == h) {
                    S += h;
                    Win();
                }
                for (int j = 1; j < board.length - 1; j++) {
                    if (board[0][j - 1] == h && board[0][j] == h && board[0][j + 1] == h) {
                        S += h;
                        Win();
                    }
                    if (board[board.length - 1][j - 1] == h && board[board.length - 1][j] == h && board[board.length - 1][j + 1] == h) {
                        S += h;
                        Win();
                    }
                }
                for (int j = 1; j < board.length - 1; j++) {
                    if (board[i][j + 1] == h && board[i][j] == h && board[i][j - 1] == h) {
                        S += h;
                        Win();
                    }
                    if (board[i - 1][j - 1] == h && board[i][j] == h && board[i + 1][j + 1] == h) {
                        S += h;
                        Win();
                    }
                    if (board[i - 1][j + 1] == h && board[i][j] == h && board[i + 1][j - 1] == h) {
                        S += h;
                        Win();
                    }
                    if (board[i - 1][j] == h && board[i][j] == h && board[i + 1][j] == h) {
                        S += h;
                        Win();
                    }
                }
            }
        }
    }

    private void Win(){
        Intent i = new Intent();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Winner: Player " + turn + "!", Toast.LENGTH_LONG).show();
            }
        });
        if(MainActivity.isLeader()) i = new Intent(SuperTicTacToeActivity.this, LibraryActivity.class);
        else i = new Intent(SuperTicTacToeActivity.this, LobbyActivity.class);
        startActivity(i);
    }

    private void connector(Button b1, final int place){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d("Socket:", "turn = " + turn + ", PlayerTurn = " + playerTurn);
                if(isPossible(place)){
                    cc.send(String.valueOf(place));
                }
            }
        });
    }

    private boolean isPossible(int place){
        boolean value = true;
        if(board[(place-1)/5][(place-1)%5] != 0 || turn != playerTurn) value = false;

        return value;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_tic_tac_toe);

        final Button[] buttons = new Button[26];
        buttons[1] = findViewById(R.id.button1);
        buttons[2] = findViewById(R.id.button2);
        buttons[3] = findViewById(R.id.button3);
        buttons[4] = findViewById(R.id.button4);
        buttons[5] = findViewById(R.id.button5);
        buttons[6] = findViewById(R.id.button6);
        buttons[7] = findViewById(R.id.button7);
        buttons[8] = findViewById(R.id.button8);
        buttons[9] = findViewById(R.id.button9);
        buttons[10] = findViewById(R.id.button10);
        buttons[11] = findViewById(R.id.button11);
        buttons[12] = findViewById(R.id.button12);
        buttons[13] = findViewById(R.id.button13);
        buttons[14] = findViewById(R.id.button14);
        buttons[15] = findViewById(R.id.button15);
        buttons[16] = findViewById(R.id.button16);
        buttons[17] = findViewById(R.id.button17);
        buttons[18] = findViewById(R.id.button18);
        buttons[19] = findViewById(R.id.button19);
        buttons[20] = findViewById(R.id.button20);
        buttons[21] = findViewById(R.id.button21);
        buttons[22] = findViewById(R.id.button22);
        buttons[23] = findViewById(R.id.button23);
        buttons[24] = findViewById(R.id.button24);
        buttons[25] = findViewById(R.id.button25);
        
        for(int i=1 ; i<buttons.length ; i++){
            connector(buttons[i], i);
        }

        Draft[] drafts = {new Draft_6455()};

        //String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/STTT/"+uname;
        String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/STTT/"+group+"/"+username;

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onMessage(String message) {
                    if(message.equals("leave")){
                        cc.close();
                        finish();
                        if(!MainActivity.isLeader()) startActivity(new Intent(SuperTicTacToeActivity.this, LobbyActivity.class));
                    }

                    Log.d("Socket:", ""+turn+", "+playerTurn);
                    if(message.contains("your turn: ")) playerTurn = Integer.parseInt(message.substring(11));
                    else{
                        final List<String> result = Arrays.asList(message.split("\\s*,\\s*"));
                        turn = Integer.parseInt(result.get(1));
                        board[(Integer.parseInt(result.get(0))-1)/5][(Integer.parseInt(result.get(0))-1)%5] = turn+1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttons[Integer.parseInt(result.get(0))].setText(""+(turn+1));
                            }
                        });
                        Winner();

                        Log.d("Socket:", "turn = " + result);
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("Socket:", "Connection open");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("Socket:", "Connection closed: " + reason);
                }

                @Override
                public void onError(Exception e){Log.d("Socket:", "Error encountered: " + String.valueOf(e));}
            };
        }
        catch (Exception e) {Log.d("Socket:", "Error encountered: " + String.valueOf(e));}
        cc.connect();


        Button MenuButton = findViewById(R.id.buttonMenu);
        Button ChatButton = findViewById(R.id.buttonChat);
        Button ResetButton = findViewById(R.id.buttonReset);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuperTicTacToeActivity.this, LibraryActivity.class);
                startActivity(i);
            }
        });

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuperTicTacToeActivity.this, LobbyActivity.class);
                startActivity(i);
            }
        });
        ChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuperTicTacToeActivity.this, chatActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(MainActivity.isLeader()) cc.send("leave");
        cc.close();
        finish();
        if(!MainActivity.isLeader() && group>0) startActivity(new Intent(SuperTicTacToeActivity.this, LobbyActivity.class));
    }
}
