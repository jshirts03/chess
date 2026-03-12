package dataaccess;

import datatypes.AuthData;
import datatypes.GameData;
import datatypes.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.requests.JoinGameRequest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DBUnitTests {
    SQLAuthDAO authdb;
    SQLGameDAO gamedb;
    SQLUserDAO userdb;
    UserData testUser;

    @BeforeEach
    public void setupDB(){
        try {
            authdb = new SQLAuthDAO();
            gamedb = new SQLGameDAO();
            userdb = new SQLUserDAO();
            testUser = new UserData("joe", "1234", "joe@joe.com");
        } catch(DataAccessException e){
            System.out.println("Uh oh, looks like the DB is down");
        }
    }

    @AfterEach
    public void clearDB(){
        try{
            authdb.clear();
            userdb.clear();
            gamedb.clear();
        } catch (DataAccessException e){
            System.out.println("Uh oh, could not clear the DB after test");
        }

    }

    @Test
    public void clearUserDb(){
        assertDoesNotThrow(() -> userdb.clear());
    }

    @Test
    public void clearAuthDb(){
        assertDoesNotThrow(() -> authdb.clear());
    }

    @Test
    public void clearGameDb(){
        assertDoesNotThrow(() -> gamedb.clear());
    }

    @Test
    public void createUser(){
        assertDoesNotThrow(() -> userdb.createUser(testUser));
    }

    @Test
    public void getUserSuccess(){
        try{
            userdb.createUser(testUser);
            UserData user = userdb.getUser("joe", "1234");
            assertEquals(user.username(), testUser.username());
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void getUserFail(){
        try{
            UserData user = userdb.getUser("joe", "1234");
            assertNull(user);
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void createAuthSuccess(){
        try{
            AuthData auth = authdb.createAuth("joe");
            assertNotNull(auth.authToken());
            assertEquals("joe", auth.username());
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }


    @Test
    public void verifyAuthSuccess(){
        try{
            AuthData auth = authdb.createAuth("joe");
            AuthData verified = authdb.verifyAuth(auth.authToken());
            assertEquals(auth.username(), verified.username());
            assertEquals(auth.authToken(), verified.authToken());
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void verifyAuthFail(){
        assertThrows(DataAccessException.class, () -> authdb.verifyAuth("1234"));
    }

    @Test
    public void getUserWithAuthSuccess(){
        try{
            AuthData auth = authdb.createAuth("joe");
            String username = authdb.getUserWithAuth(auth.authToken());
            assertEquals("joe", username);
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void getUserWithAuthFail(){
        String username = authdb.getUserWithAuth("1234");
        assertNull(username);
    }

    @Test
    public void deleteAuthSuccess(){
        try{
            AuthData auth = authdb.createAuth("joe");
            assertDoesNotThrow(() -> authdb.deleteAuth(auth.authToken()));
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void deleteAuthFail(){
        assertThrows(DataAccessException.class, () -> authdb.deleteAuth("1234"));
    }

    @Test
    public void createGameSuccess(){
        try{
            int gameid = gamedb.createGame("JoeGame");
            assertNotNull(gameid);
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void createGameFail(){
        assertThrows(DataAccessException.class, () -> gamedb.createGame(null));
    }

    @Test
    public void getGamesSuccess(){
        try{
            gamedb.createGame("JoeGame");
            gamedb.createGame("JoeGame2");
            gamedb.createGame("JoeGame3");
            ArrayList<GameData> games = gamedb.getGames();
            assertEquals("JoeGame",games.get(0).gameName());
            assertEquals("JoeGame2",games.get(1).gameName());
            assertEquals("JoeGame3",games.get(2).gameName());
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void getGamesNoGames(){
        try{
            ArrayList<GameData> games = gamedb.getGames();
            assertEquals(0, games.size());
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void joinGameSuccess(){
        try{
            int gameId = gamedb.createGame("JoeGame");
            JoinGameRequest req = new JoinGameRequest("WHITE", gameId, "Joe");
            gamedb.joinGame(req);
            GameData game = gamedb.getGames().get(0);
            assertEquals("Joe", game.whiteUsername());
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }

    @Test
    public void joinGameAlreadyTaken(){
        try{
            int gameId = gamedb.createGame("JoeGame");
            JoinGameRequest req = new JoinGameRequest("WHITE", gameId, "Joe");
            gamedb.joinGame(req);
            assertThrows(DataAccessException.class, () -> gamedb.joinGame(req));
        }
        catch (DataAccessException e){
            fail("There was a problem with DB");
        }
    }


}
