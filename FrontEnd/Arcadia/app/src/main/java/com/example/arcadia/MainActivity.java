package com.example.arcadia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private static int groupNum = 0;
    private static String username = "";
    private static boolean leader = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button library = (Button) findViewById (R.id.libraryButton);
        Button party = (Button) findViewById (R.id.partyButton);
        Button options = (Button) findViewById (R.id.optionsButton);

        library.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                startActivity(new Intent(MainActivity.this, LibraryActivity.class));
            }
        });

        party.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                if(username=="")
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                else
                    startActivity(new Intent(MainActivity.this, JoinPartyActivity.class));
            }
        });

        options.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                startActivity(new Intent(MainActivity.this, OptionsActivity.class));
            }
        });
    }

    public static int getGroupNumber ()
    {
        return groupNum;
    }

    public static String getUsername ()
    {
        return username;
    }

    public static boolean isLeader ()
    {
        return leader;
    }

    public static void setGroupNumber (int newGroup)
    {
        groupNum = newGroup;
    }

    public static void setUsername (String newUser)
    {
        username = newUser;
    }

    public static void setLeader (boolean newLeader)
    {
        leader = newLeader;
    }
}
