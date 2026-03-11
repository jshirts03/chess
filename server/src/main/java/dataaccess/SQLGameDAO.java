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

    public void clear(){
        String statement = "DROP TABLE IF EXISTS chess.games";
        try{
            DatabaseManager.executeStatement(statement);
            DatabaseManager.createTables();
        } catch (DataAccessException e){
        }
    };

    public boolean alreadyTaken(int gameId){
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
        while (alreadyTaken(gameId)){
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

    public ArrayList<GameData> getGames(){return new ArrayList<GameData>();};
    public void joinGame(JoinGameRequest request) throws DataAccessException{};

}
