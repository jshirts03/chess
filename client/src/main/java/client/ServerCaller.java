package client;

//The server caller will actually make the endpoint requests with an httpServer
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class ServerCaller {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerCaller(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public HttpRequest prepareRequest(String path, String method, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null){
            request.setHeader("Content-Type", "application/json");
        }
        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request){
        if (request != null){
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    public HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException{
        try{
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException("Error: there was a problem connecting to the server");
        }
    }



}
