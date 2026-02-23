package dataaccess;

import datatypes.GameData;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    HashSet<GameData> games;
    public MemoryGameDAO(){
        games = new HashSet<GameData>();
    }

    public void clear(){
        games.clear();
    }
}
