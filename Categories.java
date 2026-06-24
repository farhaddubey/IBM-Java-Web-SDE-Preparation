import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Categories {

    // category -> total price
    Map<String, Double> total = new HashMap<>();
    Map<String, Integer> count = new HashMap<>();

    try
    {
        int totalPages = 0;

        for (int page = 1; page <= totalPages; page++) {
            String url = "https://jsonmock.hackerrank.com/api/inventory?page=" + page;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.body());
            totalPages = ((Long) json.get("total_pages")).intValue();
            JSONArray data = (JSONArray) json.get("data");

            for (Object obj : data) {
                JSONObject item = (JSONObject) obj;
                double rating = ((Number) item.get("rating")).doubleValue();

                if (rating < threshold)
                    continue;

                String category = (String) item.get("category");
                double price = ((Number) item.get("price")).doubleValue();

                total.put(category, total.getOrDefault(category, 0.0) + price);
                count.put(category, count.getOrDefault(category, 0) + 1);
            }
        }

        // Now building list for sorting
        List<String> categories = new ArrayList<>(total.keySet());

        Collections.sort(categories, (a, b) -> {
            double avgA = total.get(a) / count.get(a);
            double avgB = total.get(b) / count.get(b);

            if (avgA != avgB) {
                return Double.compare(avgB, avgA); // DSEC
            }

            return a.compareTo(b);
        });
        return categories.subList(0, Math.min(k, categories.size()));

    }catch(
    Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
}
