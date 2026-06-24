// RETURN TOP K CATEGORIES BY : 
1. FILTER->RATING>=THRESHOLD 2. GROUP->CATEGORY 3. AGGREGATE->AVG PRICE PER CATEGORY 4. SORT->HIGER AVG PRICE FIRST IF TIE->LEXICOGRAPHICALLY SMALLER CATEGORY 5. RETURN TOP K CATEGORIES

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class ProductInventory {

    // CATEGORY -> TOTAL PRICE
    Map<String, Double> total = new HashMap<>();

    // CATEGORY -> COUNT
    Map<String, Integer> count = new HashMap<>();

    try
    {
        int totalPages = 1;

        // PAGINATION
        for (int page = 1; page <= totalPages; page++) {
            String url = "https://mock.hackerrank.com/api/inventory?page=" + page;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.ofString());

            // PARSE
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.body());
            totalPages = ((Long) json.get("total_pages")).intValue();
            JSONArray data = (JSONArray) json.get("data");

            for (Object obj : data) {
                JSONObject item = (JSONObject) obj;
                double rating = ((Number) item.get("rating")).doubleValue();
                // FILTER
                if (rating < threshold)
                    continue;
                String category = (String) item.get("category");
                double price = ((Number) item.get("price")).doubleValue();

                // GROUPING + AGGREGATION
                total.put(category, total.getOrDefault(category, 0.0) + price);
                count.put(category, count.getOrDefault(category, 0) + 1);
            }
        }
        // now building list for sorting
        List<String> categories = new ArrayList<>(total.keySet());
        List<String> categories = new ArrayList<>(total.keySet());
        List<String> categories = new ArrayList<>(total.keySet());

        // SORT
        Collections.sort(categories, (a, b) -> {
            double avgA = total.get(a) / count.get(a);
            double avgB = total.get(b) / count.get(b);

            if (avgA != avgB) {
                return Double.compare(avgB, avgA); // DESC
            }
            return a.compareTo(b); // TIE - BREAK
        });
        // ------------------------------- TOP K -------------------------------
        return categories.subList(0, Math.min(k, categories.size()));
    }catch(
    Exception ex)
    {
        ex.printStackTrace(); 
    }return new ArrayList<>();

}
