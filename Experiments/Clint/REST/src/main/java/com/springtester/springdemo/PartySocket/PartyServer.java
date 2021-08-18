package com.springtester.springdemo.PartySocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import javax.web;

@ServerEndpoint("/Party/{username}")
@Component
public class PartyServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    Connection conn = DriverManager.getConnection("jdbc:mysql://coms-309-bs-8.misc.iastate.edu:3306/Arcadia?verifyServerCertificate=false&useSSL=false&requireSSL=false", "team8", "SecretPass123!");
    public static ArrayList<users> userGroups = new ArrayList<users>();
    public static ArrayList<groupCard> formattedVals;
    public static int totalGroups = 1;

    private final Logger logger = LoggerFactory.getLogger(PartyServer.class);

    public PartyServer() throws SQLException {
    }

    public void broadcast(String message)
            throws IOException
    {
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

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws Exception {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        userGroups.add(new users(username, 0));
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE users SET groupNo=0 WHERE user_name='" + username + "'");

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, SQLException {
        // Handle new messages
        logger.info("Got Message (Party):" + message);
        String username = sessionUsernameMap.get(session);
        if(message.contains("GroupLeave: ")){
            for(int i=0 ; i<userGroups.size() ; i++){
                if(userGroups.get(i).groupNo == Integer.parseInt(message.substring(12))){
                    sendMessageToPArticularUser(userGroups.get(i).username, "CONTINUE");
                }
            }
            return;
        }
        try {
            Statement stmt = conn.createStatement();
            if(message.equals("0")){
                totalGroups++;
                stmt.executeUpdate("UPDATE users SET groupNo=" + totalGroups + " WHERE user_name='" + username + "'");
            } else if(message.equals("-1")){

            } else {
                stmt.executeUpdate("UPDATE users SET groupNo=" + message + " WHERE user_name='" + username + "'");
            }

            ResultSet rs = stmt.executeQuery("SELECT user_name,groupNo FROM users WHERE user_name IS NOT NULL");
            if (rs != null) {
                //rs.next();
                while (rs.next()) {
                    logger.info(rs.getString("groupNo"));
                    int pIdx = findUsername(rs.getString("user_name"));
                    Integer gVal = rs.getInt("groupNo");
                    if (pIdx != -1) {
                        if (gVal!=null)
                            try {
                                userGroups.get(pIdx).groupNo = gVal;
                            }
                            catch(NullPointerException e){}
                        else
                            userGroups.get(pIdx).groupNo = 0;
                    }
                }
            }
        } catch(SQLException ex){
            logger.info(String.valueOf(ex));
        }

        formattedVals = getUniqueGroups();
        String msg = new Gson().toJson(formattedVals);
        broadcast(msg);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        userGroups.remove(findUsername(username));
        try {
            this.onMessage(session, "-1");
        }catch(Exception e){}
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        //userGroups.remove(findUsername(username));
        //broadcast(username);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Entered into Error" + throwable);
    }

    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    public int findUsername(String value){
        int index = -1;

        for(int i=0 ; i<userGroups.size() ; i++){
            if(userGroups.get(i).username.equals(value)) index = i;
        }

        return index;
    }

    private ArrayList<groupCard> getUniqueGroups() throws NullPointerException{
        ArrayList<groupCard> val = new ArrayList<groupCard>();
        ArrayList<Integer> coveredVals = new ArrayList<Integer>();

        for(int i=0 ; i<userGroups.size() ; i++){
            if(userGroups.get(i).groupNo > 0 && !coveredVals.contains(i)){
                int numFound = 1;
                val.add(new groupCard(Integer.toString(userGroups.get(i).groupNo), userGroups.get(i).username,Integer.toString(numFound)+"/4"));

                for(int j=i+1 ; j<userGroups.size() ; j++){
                    if(userGroups.get(j).groupNo == userGroups.get(i).groupNo) {
                        numFound++;
                        val.get(val.size()-1).groupMembers += ", "+ userGroups.get(j).username;
                        val.get(val.size()-1).groupAmount = numFound+"/4";
                        coveredVals.add(j);
                    }
                }
            }
        }

        return val;
    }
}

class groupCard {
    public String groupName;
    public String groupMembers;
    public String groupAmount;

    public groupCard(String groupName, String groupMembers, String groupAmount){
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.groupAmount = groupAmount;
    }
}

class users {
    public String username;
    public int groupNo;

    public users(String username, int groupNo){
        this.username = username;
        this.groupNo = groupNo;
    }
}

