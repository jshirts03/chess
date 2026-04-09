package dataaccess;

import chess.ChessGame;
import datatypes.GameData;
import service.requests.JoinGameRequest;

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
        games.add(new GameData(gameId, null, null,gameName, new ChessGame()));
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

    public void joinGame(JoinGameRequest request) throws DataAccessException{
        for (GameData game: games){
            if(game.gameID() == request.gameID()){
                insertJoinedPlayer(request, game);
                games.remove(game);
                return;
            }
        }
        throw new DataAccessException("Error: bad request");

    }

    public void insertJoinedPlayer(JoinGameRequest request, GameData game) throws DataAccessException {
        checkGameAlreadyTaken(request, game);
        if (request.playerColor().equals("WHITE")){
            GameData joinedGame = new GameData(game.gameID(), request.username(), game.blackUsername(), game.gameName(), game.game());
            games.add(joinedGame);
        }
        if (request.playerColor().equals("BLACK")){
            GameData joinedGame = new GameData(game.gameID(), game.whiteUsername(), request.username() , game.gameName(), game.game());
            games.add(joinedGame);
        }
    }

    public ChessGame getGameWithId(int id) throws DataAccessException{
        for (GameData game : games){
            if (game.gameID() == id){
                return game.game();
            }
        }
        throw new DataAccessException("Error: invalid game ID");
    }

    public ChessGame.TeamColor getTeamColor(int id, String username) throws DataAccessException{
        for (GameData game : games){
            if (game.gameID() == id){
                if (game.whiteUsername().equals(username)){
                    return ChessGame.TeamColor.WHITE;
                }
                if (game.blackUsername().equals(username)){
                    return ChessGame.TeamColor.BLACK;
                }
                return null;
            }
        }
        throw new DataAccessException("Error: invalid game ID");
    }

    public void leaveGame(int id, ChessGame.TeamColor teamColor){
        for (GameData game : games){
            if (game.gameID() == id){
                if (teamColor == ChessGame.TeamColor.WHITE){
                    games.add(new GameData(game.gameID(), null, game.blackUsername(), game.gameName(), game.game()));
                }
                else{
                    games.add(new GameData(game.gameID(), game.whiteUsername(), null, game.gameName(), game.game()));
                }
                games.remove(game);
            }
        }
    }

    public void updateGame(int id, ChessGame chessGame){
        for (GameData game : games){
            if (game.gameID() == id){
                games.add(new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame));
                games.remove(game);
            }
        }
    }
}
