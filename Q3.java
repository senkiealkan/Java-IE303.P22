
import java.util.Random;

import java.util.*;

public class RadioStationAlert {
    static class Point implements Comparable<Point> {
        int x, y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            return this.x == o.x ? Integer.compare(this.y, o.y) : Integer.compare(this.x, o.x);
        }

        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    // Hàm tính tích chéo giữa 3 điểm (p, q, r)
    private static int crossProduct(Point p, Point q, Point r) {
        return (q.x - p.x) * (r.y - p.y) - (q.y - p.y) * (r.x - p.x);
    }

    // Tìm bao lồi bằng thuật toán Graham Scan
    public static List<Point> convexHull(List<Point> points) {
        if (points.size() <= 1) return points;
        
        // Sắp xếp điểm theo x, nếu x bằng nhau thì theo y
        Collections.sort(points);
        
        List<Point> hull = new ArrayList<>();
        
        // Xây dựng phần dưới của bao lồi
        for (Point p : points) {
            while (hull.size() >= 2 && crossProduct(hull.get(hull.size() - 2), hull.get(hull.size() - 1), p) <= 0) {
                hull.remove(hull.size() - 1);
            }
            hull.add(p);
        }

        // Xây dựng phần trên của bao lồi
        int lowerSize = hull.size();
        for (int i = points.size() - 2; i >= 0; i--) {
            Point p = points.get(i);
            while (hull.size() > lowerSize && crossProduct(hull.get(hull.size() - 2), hull.get(hull.size() - 1), p) <= 0) {
                hull.remove(hull.size() - 1);
            }
            hull.add(p);
        }

        // Xóa phần tử cuối trùng với phần tử đầu
        if (hull.size() > 1) hull.remove(hull.size() - 1);

        return hull;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // Số lượng trạm
        List<Point> stations = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            stations.add(new Point(x, y));
        }
        scanner.close();

        List<Point> alertStations = convexHull(stations);

        // Xuất kết quả
        for (Point p : alertStations) {
            System.out.println(p);
        }
    }
}
