
import java.util.Random;

public class MonteCarloPi {
    public static double approximatePi(int iterations) {
        Random rand = new Random();
        int insideCircle = 0;

        for (int i = 0; i < iterations; i++) {
            double x = 2 * rand.nextDouble() - 1; // Tọa độ x từ -1 đến 1
            double y = 2 * rand.nextDouble() - 1; // Tọa độ y từ -1 đến 1

            if (x * x + y * y <= 1) {
                insideCircle++;
            }
        }

        return (double) insideCircle / iterations * 4;
    }

    public static void main(String[] args) {
        int iterations = 1000000; // Số lần thử
        double estimatedPi = approximatePi(iterations);
        System.out.println("Giá trị xấp xỉ của pi: " + estimatedPi);
    }
}
