package dataaccess;

import datatypes.GameData;

import java.util.HashSet;

public interface GameDAO {
    void clear();
    int createGame(String gameName);
    HashSet<GameData> getGames();
}
