import java.util.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class bestDepartment {

    public static String bestDepartment() {
        // Storing department to Salary
        Map<String, Long> totalSalary = new HashMap<>();
        String answer = "";
        long maxSalary = -1;

        try {
            int totalPages = 1;
            for (int page = 1; page <= totalPages; page++) {
                String url = "https://hackerrank.com/api/test/emplyees?page=" + page;

                // API CALL
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // PARSING JSON
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response);

                totalPages = ((Long) json.get("total_pages")).intValue();
                JSONArray data = (JSONArray) json.get("data");

                for (Object obj : data) {
                    JSONObject emp = (JSONObject) obj;
                    String dept = (String) emp.get("department");
                    long salary = ((Long) emp.get("salary"));
                    // Adding salary to deparmtne total
                    // This problem is all about finding department wwise total salary to pay from
                    // the company
                    totalSalary.put(dept, totalSalary.getOrDefault(dept, 0L) + salary);
                }
            }
            // -------------------- FIND MAX -----------------------
            for (Map.Entry<String, Long> entry : totalSalary.entrySet()) {
                String dept = entry.getKey();
                long salary = entry.getValue();

                if (salary > maxSalary || (salary == maxSalary && dept.compareTo(answer) < 0)) {
                    maxSalary = salary;
                    answer = dept;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return answer;
    }
}
