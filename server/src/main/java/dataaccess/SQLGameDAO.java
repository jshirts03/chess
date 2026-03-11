package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import datatypes.GameData;
import datatypes.UserData;
import org.mindrot.jbcrypt.BCrypt;
import service.requests.JoinGameRequest;

import java.sql.*;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAO{
    public SQLGameDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
    }

    public void clear() throws DataAccessException{
        String statement = "DROP TABLE IF EXISTS chess.games";
        try{
            DatabaseManager.executeStatement(statement);
            DatabaseManager.createTables();
        } catch (DataAccessException e){
            throw new DataAccessException("Error: server error");
        }
    };

    public boolean idAlreadyTaken(int gameId){
        String statement = String.format("SELECT id FROM chess.games WHERE gameid = '%d'", gameId);
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    public int createGame(String gameName){
        int gameId = (int)(Math.random() * 9000) + 1000;
        while (idAlreadyTaken(gameId)){
            gameId = (int)(Math.random() * 9000) + 1000;
        }
        ChessGame game = new ChessGame();
        String gameJson = new Gson().toJson(game);
        String statement = "INSERT INTO chess.games (gameid, gamename, game) VALUES(?,?,?)";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, gameName);
            preparedStatement.setString(3, gameJson);
            preparedStatement.executeUpdate();
            return gameId;
        } catch (Exception e){
            return 0;
        }
    };

    public GameData formatGame(ResultSet rs) throws DataAccessException{
        try{
            int gameId = rs.getInt("gameid");
            String whiteUsername = rs.getString("whiteusername");
            String blackUsername = rs.getString("blackusername");
            String gameName = rs.getString("gamename");
            String gameJson = rs.getString("game");
            ChessGame game = new Gson().fromJson(gameJson, ChessGame.class);
            return new GameData(gameId, whiteUsername, blackUsername, gameName, game);
        } catch (SQLException e){
            throw new DataAccessException("failed to format game");
        }

    }

    public ArrayList<GameData> getGames() throws DataAccessException{
        ArrayList<GameData> gamesList = new ArrayList<GameData>();
        String statement = "SELECT * FROM chess.games";
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()){
                        GameData game = formatGame(rs);
                        gamesList.add(game);
                    }
                    return gamesList;
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: server error");
        }
    };

    public void joinGame(JoinGameRequest request) throws DataAccessException{
        String statement = String.format("SELECT * from chess.games WHERE gameid = %d", request.gameID());
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        GameData game = formatGame(rs);
                        insertJoinedPlayer(request, game);
                    }
                    else{
                        throw new DataAccessException("Error: bad request");
                    }
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("taken")){
                throw new DataAccessException("Error: already taken");
            }
            throw new DataAccessException("Error: service error");
        }

    };

    public void checkGameAlreadyTaken(JoinGameRequest request, GameData game) throws DataAccessException{
        if (request.playerColor().equals("WHITE") && game.whiteUsername() != null){
            throw new DataAccessException("Error: already taken");
        }
        if (request.playerColor().equals("BLACK") && game.blackUsername() != null){
            throw new DataAccessException("Error: already taken");
        }
    }

    public void insertJoinedPlayer(JoinGameRequest request, GameData game) throws DataAccessException{
        checkGameAlreadyTaken(request, game);
        String statement;
        if (request.playerColor().equals("WHITE")){
            statement = String.format("UPDATE chess.games SET whiteusername = '%s' WHERE gameid = '%d'"
            ,request.username()
            ,request.gameID());
            DatabaseManager.executeStatement(statement);
        }
        if (request.playerColor().equals("BLACK")){
            statement = String.format("UPDATE chess.games SET blackusername = '%s' WHERE gameid = '%d'"
                    ,request.username()
                    ,request.gameID());
            DatabaseManager.executeStatement(statement);
        }

    }




}
