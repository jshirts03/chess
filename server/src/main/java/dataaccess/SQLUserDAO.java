package dataaccess;

import datatypes.UserData;
import org.mindrot.jbcrypt.BCrypt;

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

    public UserData getUser(String username){return null;};


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
