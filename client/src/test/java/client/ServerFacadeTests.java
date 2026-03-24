package client;

import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
    public void clearDb() {
        facade.clear();
    }

    public String registerJoe() {
        var authData = facade.register("joe@joe.com", "joe", "1234");
        return authData.authToken();
    }

    @Test
    public void registerSuccess() {
        var authData = facade.register("joe@joe.com", "joe", "1234");
        assertEquals("joe", authData.username());
    }

    @Test
    public void registerFail() {
        facade.register("joe@joe.com", "joe", "1234");
        var authData = facade.register("joe@joe.com", "joe", "1234");
        assertTrue(authData.message().contains("Error"));
    }

    @Test
    public void loginSuccess() {
        registerJoe();
        var authData = facade.login("joe", "1234");
        assertTrue(authData.authToken().length() > 10);
        assertEquals("joe", authData.username());
    }

    @Test
    public void loginFail() {
        var authData = facade.login("NotJoe", "password");
        assertTrue(authData.message().contains("Error"));
    }

    @Test
    public void createGameSuccess() {
        String authToken = registerJoe();
        var gameRes = facade.createGame("Joe's game", authToken);
        assertNull(gameRes.message());
    }

    @Test
    public void createGameFail() {
        var gameRes = facade.createGame("Joe's bad game", "1234");
        assertTrue(gameRes.message().contains("Error"));
    }

    @Test
    public void listGamesSuccess() {
        String authToken = registerJoe();
        var gameRes = facade.createGame("Joe's game", authToken);
        String games = facade.listGames(authToken);
        assertTrue(games.contains("Joe's game"));
    }

    @Test
    public void listGamesFail() {
        String games = facade.listGames("1234");
        assertTrue(games.contains("Error"));
    }

    @Test
    public void joinGameSuccess() {
        String authToken = registerJoe();
        facade.createGame("COOL GAME", authToken);
        var joinRes = facade.joinGame("1", "WHITE", authToken);
        assertNull(joinRes);
        String games = facade.listGames(authToken);
        assertTrue(games.contains("joe"));
    }

    @Test
    public void joinGameFail() {
        String authToken = registerJoe();
        facade.createGame("COOL GAME", authToken);
        var joinRes = facade.joinGame("1", "WHITE", "1234");
        assertTrue(joinRes.message().contains("Error"));
        joinRes = facade.joinGame("3", "WHITE", authToken);
        assertTrue(joinRes.message().contains("Error"));
        joinRes = facade.joinGame("1", "GREEN", authToken);
        assertTrue(joinRes.message().contains("Error"));
    }


}
