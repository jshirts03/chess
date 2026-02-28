package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import datatypes.GameData;
import io.javalin.*;
import io.javalin.http.Context;
import service.AuthService;
import service.UserService;
import service.GameService;
import service.requests.CreateGameRequest;
import service.requests.LoginRequest;
import service.responses.*;
import service.requests.RegisterRequest;

import java.util.ArrayList;
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
        javalin.post("/session", this::loginUser);
        javalin.delete("/session", this::logoutUser);
        javalin.post("/game", this::createGame);
        javalin.get("/game", this::listGames);
        javalin.exception(Exception.class, this::generalExceptionHandler);
        javalin.error(404, this::notFound);
    }


    private void clearApplication(Context ctx) {
        ctx.contentType("application/json");
        gameService.clear();
        userService.clear();
        authService.clear();
        ctx.status(200);
        ctx.result();
    }

    private void registerUser(Context ctx){
        try{
            RegisterResponse registerRes = userService.register(new Gson().fromJson(ctx.body(), RegisterRequest.class));
            AuthResponse authRes = authService.authorize(registerRes.username());
            successResponse(ctx, new Gson().toJson(authRes));
        }
        catch (DataAccessException exception){
            specificExceptionHandler(ctx, exception.getMessage());
        }
    }

    public void loginUser(Context ctx){
        try{
            LoginResponse loginRes = userService.login(new Gson().fromJson(ctx.body(), LoginRequest.class));
            AuthResponse authRes = authService.authorize(loginRes.username());
            successResponse(ctx, new Gson().toJson(authRes));
        }
        catch (DataAccessException exception){
            specificExceptionHandler(ctx, exception.getMessage());
        }
    }

    public void logoutUser(Context ctx){
        try{
            authService.deleteAuth(ctx.header("authorization"));
            successResponse(ctx, "");
        }
        catch (DataAccessException exception){
            specificExceptionHandler(ctx, exception.getMessage());
        }
    }

    public void createGame(Context ctx){
        try{
            authService.verifyAuth(ctx.header("authorization"));
            NewGameResponse newGameRes = gameService.createGame(new Gson().fromJson(ctx.body(), CreateGameRequest.class));
            successResponse(ctx, new Gson().toJson(newGameRes));
        }
        catch (DataAccessException exception){
            specificExceptionHandler(ctx, exception.getMessage());
        }
    }

    public void listGames(Context ctx){
        try{
            authService.verifyAuth(ctx.header("authorization"));
            ListGamesResponse listGamesRes = gameService.listGames();
            successResponse(ctx, new Gson().toJson(Map.of("games", listGamesRes)));
        }
        catch (DataAccessException exception){
            specificExceptionHandler(ctx, exception.getMessage());
        }
    }


    public void successResponse(Context ctx, String message){
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.json(message);
    }

    public void specificExceptionHandler(Context ctx, String message){
        ctx.contentType("application/json");
        if (message.contains("bad")){
            ctx.status(400);
        }
        if (message.contains("unauthorized")){
            ctx.status(401);
        }
        else{
            ctx.status(403);
        }
        ctx.json(new Gson().toJson(Map.of("message", message)));
    }

    public void generalExceptionHandler(Exception e, Context context){
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        context.status(500);
        context.json(body);
    }

    private void notFound(Context context) {
        String msg = String.format("[%s] %s not found", context.method(), context.path());
        generalExceptionHandler(new Exception(msg), context);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
