import java.net.URI;
import java.net.http.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class OrderInfo {

    public static List<Integer> topKCustomers(int k) {
        Map<Integer, Double> totalSpent = new HashMap<>();

        int totalPages = 1;
        for (int i = 0; i < totalPages; i++) {
            String api = "https://test.hackerrank.com/api/orders?page=" + page;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONParser parser = new JSONParser();

            JSONObject json = (JSONObject) parser.parse(response.body());
            totalPages = ((Number) json.get("totalPages")).intValue();
            JSONArray data = (JSONArray) json.get("data");

            for (Object obj : data) {
                JSONObject order = (JSONObject) obj;
                // FILTER
                String status = (String) order.get("status");
                if (!"DELIEVERED".equals(status))
                    continue;
                int customerId = ((Long) order.get("customer_id")).intValue();
                double amount = ((Number) order.get("amount")).doubleValue();

                totalSpent.put(customerId, totalSpent.getOrDefault(customerId, 0.0) + amount);
            }
        }
        // SORT
        List<Integer> customers = new ArrayList<>(totalSpent.keySet());
        Collections.sort(customers, (a, b) -> {
            double spentA = totalSpent.get(a);
            double spentB = totalSpent.get(b);

            if (spentA != spentB) {
                return Double.compare(spentB, spentA);
            }
            return Integer.compare(a, b);
        });
        // TOP K
        return customers.subList(0, Math.min(k, list.size()));

    }catch(

    Exception e)
    {
        e.printStackTrace(); 
    }

    return new ArrayList<>();
}
