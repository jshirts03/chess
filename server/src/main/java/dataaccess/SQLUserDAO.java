package dataaccess;

import datatypes.AuthData;
import datatypes.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLUserDAO implements UserDAO {

    public SQLUserDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
    }

    public void clear() throws DataAccessException{
        String statement = "DROP TABLE IF EXISTS users";
        try{
            DatabaseManager.executeStatement(statement);
            DatabaseManager.createTables();
        } catch (DataAccessException e){
            throw new DataAccessException("Error: server error");
        }
    };

    public UserData checkPassword(String dbUsername, String password, String email, String dbPassword){
        if (BCrypt.checkpw(password, dbPassword)){
            return new UserData(dbUsername, password, email);
        }
        else{
            return null;
        }
    }


    public UserData getUser(String username, String password) throws DataAccessException{
        String statement = String.format("SELECT username, password, email FROM users WHERE username = '%s'",
                username);
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        String dbUsername = rs.getString("username");
                        String dbPassword = rs.getString("password");
                        String email = rs.getString("email");
                        return checkPassword(dbUsername, password, email, dbPassword);
                    }
                    else{
                        return null;
                    }

                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: server error");
        }
    };


    public void createUser(UserData user) throws DataAccessException{
        String statement = String.format("INSERT INTO users VALUES (null, '%s', '%s',",
                user.username(),
                user.email());
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        statement = statement + " '" + hashedPassword + "')";
        DatabaseManager.executeStatement(statement);
    };
}
