package dataaccess;

import datatypes.GameData;
import service.requests.JoinGameRequest;

import java.util.ArrayList;

public interface GameDAO {
    void clear();
    int createGame(String gameName);
    ArrayList<GameData> getGames();
    void joinGame(JoinGameRequest request) throws DataAccessException;
}
