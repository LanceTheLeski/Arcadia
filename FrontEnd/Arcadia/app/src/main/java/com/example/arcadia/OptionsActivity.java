package com.example.arcadia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionsactivity);

        Button returnButton = (Button) findViewById(R.id.returnButton);

        returnButton.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick(View view)
            {
                //startActivity(new Intent (OptionsActivity.this, MainActivity.class));
                finish ();
            }
        });
    }
}

