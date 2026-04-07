package client;

import com.google.gson.Gson;
import jakarta.websocket.*;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
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
            URI socketURI = new URI("ws://localhost:8080/ws");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    switch (notification.getServerMessageType()){
                        case ServerMessage.ServerMessageType.LOAD_GAME:
                           var specializedNotification = new Gson().fromJson(message, LoadGameMessage.class);
                           break;
                        case ServerMessage.ServerMessageType.ERROR:
                            var specializedNotification = new Gson().fromJson(message, ErrorMessage.class);
                            break;
                        case ServerMessage.ServerMessageType.NOTIFICATION:
                            var specializedNotification = new Gson().fromJson(message, NotificationMessage.class);
                    }
                    notificationHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    public void connectToGame(String authToken, int gameID){
        try {
            var connectReq = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(connectReq));
        } catch (IOException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }


}
