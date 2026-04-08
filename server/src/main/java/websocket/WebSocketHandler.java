package websocket;

import com.google.gson.Gson;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;
import websocket.commands.UserGameCommand;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    public ConcurrentHashMap<Integer, ArrayList<ConnectionInfo>> sessions;
    //need gameId, username, and connection
    //make a map from gameId to a connection info record class that I can create
    //then we can go into the gameId and send a message to each ctx that doesn't have the same username as me

    public WebSocketHandler(){
        this.sessions = new ConcurrentHashMap<>();
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
                handleConnection(gameCommand);
                break;
            case UserGameCommand.CommandType.MAKE_MOVE:
                MakeMoveCommand makeMoveCommand = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                handleMakeMove()
        }
    }

    @Override
    public void handleClose(@NotNull WsCloseContext ctx){
        //does nothing :)
        //remove message context from hashmap
    }

}

