package dataaccess;

import datatypes.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{
    public SQLAuthDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
    }

    public void clear() throws DataAccessException{
        String statement = "DROP TABLE IF EXISTS auth";
        try{
            DatabaseManager.executeStatement(statement);
            DatabaseManager.createTables();
        } catch (DataAccessException e){
            throw new DataAccessException("Error: server error");
        }
    };

    public AuthData createAuth(String username) throws DataAccessException{
        String token = UUID.randomUUID().toString();
        String statement = "INSERT INTO auth VALUES(null, '" + username + "', '" + token + "')";
        DatabaseManager.executeStatement(statement);
        return new AuthData(token, username);
    };

    public void deleteAuth(String authToken) throws DataAccessException{
        verifyAuth(authToken);
        String statement = String.format("DELETE FROM auth WHERE authtoken = '%s'", authToken);
        DatabaseManager.executeStatement(statement);
    };


    public AuthData verifyAuth(String authToken) throws DataAccessException{
        String statement = String.format("SELECT username, authtoken FROM auth WHERE authtoken = '%s'", authToken);
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        String username = rs.getString("username");
                        String dbToken = rs.getString("authtoken");
                        return new AuthData(dbToken, username);
                    }
                    else{
                        throw new DataAccessException("Error: unauthorized");
                    }

                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: server error");
        }
    };

    public String getUserWithAuth(String authToken){
        try{
            AuthData user = verifyAuth(authToken);
            return user.username();
        } catch (DataAccessException e){
            return null;
        }
    };

}
