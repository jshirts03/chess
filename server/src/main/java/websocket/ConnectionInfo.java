package websocket;

import chess.ChessGame;
import io.javalin.websocket.WsMessageContext;

public record ConnectionInfo(WsMessageContext ctx, String username, ChessGame.TeamColor teamColor) {
}
// ctx.session() might be the thing, so you can call getRemote.sendString