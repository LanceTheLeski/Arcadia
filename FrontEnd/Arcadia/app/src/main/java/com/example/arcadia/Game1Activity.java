package com.example.arcadia;

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Game1Activity extends AppCompatActivity {




    private long time;
    private static Game1Activity game1Activity = null;
    public static Game1Activity getGame1Activity() {
        return game1Activity;
    }
    public Game1Activity(){
        game1Activity = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // Button pause = (Button) findViewById (R.id.pauseButton);


        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game1);
        //getSupportActionBar().hide();
        setContentView(new game1(this));

       /* pause.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                startActivity (new Intent(Game1Activity.this, PauseActivity.class));
            }
        });
        */

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            long t=System.currentTimeMillis();
            if(t-time<=500){
                exit();
            }else{
                time=t;
                Toast.makeText(getApplicationContext(),"one more time to exit game1",Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return false;

    }
    public void exit(){
        Game1Activity.this.finish();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
                System.exit(0);
            }
        }).start();
    }


}