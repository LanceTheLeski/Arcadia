package com.example.arcadia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
{
    private EditText username, firstName, lastName, password, existingUsername, existingPassword;
    private TextView error, error2;

    private String URL = "http://coms-309-bs-8.misc.iastate.edu:8080/users/new";

    private String [] usernames = new String [0];
    private String [] passwords = new String [0];
    private boolean login = false;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_loginactivity);

        Button submitButton = (Button) findViewById (R.id.submitButton);
        Button logInButton = (Button) findViewById (R.id.logInButton);
        Button returnButton = (Button) findViewById (R.id.returnButton);

        username = (EditText) findViewById (R.id.enterUsername);
        firstName = (EditText) findViewById (R.id.enterFirstName);
        lastName = (EditText) findViewById (R.id.enterLastName);
        password = (EditText) findViewById (R.id.enterPassword);

        error = (TextView) findViewById (R.id.error);

        existingUsername = (EditText) findViewById (R.id.existingUsername);
        existingPassword = (EditText) findViewById (R.id.existingPassword);

        error2 = (TextView) findViewById (R.id.error2);

        returnButton.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                finish ();
            }
        });

        logInButton.setOnClickListener (new View.OnClickListener ()
        {
            public void onClick (View view)
            {
                login = true;
                getCredentials ();
            }
        });

        submitButton.setOnClickListener (new View.OnClickListener()
        {
            public void onClick (View view)
            {
                validateRegistration ();
            }
        });
    }

    private void validateRegistration ()
    {
        boolean valid = true;

        if ((username.getText ().toString ().length () > 12) ||
                (firstName.getText ().toString ().length () > 12) ||
                (lastName.getText ().toString ().length () > 12) ||
                (password.getText ().toString ().length () > 12)
        )
        {
            error.setText ("All fields must be less than 13 characters!");
            valid = false;
        }

        if (password.getText ().toString ().length () <= 5)
        {
            error.setText ("Passwords must at least be 5 characters");
            valid = false;
        }

        for (int temp = 0; temp < username.getText ().toString ().length (); temp ++) if (username.getText ().toString ().charAt (temp) == ' ')
        {
            error.setText ("The username should contain no spaces!");
            valid = false;
        }

        getCredentials();

        if (usernames.length != 0) for (int temp = 0; temp < usernames.length; temp++)
            if (username.getText ().toString ().equalsIgnoreCase (usernames [temp]))
            {
                error.setText ("Username is taken. Please try another");
                valid = false;
            }

        if (valid == true)
        {
            error.setVisibility (View.INVISIBLE);
            sendNewCredentials ();
        }
        else error.setVisibility (View.VISIBLE);
    }

    private boolean validateLogin ()
    {
        boolean valid = false;
        
        for (int temp = 0; temp < usernames.length; temp++) {
            if ((existingUsername.getText().toString().equalsIgnoreCase(usernames[temp])) && (existingPassword.getText().toString().equalsIgnoreCase(passwords[temp]))) {
                valid = true;
                break;
            }
        }

        if (valid == false)
        {
            error2.setText ("Your information is not correct.");
            error2.setVisibility (View.VISIBLE);
        }
        else error2.setVisibility (View.INVISIBLE);

        if (valid){
            Log.e("userOut", existingUsername.getText().toString());
            MainActivity.setUsername (existingUsername.getText().toString());
            finish();
            startActivity(new Intent (LoginActivity.this, JoinPartyActivity.class));
        } else login = false;

        return valid;
    }

    private void getCredentials ()
    {
        RequestQueue queue = Volley.newRequestQueue (getApplicationContext ());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URL.substring(0, 48), null, new Response.Listener <JSONArray> ()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                Log.e("WORKS", "WORKS");

                usernames = new String [response.length ()];
                passwords = new String [response.length ()];

                for (int temp = 0; temp < usernames.length; temp ++)
                {
                    try
                    {
                        usernames [temp] = response.getJSONObject (temp).getString ("userName");
                        passwords [temp] = response.getJSONObject (temp).getString ("password");
                    }
                    catch (JSONException error)
                    {
                        Toast.makeText (LoginActivity.this, error.toString () + " ...1", Toast.LENGTH_LONG).show ();
                    }
                }
                if(login) validateLogin();
            }
        },
        new Response.ErrorListener ()
        {
            @Override
            public void onErrorResponse (VolleyError error)
            {
                Toast.makeText (LoginActivity.this, error.toString () + " ...2", Toast.LENGTH_LONG).show ();
            }
        });

        queue.add (arrayRequest);
    }

    private void sendNewCredentials ()
    {
        RequestQueue queue = Volley.newRequestQueue (getApplicationContext ());

        String userStr = username.getText ().toString ();
        String firstStr = firstName.getText ().toString ();
        String lastStr = lastName.getText ().toString ();
        String passStr = password.getText ().toString ();

        JSONObject json = new JSONObject ();
        try
        {
                json.put ("userName", userStr);
                json.put ("firstName", firstStr);
                json.put ("lastName", lastStr);
                json.put ("password", passStr);
        }
        catch (JSONException e)
        {
            Log.e ("DOESN'T WORK", "DOESN'T WORK");
            e.printStackTrace ();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.POST, URL, json, new Response.Listener <JSONObject> ()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.e("WORKS", "WORKS");
            }
        },
        new Response.ErrorListener ()
        {
            @Override
            public void onErrorResponse (VolleyError error)
            {
                //Toast.makeText (LoginActivity.this, error.toString (), Toast.LENGTH_LONG).show ();
            }
        });

        queue.add (objectRequest);
    }

    public void setUsername (EditText text)
    {
        username = text;
    }

    public void setPassword (EditText text)
    {
        password = text;
    }

    public void setFirstname (EditText text)
    {
        firstName = text;
    }

    public void setLastname (EditText text)
    {
        lastName = text;
    }

    public String getError ()
    {
        return error.getText ().toString ();
    }
}
