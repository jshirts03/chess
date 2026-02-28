package dataaccess;

import datatypes.AuthData;
import java.util.HashSet;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
    HashSet<AuthData> authSet;
    public MemoryAuthDAO(){
        authSet = new HashSet<AuthData>();
    }

    public void clear(){
        authSet.clear();
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
    public AuthData createAuth(String username){
        String token = generateToken();
        AuthData newAuth = new AuthData(token, username);
        authSet.add(newAuth);
        return newAuth;
    }

    public void deleteAuth(String authToken) throws DataAccessException{
        AuthData data = verifyAuth(authToken);
        authSet.remove(data);
    }

    public AuthData verifyAuth(String authToken) throws DataAccessException{
        for (AuthData data : authSet){
            if (data.authToken().equals(authToken)){
                return data;
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }
}
