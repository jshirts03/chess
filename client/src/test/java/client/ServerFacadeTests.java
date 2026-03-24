package client;

import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

    public void registerJoe(){
        facade.register("joe@joe.com", "joe", "1234");
    }

    @Test
    public void registerSuccess() {
        var authData = facade.register("joe@joe.com", "joe", "1234");
        assertEquals("joe", authData.username());
    }

}
