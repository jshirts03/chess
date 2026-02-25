package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.*;
import io.javalin.http.Context;
import service.AuthService;
import service.UserService;
import service.GameService;
import service.responses.RegisterResponse;
import service.requests.RegisterRequest;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final GameService gameService;
    private final UserService userService;
    private final AuthService authService;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        gameService = new GameService();
        userService = new UserService();
        authService = new AuthService();

        // Register your endpoints and exception handlers here
        javalin.delete("/db", this::clearApplication);
        javalin.post("/user", this::registerUser);

    }


    private void clearApplication(Context ctx) {
        ctx.contentType("application/json");
        try{
            gameService.clear();
            userService.clear();
            authService.clear();
            ctx.status(200);
            ctx.result();
        }
        catch (DataAccessException exception){
            ctx.status(500);
            ctx.json(new Gson().toJson(exception));
        }
    }

    private void registerUser(Context ctx){
        ctx.contentType("application/json");
        try{
            RegisterResponse response = userService.register(new Gson().fromJson(ctx.body(), RegisterRequest.class));
            ctx.status(200);
            ctx.json(new Gson().toJson(response));
        }
        catch (DataAccessException exception){
            String message = exception.getMessage();
            if (message.contains("bad")){
                ctx.status(400);
            }
            if (message.contains("taken")){
                ctx.status(403);
            }
            else{
                ctx.status(500);
            }
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
