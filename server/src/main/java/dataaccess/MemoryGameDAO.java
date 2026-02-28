package dataaccess;

import chess.ChessGame;
import datatypes.GameData;

import java.util.ArrayList;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    ArrayList<GameData> games;
    public MemoryGameDAO(){
        games = new ArrayList<GameData>();
    }

    public void clear(){
        games.clear();
    }

    public int createGame(String gameName){
        int gameId = (int)(Math.random() * 9000) + 1000;
        while (alreadyTaken(gameId)){
            gameId = (int)(Math.random() * 9000) + 1000;
        }
        games.add(new GameData(gameId, "", "",gameName, new ChessGame()));
        return gameId;
    }

    public boolean alreadyTaken(Integer gameId){
        for (GameData game: games){
            if (game.gameID() == gameId){
                return true;
            }
        }
        return false;
    }

    public ArrayList<GameData> getGames(){
        return games;
    }
}
