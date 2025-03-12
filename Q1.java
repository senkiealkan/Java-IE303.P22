
import java.util.Random;

public class CircleAreaApproximation {

    public static double approximateCircleArea(double r, int iterations) {
        Random rand = new Random();
        int insideCircle = 0;

        for (int i = 0; i < iterations; i++) {
            double x = (2 * r) * rand.nextDouble() - r; // Tọa độ x từ -r đến r
            double y = (2 * r) * rand.nextDouble() - r; // Tọa độ y từ -r đến r

            if (x * x + y * y <= r * r) {
                insideCircle++;
            }
        }

        return (double) insideCircle / iterations * (4 * r * r);
    }

    public static void main(String[] args) {
        double r = 5; // Bán kính
        int iterations = 1000000; // Số lần thử

        double estimatedArea = approximateCircleArea(r, iterations);
        System.out.println("Xấp xỉ diện tích hình tròn bán kính " + r + " là: " + estimatedArea);
    }
}
