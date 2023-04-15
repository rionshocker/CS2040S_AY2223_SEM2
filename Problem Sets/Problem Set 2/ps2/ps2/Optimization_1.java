/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization_1 {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        int start = 0;
        int end = dataArray.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;
            if ((mid == 0 || dataArray[mid] > dataArray[mid - 1]) && (mid == end || dataArray[mid] > dataArray[mid + 1])) {
                start = mid;
                end = mid;
            } else if (dataArray[start] > dataArray[start + 1]) {
                if (dataArray[start] > dataArray[end]) {
                    end = start;
                } else {
                    start = end;
                }
            } else if (dataArray[mid] < dataArray[mid - 1]) {
                end = mid - 1;
            } else if (end == 1 || start == end - 1) {
                if (dataArray[start] > dataArray[end]) {
                    end = start;
                } else {
                    start = end;
                }
            } else {
                start = mid + 1;
            }
        }
        if (dataArray.length == 0) {
            return 0;
        } else {
            return dataArray[start];
        }
    } 

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}