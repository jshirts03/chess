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

    public void clear(){
        String statement = "DROP TABLE IF EXISTS chess.users";
        try{
            DatabaseManager.executeStatement(statement);
            DatabaseManager.createTables();
        } catch (DataAccessException e){
        }
    };

    public UserData getUser(String username, String password){
        String statement = String.format("SELECT username, password, email FROM chess.users WHERE username = '%s'", username);
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        String dbUsername = rs.getString("username");
                        String dbPassword = rs.getString("password");
                        String email = rs.getString("email");
                        if (BCrypt.checkpw(password, dbPassword)){
                            return new UserData(dbUsername, password, email);
                        }
                        else{
                            return null;
                        }
                    }
                    else{
                        return null;
                    }

                }
            }
        } catch (Exception e) {
            return null;
        }
    };


    public void createUser(UserData user){
        String statement = String.format("INSERT INTO chess.users VALUES (null, '%s', '%s',", user.username(), user.email());
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        statement = statement + " '" + hashedPassword + "')";
        try{
            DatabaseManager.executeStatement(statement);
        } catch (DataAccessException e){
        }
    };
}
