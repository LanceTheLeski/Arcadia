package com.springtester.springdemo.WebSocket;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import javax.web;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/chat/{group}/{username}")
@Component
public class WebSocketServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static ArrayList<users> userArray = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private void broadcast(String message, int group) throws IOException {
        try {
            for(int i=0 ; i<userArray.size() ; i++){
                Session session = userArray.get(i).session;
                if(userArray.get(i).groupNo == group){
                    synchronized (session) {
                        try {
                            session.getBasicRemote().sendText(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {

        }
    }


    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username, @PathParam("group") String group) throws IOException {
        logger.info("Entered into Open");

        if(sessionUsernameMap.containsValue(username)) return;
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        userArray.add(new users(username, Integer.parseInt(group), session));

        ArrayList<String> temp = groupAmount(Integer.parseInt(group));
        String message = "";
        for(int i=0 ; i<temp.size() ; i++) {
            message += temp.get(i);
            if(i<temp.size()-1) message += ", ";
        }
        broadcast(message, Integer.parseInt(group));

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);

        if (message.startsWith("@"))
        {
            String destUsername = message.split(" ")[0].substring(1);
            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
        } else
        {
            broadcast(username + ": " + message, groupFromSession(session));
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        int groupNo = groupFromSession(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        userArray.remove(userArray.indexOf(new users(username, groupNo, session)));

        String message = username + " disconnected";
        broadcast(message, groupNo);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private int groupFromSession(Session sess){
        for(int i=0 ; i<userArray.size() ; i++){
            if(userArray.get(i).session.equals(sess)) return userArray.get(i).groupNo;
        }
        return -1;
    }

    private ArrayList<String> groupAmount(int group){
        ArrayList<String> total = new ArrayList<>();
        for(int i=0 ; i<userArray.size() ; i++){
            if(userArray.get(i).groupNo == group) total.add(userArray.get(i).username);
        }

        return total;
    }
}

class users {
    public String username;
    public int groupNo;
    public Session session;

    public users(String username, int groupNo, Session session){
        this.username = username;
        this.groupNo = groupNo;
        this.session = session;
    }
}

