import java.net.URI; 
import java.net.http.HttpClient; 
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

import java.util.Map;
import java.util.HashMap; 

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 

public class CountEmployee {

    public static Map<String, Integer> countEmployeesDepartmentWise() {
        Map<String, Integer> departmentCount = new HashMap<>(); 
        
        try {
            int totalPages = 1; 
            HttpClient client = HttpClient.newHttpClient(); 

            for (int page = 1; page <= totalPages; page++) {
                // Now we call the API URL 
                String url = "https://jsonmock.hackerrank.com/api/employees?page=" + page; 
                // REQUEST 
                HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(url)).build(); 
                // RESPONSE 
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.ofString()); 

                // PARSING JSON 
                JSONParser parser = new JSONParser(); 
                JOSNObject json = (JSONObject) parser.parse(response.body()); 

                // TOTAL PAGES 
                totalPages = ((Long) json.get("total_pages")).intValue(); 

                // DATA ARRAY 
                JSONArray data = (JSONArray) json.get("data"); 

                // LOOPING EMPLOYEES 
                for (Object obj : data) {
                    JSONObject employee = (JSONObject) obj; 
                    String department = (String) employee.get("department"); 
                    // Frequency Map Logic 
                    departmentCount.put(department, departmentCount.getOrDefault(department, 0) + 1); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departmentCount; 
    }

    public static void main(String[] args) {
        Map<String, Integer> answer = countEmployeesDepartmentWise(); 
        for (String department : answer.keySet()) {
            System.out.println(department + "-->" + answer.get(department)); 
        }
    }
}
