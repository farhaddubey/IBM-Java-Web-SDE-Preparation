import java.util.*;

public class TakeInput {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();

        String[] parts = s.split(",");

        long x = sc.nextLong();
        double x = sc.nextDouble();

        for (String st : parts) {
            System.out.println(st);
        }
    }
}
