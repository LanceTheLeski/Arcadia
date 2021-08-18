package com.example.arcadia;

import android.content.Context;
import android.widget.EditText;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PartyActivityTest
{
    @Mock
    Context mockContext;



    @Test
    public void validRegistrationInformation ()
    {
        LoginActivity test = Mockito.mock (LoginActivity.class);

        EditText hello = new EditText (mockContext);
        test.setUsername (hello);
        test.setPassword (hello);
        test.setFirstname (hello);
        test.setLastname (hello);

        test.validateRegistration ();
    }

    @Test
    public void getCredentials ()
    {
        LoginActivity test = Mockito.mock (LoginActivity.class);

        test.getCredentials ();
    }

    @Test
    public void sendNewCredentials ()
    {
        LoginActivity test = Mockito.mock (LoginActivity.class);

        test.sendNewCredentials ();
    }

    @Test
    public void validLoginInformation ()
    {

    }
}
