package com.example.arcadia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ScrollView;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JoinPartyActivity extends AppCompatActivity {

    private groupCardAdapter adapter;
    public RecyclerView screen;
    private ArrayList<groupCard> groupCardArrayList = new ArrayList<>();
    public WebSocketClient cc;
    public static String newGroup = "0";

    public void onCreate (final Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_joinpartyactivity);

        Button leave = findViewById (R.id.leaveButton);
        Button Continue = findViewById (R.id.ContinueButton);
        Button group = findViewById (R.id.groupButton);
        screen = findViewById(R.id.ListView);
        String username = MainActivity.getUsername ();
        final int groupNum = MainActivity.getGroupNumber ();

        screen.setLayoutManager(new LinearLayoutManager(this));
        adapter = new groupCardAdapter(this, groupCardArrayList);
        screen.setAdapter(adapter);

        leave.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                //startActivity (new Intent (JoinPartyActivity.this, LoginActivity.class));
                cc.close();
                finish();
            }
        });

        Continue.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                if(MainActivity.isLeader ()) {
                    cc.send("GroupLeave: " + MainActivity.getGroupNumber ());
                    cc.close();
                    finish();
                    startActivity(new Intent(JoinPartyActivity.this, LibraryActivity.class));
                }
            }
        });

        Draft[] drafts = {new Draft_6455()};

        //String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/Party/"+username;
        String w = "ws://coms-309-bs-8.misc.iastate.edu:8080/Party/"+username;

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onMessage(String message) {

                    Log.d("Socket:", "Trying to receive a message");

                    if(message.equals("CONTINUE")){
                        cc.close();
                        if(!MainActivity.isLeader ()){
                            finish();
                            startActivity(new Intent(JoinPartyActivity.this, LobbyActivity.class));
                        }
                        return;
                    }

                    Type listType = new TypeToken<ArrayList<groupCard>>(){}.getType();
                    ArrayList<groupCard> temp = new Gson().fromJson(message, listType);
                    groupCardArrayList.clear();
                    for(int i=0 ; i<temp.size() ; i++){
                        Log.d("Socket:", String.valueOf(i));
                        groupCardArrayList.add(temp.get(i));
                        if(temp.get(i).getGroupMembers().contains(MainActivity.getUsername ())) {
                            MainActivity.setGroupNumber (Integer.parseInt (temp.get (i).getGroupName ()));
                            List<String> result = Arrays.asList(temp.get(i).getGroupMembers().split("\\s*,\\s*"));
                            if(result.get(0).equals(MainActivity.getUsername ())) MainActivity.setLeader (true);
                            else MainActivity.setLeader (false);
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.d("Socket:", groupCardArrayList.get(1).getGroupName());
                            } catch(Exception e){}
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("Socket:", "Connection open");
                    cc.send("-1");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
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

        group.setOnClickListener (new View.OnClickListener() {
            public void onClick (View view) {
                cc.send("0");
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if (!newGroup.equals("0")) {
                        Log.d("Socket:", newGroup);
                        if(!cc.isClosed())
                            cc.send(newGroup);
                        newGroup = "0";
                    }
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed(){
        MainActivity.setGroupNumber (0);
        cc.close();
        finish();
    }
}


class groupCard {
    private String groupName;
    private String groupMembers;
    private String groupAmount;

    public groupCard(String groupName, String groupMembers, String groupAmount){
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.groupAmount = groupAmount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getGroupAmount() {
        return groupAmount;
    }

    public void setGroupAmount(String groupAmount) {
        this.groupAmount = groupAmount;
    }
}

class groupCardAdapter extends RecyclerView.Adapter<groupCardHolder> {

    private final Context context;
    private ArrayList<groupCard> groupCards;

    public groupCardAdapter(Context context, ArrayList<groupCard> groupCards){
        this.context = context;
        this.groupCards = groupCards;
    }

    @Override
    public groupCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.partycard, parent, false);
        return new groupCardHolder(view);
    }

    @Override
    public void onBindViewHolder(groupCardHolder holder, final int position) {
        groupCard groupCard = groupCards.get(position);
        holder.setDetails(groupCard);
    }

    @Override
    public int getItemCount() {
        return groupCards.size();
    }

    public void update(){
        this.notifyDataSetChanged();
    }
}

class groupCardHolder extends RecyclerView.ViewHolder {

    private TextView GroupName, GroupMembers, GroupAmount;

    public groupCardHolder(View itemView) {
        super(itemView);

        GroupName = itemView.findViewById(R.id.GroupName);
        GroupMembers = itemView.findViewById(R.id.GroupMembers);
        GroupAmount = itemView.findViewById(R.id.GroupAmount);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View itemView) {
                JoinPartyActivity.newGroup = GroupName.getText().toString();
            }
        });
    }

    public void setDetails(groupCard groupCard){
        GroupName.setText(groupCard.getGroupName());
        GroupMembers.setText(groupCard.getGroupMembers());
        GroupAmount.setText(groupCard.getGroupAmount());
    }
}
