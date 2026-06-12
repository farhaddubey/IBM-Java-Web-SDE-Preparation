import org.json.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.junit.jupiter.api.Test; 

import java.net.URI; 
import java.net.http.HttpClient; 
import java.net.http.HttpRequest; 
import java.net.http.HttpResponse; 

public class CountGenre {
    public static int countGenre(String genre) {
        int count = 0; 
        
        try {
            int totalPages = 1; 
            for (int page = 1; page <= totalPages; page++) {
                String url = "https://jsonmock.hackerrank.com/api/tvseries?page=" + page; 
                HttpClient client = HttpClient.newHttpClient(); 
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build(); 
                HttpResponse response = client.send(request, HttpResponse.BodyHandler.ofString()); 

                JSONParser parser = new JSONParser(); 
                JSONObject json = (JSONObject) parser.parse(response); 

                totalPages = ((Long) json.get("total_pages")).intValue(); 

                JSONArray data = (JSONArray) json.get("data"); 
                for (Object obj : data) {
                    JSONObject show = (JSONObject) obj; 
                    String genres = (String) show.get("genres"); 
                    if (genres.contains(genre)) {
                        count++; 
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;  
    }
}
