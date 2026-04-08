package websocket;


import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ErrorMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


//this class will manage the hashmap of connections that track the gameID's
//it will broadcast out messages to everyone else in the game as needed
public class ConnectionHandler {
    public ConcurrentHashMap<Integer, ArrayList<ConnectionInfo>> sessions;

    public ConnectionHandler(){
        this.sessions = new ConcurrentHashMap<>();
    }

    public void sendError(Session session, String message){
        ErrorMessage errorMessage = new ErrorMessage(message);
        try{
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        } catch (IOException ex){
            System.out.print("Tried to send error message but failed");
        }
    }
}
