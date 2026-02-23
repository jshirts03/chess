package server;

import dataaccess.DataAccessException;
import io.javalin.*;
import io.javalin.http.Context;
import service.LoginService;
import service.GameService;

public class Server {

    private final Javalin javalin;
    private GameService gameService;
    private LoginService loginService;
    private AuthService authService;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        try {
            javalin.delete("/db", this::clearApplication);
        }
        catch (Exception){

        }

    }


    private void clearApplication(Context ctx) throws DataAccessException {
        ctx.contentType("application/json");
        ctx.result();
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
