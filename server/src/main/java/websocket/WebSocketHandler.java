package websocket;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.SQLAuthDAO;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    public ConnectionHandler connectionHandler;
    private AuthDAO authDAO;
    //need gameId, username, and connection
    //make a map from gameId to a connection info record class that I can create
    //then we can go into the gameId and send a message to each ctx that doesn't have the same username as me

    public WebSocketHandler(){
        this.connectionHandler = new ConnectionHandler();
        try{
            this.authDAO = new SQLAuthDAO();
        } catch (DataAccessException e){
            this.authDAO = new MemoryAuthDAO();
        }

    }

    @Override
    public void handleConnect(@NotNull WsConnectContext ctx){
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext ctx){
        UserGameCommand gameCommand = new Gson().fromJson(ctx.message(), UserGameCommand.class);
        switch (gameCommand.getCommandType()){
            case UserGameCommand.CommandType.CONNECT:
                handleConnection(ctx, gameCommand);
                break;
            case UserGameCommand.CommandType.MAKE_MOVE:
                MakeMoveCommand makeMoveCommand = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                handleMakeMove(makeMoveCommand);
                break;
            case UserGameCommand.CommandType.RESIGN:
                handleResign(gameCommand);
                break;
            case UserGameCommand.CommandType.LEAVE:
                handleLeave(gameCommand);
                break;
            default:
                ErrorMessage message = new ErrorMessage("Error: invalid command type");
                try{
                    ctx.session.getRemote().sendString(new Gson().toJson(message));
                } catch (IOException e){
                    System.out.print("error");
                }

        }
    }

    @Override
    public void handleClose(@NotNull WsCloseContext ctx){
        //does nothing :)
        //remove message context from hashmap
    }

    public void handleConnect(WsMessageContext ctx, UserGameCommand gameCommand){
        try{
            authDAO.verifyAuth(gameCommand.getAuthToken());
        } catch (DataAccessException e){
            connectionHandler.sendError(ctx.session, "Error: unauthorized");
        }
        //if there is an error
        //call connection handler to send error message to the root user

        //else
        //call connection handler to add person to connections
            //called internally
            //calls connection handler to notify everyone else in the game that someone joined
    }

}

