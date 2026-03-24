package client;

import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

    public void registerJoe(){
        facade.register("joe@joe.com", "joe", "1234");
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

}
