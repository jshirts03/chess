package websocket;

import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    @Override
    public void handleConnect(@NotNull WsConnectContext ctx){

    }

    @Override
    public void handleMessage(@NotNull WsMessageContext ctx){

    }

    @Override
    public void handleClose(@NotNull WsCloseContext ctx){

    }

}

