package websocket;


import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;

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

    public void sendLoadGame(Session session, int gameId){
        try {
            ChessGame game = gameDAO.getGameWithId(gameId);
            LoadGameMessage message = new LoadGameMessage(game);
            session.getRemote().sendString(new Gson().toJson(message));
        } catch (DataAccessException | IOException e) {
            sendError(session, e.getMessage());
        }
    }

    public void sendNotification(Session excluded, String message, int gameId){
        ArrayList<ConnectionInfo> connections = sessions.get(gameId);
        try{
            for (ConnectionInfo info : connections){
                Session c = info.session();
                if (c.isOpen()){
                    if (!c.equals(excluded)){
                        c.getRemote().sendString(message);
                    }
                }
            }
        } catch (IOException e){
            System.out.print("Tried to broadcast message but an error ocurred");
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

        String notification = String.format("%s joined the game.", username);
        sendNotification(session, notification, gameId);
        sendLoadGame(session, gameId);

    }
}
