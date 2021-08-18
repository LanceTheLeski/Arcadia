package com.example.arcadia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PauseActivity extends Activity
{

    private static PauseActivity pauseActivity = null;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_pauseactivity);

        Button close = (Button) findViewById (R.id.leaveButton);
        Button chat = (Button) findViewById (R.id.chatButton);
        Button scoreboard = (Button) findViewById (R.id.scoreboardButton);

        close.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                finish ();
            }
        });

        chat.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(PauseActivity.this, chatActivity.class));
            }
        });

        scoreboard.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(PauseActivity.this, LibraryActivity.class));//Will change when we add scoreboard
            }
        });

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics (display);

        int width = display.widthPixels;
        int height = display.heightPixels;

        getWindow ().setLayout ((int) (width * .8), (int) (height * .6));

        WindowManager.LayoutParams params = getWindow ().getAttributes ();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow ().setAttributes (params);
    }
}
