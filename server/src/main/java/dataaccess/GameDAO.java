package dataaccess;

import chess.ChessGame;
import datatypes.GameData;
import service.requests.JoinGameRequest;

import java.util.ArrayList;

public interface GameDAO {
    void clear() throws DataAccessException;
    int createGame(String gameName) throws DataAccessException;
    ArrayList<GameData> getGames() throws DataAccessException;
    void joinGame(JoinGameRequest request) throws DataAccessException;
    ChessGame getGameWithId(int gameId) throws DataAccessException;
    ChessGame.TeamColor getTeamColor(int gameId, String username) throws DataAccessException;

    default void checkGameAlreadyTaken(JoinGameRequest request, GameData game) throws DataAccessException{
        if (request.playerColor().equals("WHITE") && game.whiteUsername() != null){
            throw new DataAccessException("Error: already taken");
        }
        if (request.playerColor().equals("BLACK") && game.blackUsername() != null){
            throw new DataAccessException("Error: already taken");
        }
    }
}
