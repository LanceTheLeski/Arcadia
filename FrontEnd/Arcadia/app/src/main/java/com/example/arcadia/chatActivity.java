package com.example.arcadia;

//import android.support.v4.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class chatActivity extends AppCompatActivity {

    private Button  b2;
    private EditText e2;
    private TextView t1,t_users;

    private String link;
    private ArrayList <String> messages;
    private String username = MainActivity.getUsername ();
    private int group = MainActivity.getGroupNumber ();

    private static final String PREFS = "PREFS";

    /*private void restoreSettings ()
    {
        SharedPreferences settings = getSharedPreferences(PREFS, 0);
        username = settings.getString ("username", null);
        for (int temp = 0; temp < settings.getInt ("messagesSize", 0); temp ++) messages.add (settings.getString ("" + temp + "", null));
        for (int temp = 0; temp < messages.size (); temp ++)
        {
            if (temp == 0) t1.append (messages.get (0));
            else t1.append ('\n' + messages.get (temp));
        }

        e1.setText (settings.getString ("username", ""));
        e1.setVisibility (View.GONE);
        b1.setVisibility (View.GONE);

        //if(((LinearLayout) connect).getChildCount() > 0)
            //((LinearLayout) connect).removeAllViews();
        Draft[] drafts = {new Draft_6455()};

        link = "ws://coms-309-bs-8.misc.iastate.edu:8080/chat/" + group +"/"+username;

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(link),(Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    String s = t1.getText().toString();
                    if (message.indexOf(":") != -1)
                    {
                        t1.append('\n' + message);
                        messages.add (message);
                    }
                    else if(t_users.getText().toString().equals("Current Users: "))
                        t_users.append(message);
                    else
                        t_users.append(", "+message);
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
    }*/

    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        b2=(Button)findViewById(R.id.bt2);
        e2=(EditText)findViewById(R.id.et2);
        t1=(TextView)findViewById(R.id.t1);
        t_users=(TextView)findViewById(R.id.t_users);

        Button exit = (Button) findViewById (R.id.exitChatButton);

        messages = new ArrayList <String> ();

        //SharedPreferences settings = getSharedPreferences(PREFS, 0);
        //String username = settings.getString ("username", "");
        //if (! (username.equalsIgnoreCase (""))) restoreSettings ();

        exit.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View view)
            {
                finish ();//This will change once chat works without issue. Ideally it will save the content of the messages so they'll be up once users visit the page again
            }
        });

        //if(((LinearLayout) connect).getChildCount() > 0)
            //((LinearLayout) connect).removeAllViews();
        Draft[] drafts = {new Draft_6455()};

        link = "ws://coms-309-bs-8.misc.iastate.edu:8080/chat/"+group+"/"+username;

        try {
            Log.d ("Socket:", "Trying socket");
            cc = new WebSocketClient (new URI (link), (Draft) drafts [0]) {
                @Override
                public void onMessage (String message) {
                    Log.d("", "run() returned: " + message);
                    String s=t1.getText().toString();
                    if (message.indexOf(":") != -1)
                    {
                        messages.add (message);
                        t1.append('\n' + message);
                    }
                    else if (t_users.getText().toString().equals("Current Users: "))
                        t_users.setText("Current Users: "+message);
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cc.send(e2.getText().toString());
                    e2.setText("");
                    //if(((LinearLayout) connect).getChildCount() > 0)
                    //   ((LinearLayout) connect).removeAllViews();
                }
                catch (Exception e)
                {
                    Log.d("ExceptionSendMessage:", ""+e);
                }
            }
        });
    }

    /*@Override
    public void onBackPressed(){
        cc.close();
        finish();
    }*/
}
