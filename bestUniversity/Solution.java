package bestUniversity;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;

import java.nio.charset.StandardCharsets;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Solution {

    public static String bestUniversity(String countryName) {
        try {
            // -----------------------------------------------
            // URL ENCODING
            // -----------------------------------------------
            String url = "https://jsonmock.hackerank.com/api/countries?name=" +
                    URLEncoder.encode(countryName, StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(response.body());

            // -----------------------------------------------
            // DATA ARRAY
            // -----------------------------------------------
            JSONArray data = (JSONArray) root.get("data");
            if (data == null || data.isEmpty()) {
                return "-1";
            }

            double bestRating = -1;
            long bestRank = Long.MAX_VALUE;
            String answer = "-1";

            // ................................................
            // country objects
            // ................................................
            for (Object countryObj : data) {
                JSONObject country = (JSONObject) countryObj;
                JSONArray universities = (JSONArray) country.get("universities");
                if (universities == null) {
                    continue;
                }

                // --------------------------------------------
                // university objects
                // ............................................

                for (Object uniObj : universities) {
                    JSONObject university = (JSONObject) uniObj;
                    String name = university.get("name").toString().trim();
                    double rating = ((Number) university.get("rating")).doubleValue();
                    long rank = ((Number) university.get("rank")).longValue();
                }

                // .............................................
                // BETTER RATING
                // .............................................
                if (rating > bestRating) {
                    bestRating = rating;
                    bestRank = rank;
                    answer = name;
                }

                // SAME RATING
                else if (rating == bestRating) {
                    // LOWER RANK WINS
                    if (rank < bestRank) {
                        bestRank = rank;
                        answer = name;
                    }
                    // SAME RANK
                    else if (rank == bestRank && name.compareTo(answer) < 0) {
                        answer = name;
                    }
                }
            }
            return answer;
        } catch (Error e) {
            e.printStackTrace();
            return "-1";
        }
    }
}
