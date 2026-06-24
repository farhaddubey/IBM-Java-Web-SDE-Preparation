import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

import netscape.javascript.JSException;

public class Solution {

    public static List<String> getMovieTitles(String title) {

        List<String> result = new ArrayList<>();

        try {
            String url = "https://jsonmock.hackerank.com/api/movies/search/?Title=" + title;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // I missed this line so was gettiing NUll
            String body = response.body();

            JSONObject json = new JSONObject(body);

            JSONArray data = (JSONArray) json.get("data");
            for (JSONObject obj : data) {
                JSONObject movieTitle = (JSONObject) obj;
                String title = (String) movieTitle.get("Title");
                result.add(title);
            }

            Collections.sort(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
