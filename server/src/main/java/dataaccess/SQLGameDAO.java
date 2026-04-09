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
        String statement = "DROP TABLE IF EXISTS games";
        try{
            DatabaseManager.executeStatement(statement);
            DatabaseManager.createTables();
        } catch (DataAccessException e){
            throw new DataAccessException("Error: server error");
        }
    };

    boolean idAlreadyTaken(int gameId){
        String statement = String.format("SELECT id FROM games WHERE gameid = '%d'", gameId);
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

    public int createGame(String gameName) throws DataAccessException{
        int gameId = (int)(Math.random() * 9000) + 1000;
        while (idAlreadyTaken(gameId)){
            gameId = (int)(Math.random() * 9000) + 1000;
        }
        ChessGame game = new ChessGame();
        String gameJson = new Gson().toJson(game);
        String statement = "INSERT INTO games (gameid, gamename, game) VALUES(?,?,?)";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, gameName);
            preparedStatement.setString(3, gameJson);
            preparedStatement.executeUpdate();
            return gameId;
        } catch (Exception e){
            throw new DataAccessException("Error: server error");
        }
    };

    GameData formatGame(ResultSet rs) throws DataAccessException{
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
        String statement = "SELECT * FROM games";
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
        String statement = String.format("SELECT * from games WHERE gameid = %d", request.gameID());
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
            if (e.getMessage().contains("bad")){
                throw new DataAccessException("Error: bad request");
            }
            throw new DataAccessException("Error: service error");
        }

    };

    public void updateGame(int id, ChessGame game) throws DataAccessException{
        String gameJson = new Gson().toJson(game);
        String statement = "UPDATE games SET game = ? WHERE gameid = ?";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.setString(1, gameJson);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            throw new DataAccessException("Error: server error");
        }
    }

    public void leaveGame(int id, ChessGame.TeamColor teamColor) throws DataAccessException{
        String statement;
        if (teamColor == ChessGame.TeamColor.WHITE){
            statement = String.format("UPDATE games SET whiteusername = NULL WHERE gameid = '%d'" ,id);
        }
        else{
            statement = String.format("UPDATE games SET blackusername = NULL WHERE gameid = '%d'" ,id);
        }
        DatabaseManager.executeStatement(statement);

    }


    void insertJoinedPlayer(JoinGameRequest request, GameData game) throws DataAccessException{
        checkGameAlreadyTaken(request, game);
        String statement;
        if (request.playerColor().equals("WHITE")){
            statement = String.format("UPDATE games SET whiteusername = '%s' WHERE gameid = '%d'"
            ,request.username()
            ,request.gameID());
            DatabaseManager.executeStatement(statement);
        }
        if (request.playerColor().equals("BLACK")){
            statement = String.format("UPDATE games SET blackusername = '%s' WHERE gameid = '%d'"
                    ,request.username()
                    ,request.gameID());
            DatabaseManager.executeStatement(statement);
        }

    }

    public ChessGame getGameWithId(int id) throws DataAccessException{
        String statement = String.format("SELECT * from games WHERE gameid = %d", id);
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        String gameString = rs.getString("game");
                        return new Gson().fromJson(gameString, ChessGame.class);

                    }
                    throw new DataAccessException("Error: invalid game id");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: server error");
        }
    }

    public ChessGame.TeamColor getTeamColor(int id, String username) throws DataAccessException{
        String statement = String.format("SELECT * from games WHERE gameid = %d", id);
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        String whiteUser = rs.getString("whiteusername");
                        String blackUser = rs.getString("blackusername");
                        return evaluateUsername(whiteUser, blackUser, username);

                    }
                    throw new DataAccessException("Error: invalid game id");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: server error");
        }
    }

    private ChessGame.TeamColor evaluateUsername(String whiteUser, String blackUser, String username){
        if (whiteUser != null){
            if (whiteUser.equals(username)){
                return ChessGame.TeamColor.WHITE;
            }
        }
        if (blackUser != null){
            if (blackUser.equals(username)){
                return ChessGame.TeamColor.BLACK;
            }
        }
        return null;
    }




}
