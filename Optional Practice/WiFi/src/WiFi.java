import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        Arrays.sort(houses);
        double start = 0.0;
        double end = houses[houses.length - 1];

        while (end - start > 0.01) {
            double mid = start + (end - start) / 2;
            if (coverable(houses, numOfAccessPoints, mid)) {
                end = mid;
            } else {
                start = mid + 0.01;
            }
        }
        return start;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        Arrays.sort(houses);
        int count = 1;
        if (houses.length == 0) {
            count = 0;
        } else {
            double location = houses[0] + distance;
            for (int i = 1; i < houses.length; i++) {
                if (houses[i] - location > distance || -houses[i] + location > distance) {
                    location = houses[i] + distance;
                    count++;
                }
            }
        }
        return count <= numOfAccessPoints;
    }
}
