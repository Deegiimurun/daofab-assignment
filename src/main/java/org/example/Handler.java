package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        Map<String, String> queryMap = queryToMap(t.getRequestURI().getQuery());
        JSONArray result = new JSONArray();

        int page = queryMap.containsKey("page") ? Integer.parseInt(queryMap.get("page")) : 1;

        for (int i = 0; i < 2; i++) {
           result.put(Main.parents.get((page - 1) * 2 + i).toJson());
        }

        t.sendResponseHeaders(200, result.toString().length());
        OutputStream os = t.getResponseBody();
        os.write(result.toString().getBytes());
        os.close();
    }

    public Map<String, String> queryToMap(String query) {
        if(query == null) {
            return new HashMap<>();
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
