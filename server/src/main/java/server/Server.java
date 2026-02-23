package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.*;
import io.javalin.http.Context;
import service.AuthService;
import service.UserService;
import service.GameService;

public class Server {

    private final Javalin javalin;
    private GameService gameService;
    private UserService userService;
    private AuthService authService;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        gameService = new GameService();
        // Register your endpoints and exception handlers here
        javalin.delete("/db", this::clearApplication);

    }


    private void clearApplication(Context ctx) {
        try{
            gameService.clear();
            ctx.contentType("application/json");
            ctx.status(200);
            ctx.result();
        }
        catch (DataAccessException exception){
            ctx.status(500);
            ctx.json(new Gson().toJson(exception));
        }
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
