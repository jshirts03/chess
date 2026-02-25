package dataaccess;


import datatypes.UserData;
import java.util.ArrayList;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{
    private HashSet<UserData> users;

    public MemoryUserDAO(){
        users = new HashSet<UserData>();
    }

    public void clear(){
        users.clear();
    }

    public UserData getUser(String username){
        for (UserData user : users){
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void createUser(UserData data){
        users.add(data);
    }
    //insert methods here that manipulate the list of users stored in this DAO object
    //because there no DB, we just create one of these when the server starts up and then it will reset
}
