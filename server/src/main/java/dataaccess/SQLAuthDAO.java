package dataaccess;

import datatypes.AuthData;

import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{
    public SQLAuthDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
    }

    public void clear(){
        String statement = "DROP TABLE IF EXISTS chess.auth";
        try{
            DatabaseManager.executeStatement(statement);
            DatabaseManager.createTables();
        } catch (DataAccessException e){
        }
    };

    public AuthData createAuth(String username){
        String token = UUID.randomUUID().toString();
        String statement = "INSERT INTO chess.auth VALUES(null, '" + username + "', '" + token + "')";
        try{
            DatabaseManager.executeStatement(statement);
            return new AuthData(token, username);
        } catch (DataAccessException e){
            return null;
        }
    };


    ;
    public void deleteAuth(String authToken) throws DataAccessException{};
    public AuthData verifyAuth(String authToken) throws DataAccessException{ return new AuthData("hello", "jimmy");};
    public String getUserWithAuth(String authToken){return "12345";};

}
