import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import netscape.javascript.JSException;

public class topCategories {
    public static List<String> topCategories() {
        Map<String, Integer> stockMap = new HashMap<>();

        try {
            int totalPages = 1;

            for (int page = 1; page <= totalPages; page++) {
                String url = "https://jsonmock.hackerrank.com/api/products?page=" + page;

                // API CALL
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.ofString());

                // PARSING
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response.body());

                totalPages = ((Long) json.get("total_pages")).intValue();
                JSONArray data = (JSONArray) json.get("data");

                for (Object obj : data) {
                    JSONObject product = (JSONObject) obj;
                    int stock = ((Long) product.get("stock")).intValue();
                    // FILTERING
                    if (stock < 10) {
                        continue;
                    }
                    String category = (String) product.get("category");

                    stockMap.put(category, stockMap.getOrDefault(category, 0) + stock);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // SORTING
        List<Map.Entry<String, Integer>> list = new ArrayList<>(stockMap.entrySet());
        Collections.sort(list, (a, b) -> {
            // Higher stock 1st
            if (b.getValue() != a.getValue()) {
                return b.getValue() - a.getValue();
            }
            // Tie - Break
            return a.getKey().compareTo(b.getKey());
        });

        // TOP - K
        List<String> result = new ArrayList<>();
        int k = Math.min(2, list.size());

        for (int i = 0; i < k; i++) {
            result.add(list.get(i).getKey());
        }

        return result;
    }
}
