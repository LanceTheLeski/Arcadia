package com.example.arcadia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity
{
    private WebSocketClient cc;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final game1 temp = new game1(this);
        setContentView(temp);
        String username = MainActivity.getUsername ();
        int groupNum = MainActivity.getGroupNumber ();
        Draft[] drafts = {new Draft_6455()};

        //String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/Party/"+username;
        String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/Lobby/"+groupNum+"/"+username;

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onMessage(String message) {
                    switch (Integer.parseInt(message)){
                        case 1: startActivity(new Intent(LobbyActivity.this, TicTacToe.class));
                                cc.close();
                                finish();
                                break;
                        case 2: startActivity(new Intent(LobbyActivity.this, SuperTicTacToeActivity.class));
                                cc.close();
                                finish();
                                break;
                        case 3: startActivity(new Intent(LobbyActivity.this, Game1Activity.class));
                                cc.close();
                                finish();
                                break;
                        case 4: startActivity(new Intent(LobbyActivity.this, TanksActivity.class));
                                cc.close();
                                finish();
                                break;
                        case 5: startActivity(new Intent(LobbyActivity.this, Game2048Activity.class));
                                cc.close();
                                finish();
                                break;
                        default: return;
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("Socket:", "Connection closed: " + reason);
                }

                @Override
                public void onError(Exception e){Log.d("Socket:", "Error encountered: " + e);}
            };
            cc.connect();
        } catch (Exception e) {Log.d("Socket:", "Error encountered: " + e);}
    }

    @Override
    public void onBackPressed(){
        MainActivity.setGroupNumber (0);
        cc.close();
        finish();
    }

}