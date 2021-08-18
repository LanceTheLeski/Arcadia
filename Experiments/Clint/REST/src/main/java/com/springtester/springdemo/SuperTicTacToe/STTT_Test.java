package com.springtester.springdemo.SuperTicTacToe;

import org.json.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import javax.websocket.Session;


@RunWith(MockitoJUnitRunner.class)
public class STTT_Test {

    String message = "message";

    JSONObject msg = new JSONObject();
    JSONObject msg2 = new JSONObject();
    JSONObject msg3 = new JSONObject();

    @Mock
    Session sess;

    @Mock
    Session sess2;

    @Mock
    Session sess3;

    STTTServer server = new STTTServer();

    @Before
    public void init() throws JSONException {
        MockitoAnnotations.initMocks(this);

        msg.put("location", 12);
        msg.put("symbol", 1);
        msg.put("currSymbol", 0);

        msg2.put("location", 12);
        msg2.put("symbol", 3);
        msg2.put("currSymbol", 1);

        msg3.put("location", 11);
        msg3.put("symbol", 2);
        msg3.put("currSymbol", 0);
    }

    @Test
    public void connectionTest() throws Exception{
        //server.onOpen(sess, message);
        //server.onOpen(sess2, "message2");

        //assertEquals("message", server.userTurns.get(0));
        //assertEquals("message2", server.userTurns.get(1));
    }
    @Test
    public void connectionEndTest() throws Exception{


        //server.onOpen(sess, message);
        //server.onClose(sess);

        //assertEquals(0, server.userTurns.size());
    }
    @Test
    public void turnTest() throws Exception{

        //server.onOpen(sess, msg.toString());
        //server.onOpen(sess2, msg2.toString());
        //server.onOpen(sess3, msg3.toString());

        //server.onMessage(sess,msg.toString());
        //server.onMessage(sess2,msg2.toString());
        //server.onMessage(sess3,msg3.toString());

        //assertEquals(1, server.turn);

        //server.onMessage(sess2,msg2.toString());

        //assertEquals(1, server.turn);
    }
}