import java.util.*; 

public class LcmGcd {

    static int LCM(int a, int b) {

        // Larger value 
        int g = Math.max(a, b); 

        // Smaller value 
        int s = Math.min(a, b); 

        for (int i = g; i <= a * b; i += g) {
            if (i % s == 0) {
                return i; 
            }
        }

        return a * b; 
    }

    static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);  
    }
}
