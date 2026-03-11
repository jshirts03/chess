package dataaccess;

import datatypes.GameData;
import service.requests.JoinGameRequest;

import java.util.ArrayList;

public interface GameDAO {
    void clear() throws DataAccessException;
    int createGame(String gameName);
    ArrayList<GameData> getGames() throws DataAccessException;
    void joinGame(JoinGameRequest request) throws DataAccessException;
}
