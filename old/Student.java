import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Student {
    public static void main(String[] args) throws Exception {

        String json =
            "{\"students\":[{\"name\":\"A\"},{\"name\":\"B\"},{\"name\":\"C\"}]}";

        JSONParser parser = new JSONParser();

        JSONObject root = (JSONObject) parser.parse(json);
        JSONArray students = (JSONArray) root.get("students");

        for (Object obj : students) {
            JSONObject student = (JSONObject) obj;
            System.out.println(student.get("name"));
        }
    }
}