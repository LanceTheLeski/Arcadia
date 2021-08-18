package com.springtester.springdemo.LobbySocket;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import javax.web;

@ServerEndpoint("/Lobby/{group}/{username}")
@Component
public class LobbyServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();


    private final Logger logger = LoggerFactory.getLogger(LobbyServer.class);

    public void broadcast(String message)
            throws IOException {
        try {
            sessionUsernameMap.forEach((session, username) -> {
                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (NullPointerException e) {

        }
    }

    //@PathParam("username")
    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username, @PathParam("group") String group) throws Exception {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, SQLException {
        // Handle new messages
        logger.info("Got Message (Lobby):" + message);
        String username = sessionUsernameMap.get(session);

        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        //broadcast(username);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error" + throwable);
    }
}
