package com.example.arcadia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class LibraryActivity extends AppCompatActivity {

    private String username = MainActivity.getUsername ();
    private int groupNum = MainActivity.getGroupNumber ();
    private WebSocketClient cc;
    private Draft[] drafts = {new Draft_6455()};

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraryactivity);

        Button returnButton = (Button) findViewById(R.id.returnButton);
        Button TicTacToeButton = (Button) findViewById(R.id.button);
        Button Game1 = (Button) findViewById(R.id.game1);
        Button Tanks = (Button) findViewById(R.id.Tanks);
        Button Game2048 = (Button) findViewById(R.id.Game2048);
        Button pause = (Button) findViewById (R.id.pauseButton);//FOR TESTING
        Button RockPaperScissors = (Button) findViewById(R.id.RockPaperScissors);

        Button SuperTicTacToeButton = (Button) findViewById(R.id.button2);
        if(groupNum!=0){
            connect();
        }
        RockPaperScissors.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                //sendMessage(1);
                //Log.d("Socket:", ""+cc.isOpen());
                startActivity(new Intent(LibraryActivity.this, RockPaperScissorsActivity.class));
            }
        });

        returnButton.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                //startActivity(new Intent(LibraryActivity.this, MainActivity.class));
                finish ();
            }
        });
        TicTacToeButton.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                //sendMessage(1);
                //Log.d("Socket:", ""+cc.isOpen());
                startActivity(new Intent(LibraryActivity.this, TicTacToe.class));
            }
        });
        SuperTicTacToeButton.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                sendMessage(2);
                startActivity(new Intent(LibraryActivity.this, SuperTicTacToeActivity.class));
            }
        });
        Game1.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                sendMessage(3);
                startActivity(new Intent(LibraryActivity.this, Game1Activity.class));
            }
        });
        pause.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                startActivity (new Intent (LibraryActivity.this, PauseActivity.class));
            }
        });
        Tanks.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                sendMessage(4);
                startActivity(new Intent(LibraryActivity.this, TanksActivity.class));
            }
        });
        Game2048.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                sendMessage(5);
                startActivity(new Intent(LibraryActivity.this, Game2048Activity.class));
            }
        });

    }

    private void connect(){
        //String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/Party/"+username;
        String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/Lobby/"+groupNum+"/"+username;

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onOpen(ServerHandshake handshake) {

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("Socket:", "Connection closed: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Socket:", "Error encountered: " + String.valueOf(e));
                }
            };
            cc.connect();
        } catch (Exception e) {Log.d("Socket:", "Error encountered: " + String.valueOf(e));}
    }

    private void sendMessage(int value){
        if(cc != null && cc.isOpen()) {
            Log.d("Socket:", ""+value);
            cc.send(String.valueOf(value));
        }
    }

}
