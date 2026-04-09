package websocket;


import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.*;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

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

    public void sendOneLoadGame(Session session, int gameId, ChessGame.TeamColor teamColor){
        try {
            ChessGame game = gameDAO.getGameWithId(gameId);
            LoadGameMessage message = new LoadGameMessage(game, teamColor);
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
                        NotificationMessage notification = new NotificationMessage(message);
                        c.getRemote().sendString(new Gson().toJson(notification));
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
        sendOneLoadGame(session, gameId, teamColor);
    }

    public void removeConnection(Session session, UserGameCommand gameCommand) throws DataAccessException{
        int gameId = gameCommand.getGameID();
        var gameInfo = sessions.get(gameId);
        for (ConnectionInfo info : gameInfo) {
            if (info.session().equals(session)) {
                gameDAO.leaveGame(gameId, info.teamColor());
                String notification = String.format("%s left the game.", info.username());
                sendNotification(session, notification, gameId);
                gameInfo.remove(info);
                return;
            }
        }
        throw new DataAccessException("Error: player has already left game");
    }

    //this will get the game from the DB with the game ID
    //get the player's team color from the DB
    //if it can't, then the person is an observer, error
    //check if it's the person's turn
    // make move should throw and exception
    public void makeMove(Session session, MakeMoveCommand makeMoveCommand){
        int gameId = makeMoveCommand.getGameID();
        ChessMove move = makeMoveCommand.getMove();
        try{
            ChessGame game = gameDAO.getGameWithId(gameId);
            String username = authDAO.getUserWithAuth(makeMoveCommand.getAuthToken());
            ChessGame.TeamColor teamColor = gameDAO.getTeamColor(gameId, username);
            if (game.getTeamTurn() != teamColor){
                sendError(session, "Error: it is your opponent's turn");
            }
            if (game.getGameIsOver()){
                sendError(session, "Error: game has ended");
            }
            //if the person reaches here, then it is their turn, so makeMove method should handle
            //trying to move opponent piece or a nonexisting piece
            game.makeMove(move);
            gameDAO.updateGame(game);
            send

            //notify users

        } catch (DataAccessException | InvalidMoveException e){
            sendError(session, e.getMessage());
        }

    }
}
