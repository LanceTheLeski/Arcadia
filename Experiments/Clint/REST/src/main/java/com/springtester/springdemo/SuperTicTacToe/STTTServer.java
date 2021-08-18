package com.springtester.springdemo.SuperTicTacToe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/STTT/{group}/{username}")
@Component
public class STTTServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static ArrayList<users> userTurns = new ArrayList<>();
    private static int turn = 0;

    private final Logger logger = LoggerFactory.getLogger(STTTServer.class);

    private void broadcast(String message, int group) throws IOException {
        try {
            for(int i=0 ; i<userTurns.size() ; i++){
                Session session = userTurns.get(i).session;
                if(userTurns.get(i).groupNo == group){
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
            @PathParam("username") String username, @PathParam("group") String group) throws Exception {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        userTurns.add(new users(username, Integer.parseInt(group), session));
        int groupAmount = groupAmount(groupFromSession(session));
        sendMessageToParticularUser(username, "your turn: "+(groupAmount-1));
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        if(message.equals("leave")) broadcast("leave", groupFromSession(session));

        int groupAmount = groupAmount(groupFromSession(session));

        if(turn<groupAmount-1) turn++;
        else turn = 0;
        logger.info(""+turn+", "+groupAmount);

        broadcast(message + ", "+ turn, groupFromSession(session));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        userTurns.remove(userTurns.indexOf(username));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error" + throwable);
    }

    private void sendMessageToParticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String usernameFromSession(Session sess){
        for(int i=0 ; i<userTurns.size() ; i++){
            if(userTurns.get(i).session.equals(sess)) return userTurns.get(i).username;
        }
        return null;
    }

    private int groupFromSession(Session sess){
        for(int i=0 ; i<userTurns.size() ; i++){
            if(userTurns.get(i).session.equals(sess)) return userTurns.get(i).groupNo;
        }
        return -1;
    }

    private int groupAmount(int group){
        int total = 0;
        for(int i=0 ; i<userTurns.size() ; i++){
           if(userTurns.get(i).groupNo == group) total++;
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

