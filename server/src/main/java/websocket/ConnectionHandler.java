package websocket;


import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


//this class will manage the hashmap of connections that track the gameID's
//it will broadcast out messages to everyone else in the game as needed
public class ConnectionHandler {
    public ConcurrentHashMap<Integer, ArrayList<ConnectionInfo>> sessions;
    public AuthDAO authDAO;
    public GameDAO gameDAO;

    public ConnectionHandler(){
        this.sessions = new ConcurrentHashMap<>();
        try{
            this.authDAO = new SQLAuthDAO();
            this.gameDAO = new SQLGameDAO();
        } catch (DataAccessException e){
            this.authDAO = new MemoryAuthDAO();
            this.gameDAO = new MemoryGameDAO();
        }

    }

    public void sendError(Session session, String message){
        ErrorMessage errorMessage = new ErrorMessage(message);
        try{
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        } catch (IOException ex){
            System.out.print("Tried to send error message but failed");
        }
    }

    public void addConnection(Session session, UserGameCommand gameCommand) throws DataAccessException{
        int gameId = gameCommand.getGameID();
        String username = authDAO.getUserWithAuth(gameCommand.getAuthToken());
        ChessGame.TeamColor teamColor = gameDAO.getTeamColor(gameId, username);
        ConnectionInfo info = new ConnectionInfo(session, username, teamColor);
        if (sessions.containsKey(gameId)){
            sessions.get(gameId).add(info);
        }
        else{
            sessions.put(gameId, new ArrayList<ConnectionInfo>());
            sessions.get(gameId).add(info);
        }
        //tries to get the username with authToken
        //gets the user's teamColor with the gameId // check for valid gameID

        //creates a Connection Info object with those 3 values, session, username, and teamColor
        //adds to the HashSet
        //notifies everyone that a player has joined

    }
}
