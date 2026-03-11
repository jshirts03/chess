package dataaccess;

import datatypes.GameData;
import service.requests.JoinGameRequest;

import java.util.ArrayList;

public class SQLGameDAO implements GameDAO{
    public SQLGameDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
    }
    public void clear(){};
    public int createGame(String gameName){return 1;};
    public ArrayList<GameData> getGames(){return new ArrayList<GameData>();};
    public void joinGame(JoinGameRequest request) throws DataAccessException{};

}
