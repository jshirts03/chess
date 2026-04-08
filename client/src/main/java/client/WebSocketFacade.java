package client;

import com.google.gson.Gson;
import jakarta.websocket.*;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint{
    NotificationHandler notificationHandler;
    Session session;


    public WebSocketFacade(NotificationHandler nf) throws ResponseException{
        try{
            notificationHandler = nf;
            URI socketURI = new URI("ws://localhost:8080/ws"); //take in hostname and port as parameter

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()){
                        case ServerMessage.ServerMessageType.LOAD_GAME:
                            var loadGameMessage = new Gson().fromJson(message, LoadGameMessage.class);
                            notificationHandler.loadGame(loadGameMessage);
                            break;
                        case ServerMessage.ServerMessageType.ERROR:
                            var errorMessage = new Gson().fromJson(message, ErrorMessage.class);
                            notificationHandler.printError(errorMessage);
                            break;
                        case ServerMessage.ServerMessageType.NOTIFICATION:
                            var notification = new Gson().fromJson(message, NotificationMessage.class);
                            notificationHandler.notify(notification);
                            break;
                    }

                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    public void connectToGame(String authToken, int gameID) throws ResponseException{
        try {
            var connectReq = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(connectReq));
        } catch (IOException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }


}
