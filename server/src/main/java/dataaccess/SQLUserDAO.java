package dataaccess;

import datatypes.UserData;

public class SQLUserDAO implements UserDAO {

    public SQLUserDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
    }

    public void clear(){};
    public UserData getUser(String username){return new UserData("joe", "12345", "hi@gmail.com");};
    public void createUser(UserData user){};
}
