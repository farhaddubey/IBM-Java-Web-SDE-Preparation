import java.util.*;
import java.net.URI;
import java.net.http.*;

public class bestMovie {
    public static String bestMovie(String title) {
        // Best answer storage 
        String bestMovie = ""; 
        
        // Lowest initial rating 
        double maxRating = -1; 

        try {
            // Assuming 1st page 
            int totalPages = 1; 
            // PAGINATION PATTERN 
            for (int page = 1; page <= totalPages; page++) {
                // Building URL dynamically 
                String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + title + "&page=" + page; 

                // API CALL PATTERN 
                HttpClient client = HttpClient.newHttpClient(); 
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build(); 
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); 

                // JSON PARSING 
                JSONParser parser = new JSONParser(); 
                JSONObject json = (JSONObject) parser.parse(response.body()); 

                // UPDATING TOTAL PAGES 
                totalPages = ((Long) json.get("total_pages")).intValue(); 
                JSONArray data = (JSONArray) json.get("data"); 

                // PROCESSING THE MOVIE 
                for (Object obj : data) {
                    JSONObject movie =  (JSONObject) obj; 
                    // Extracting fields 
                    String movieName = (String) movie.get("Title"); 
                    double rating = Double.parseInt((String) movie.get("imdb_rating")); 
                    // MAX + TIE BREAK PATTERN 
                    if (rating > maxRating || (rating == maxRating && movieName.compareTo(bestMovie) < 0)) {
                        maxRating = rating; 
                        bestMovie = movieName; 
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bestMovie; 
    }
}
