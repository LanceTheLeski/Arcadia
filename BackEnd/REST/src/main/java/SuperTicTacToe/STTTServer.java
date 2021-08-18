package SuperTicTacToe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;
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

//import javax.web;

@ServerEndpoint("/STTT/{username}")
@Component
public class STTTServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    public ArrayList<String> userTurns = new ArrayList<>();
    public int turn = 0;

    private final Logger logger = LoggerFactory.getLogger(STTTServer.class);

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

        userTurns.add(username);
        //broadcast(username);

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);

        msgInfo newMessage = new ObjectMapper().readValue(message, msgInfo.class);

            if(newMessage.symbol-1 == turn && newMessage.currSymbol == 0) {
                broadcast(message);
                if(turn < userTurns.size())
                    turn++;
                else
                    turn = 0;
            }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        userTurns.remove(userTurns.indexOf(username));
        broadcast(username);
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
}

class msgInfo {
    public int location;
    public int symbol;
    public int currSymbol;
}

