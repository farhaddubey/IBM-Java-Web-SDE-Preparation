import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class OrderAnalyticsAPI {

    public static List<Integer> topKCustomers(int k) {

        // custommer -> total spend
        Map<Integer, Double> totalSpent = new HashMap<>();

        try {

            int totalPages = 1;

            for (int page = 1; page <= totalPages; page++) {
                String url = "https://jsonmock.hackerrank.com/api/orders/?page=" + page;
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // SO by HTTP call we get the response
                // Now we need PARSER to parse the response body into JSON Object
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response.body());

                totalPages = ((Long) json.get("total_pages")).intValue();
                JSONArray data = (JSONArray) json.get("data");

                for (Object obj : data) {
                    JSONObject order = (JSONObject) obj;

                    // FILTERING
                    String status = (String) order.get("status");
                    if (!"DELIVERED".equals(status))
                        continue;
                    int customerId = ((Long) order.get("customer_id")).intValue();
                    double amount = ((Number) order.get("amount")).doubleValue();

                    // GROUPING + AGGREGATE
                    totalSpent.put(customerId, totalSpent.getOrDefault(customerId, 0.0) + amount);
                }
            }
            // AFTER THE FOR LOOP IS ENDED --
            // ------------ SORTING ---------
            List<Integer> customers = new ArrayList<>(totalSpent.keySet());
            Collections.sort(customers, (a, b) -> {

                double spentA = totalSpent.get(a);
                double spentB = totalSpent.get(b);

                if (spentA != spentB) {
                    return Double.compare(spentB, spentA);
                }

                return Integer.compare(a, b);
            });

            // RETURNING TOP K
            return customers.subList(0, Math.min(k, customers.size()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
