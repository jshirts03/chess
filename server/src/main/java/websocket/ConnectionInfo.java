package websocket;

import chess.ChessGame;
import io.javalin.websocket.WsMessageContext;
import org.eclipse.jetty.websocket.api.Session;

public record ConnectionInfo(Session session, String username, ChessGame.TeamColor teamColor) {
}
// ctx.session() might be the thing, so you can call getRemote.sendString