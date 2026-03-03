package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.requests.CreateGameRequest;
import service.requests.JoinGameRequest;
import service.requests.LoginRequest;
import service.requests.RegisterRequest;
import service.responses.*;

import static org.junit.jupiter.api.Assertions.*;

public class MyUnitTests {

    AuthService authService;
    GameService gameService;
    UserService userService;
    RegisterRequest testRegister;

    @BeforeEach
    public void setUpService(){
        authService = new AuthService();
        gameService = new GameService();
        userService = new UserService();
        testRegister = new RegisterRequest("Joe", "1234", "joe@joe.com");
    }

    public RegisterResponse registerUser() throws DataAccessException{
        return userService.register(testRegister);
    }

    @Test
    public void clearUser(){
        assertDoesNotThrow(() -> userService.clear());
    }

    @Test
    public void clearGame(){
        assertDoesNotThrow(() -> gameService.clear());
    }

    @Test
    public void clearAuth(){
        assertDoesNotThrow(() -> authService.clear());
    }

    @Test
    public void registerSuccess() throws DataAccessException{
        RegisterResponse registerRes = userService.register(testRegister);
        assertEquals("Joe", registerRes.username());
    }

    @Test
    public void registerFail(){
        RegisterRequest badRegister = new RegisterRequest("Joe", null, null);
        assertThrows(DataAccessException.class, () -> userService.register(badRegister));
    }

    @Test
    public void loginSuccess() throws DataAccessException{
        RegisterResponse registerRes = userService.register(testRegister);
        LoginResponse loginRes = userService.login(new LoginRequest("Joe", "1234"));
        assertEquals("Joe", loginRes.username());
        assertNull(loginRes.authToken());
    }

    @Test
    public void loginFail() throws DataAccessException{
        RegisterResponse registerRes = userService.register(testRegister);
        assertThrows(DataAccessException.class, () -> userService.login(new LoginRequest("Joe", "4675")));
    }

    @Test
    public void authorizeSuccess(){
        AuthResponse authRes = authService.authorize("Joe");
        assertEquals("Joe", authRes.username());
        assertNotNull(authRes.authToken());
    }

    public String authorize(){
        AuthResponse authRes = authService.authorize("Joe");
        return authRes.authToken();
    }

    @Test
    public void verifyAuthSuccess(){
        String authToken = authorize();
        assertDoesNotThrow(() -> authService.verifyAuth(authToken));
    }

    @Test
    public void verifyAuthFail(){
        assertThrows(DataAccessException.class, () -> authService.verifyAuth("12345"));
    }

    @Test
    public void getUserWithAuthSuccess(){
        String authToken = authorize();
        String username = authService.getUserWithAuth(authToken);
        assertEquals("Joe", username);
    }

    @Test
    public void getUserWithAuthFail(){
        String username = authService.getUserWithAuth("12345");
        assertNull(username);
    }

    @Test
    public void createGameSuccess() throws DataAccessException{
        NewGameResponse gameRes = gameService.createGame(new CreateGameRequest("Joe's Game"));
        assertTrue(999 < gameRes.gameID());
    }

    @Test
    public void createGameFail(){
        assertThrows(DataAccessException.class, () -> gameService.createGame(new CreateGameRequest(null)));
    }

    @Test
    public void listGamesSuccess() throws DataAccessException{
        NewGameResponse gameRes = gameService.createGame(new CreateGameRequest("Joe's Game"));
        ListGamesResponse listGameRes = gameService.listGames();
        assertEquals(1, listGameRes.games().length);
        assertEquals("Joe's Game", listGameRes.games()[0].gameName());
    }

    @Test
    public void joinGameSuccess() throws DataAccessException{
        NewGameResponse gameRes = gameService.createGame(new CreateGameRequest("Joe's Game"));
        int id = gameRes.gameID();
        assertDoesNotThrow(() -> gameService.joinGame(new JoinGameRequest("WHITE", id, "Joe")));
        ListGamesResponse listGameRes = gameService.listGames();
        assertEquals("Joe", listGameRes.games()[0].whiteUsername());
    }

    @Test
    public void joinGameFail(){
        assertThrows(DataAccessException.class, () -> gameService.joinGame(new JoinGameRequest("GREEN", 0000, null)));
        assertThrows(DataAccessException.class, () -> gameService.joinGame(new JoinGameRequest("BLACK", 23456, "Joe")));
    }



}
