package dataaccess;

import datatypes.UserData;

public class SQLUserDAO extends UserDAO{

    public SQLUserDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
    }

    public void clear(){};
    public UserData getUser(String username){};
    public void createUser(UserData user){};
}
