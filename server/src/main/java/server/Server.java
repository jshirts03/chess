package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.*;
import io.javalin.http.Context;
import service.handlers.ClearHandler;

public class Server {

    private final Javalin javalin;

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
        ClearResponse response = new ClearHandler(ctx).run();
        ctx.contentType("application/json");
        ctx.result(new Gson().toJson(response));
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
