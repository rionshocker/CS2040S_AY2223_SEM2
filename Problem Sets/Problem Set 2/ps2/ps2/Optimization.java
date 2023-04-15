/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

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
        //Running time: O(logn)
        int start = 0; //variable to signify the start of search
        int end = dataArray.length - 1; //variable to signify the end of search of array
        //Take into account if the array is an empty one
        if (end == -1) {
            return 0;
        } else if (end == 0) {
            return dataArray[start];
        //In the case when there are only 2 elements in the array or to compare between each other
        } else if (end == 1) {
            if (dataArray[start] > dataArray[end]) {
                return dataArray[start];
            } else {
                return dataArray[end];
            }
        //This helps to search if there is a decreasing trend or if the trend is a v-shape
        } else if (dataArray[start] > dataArray[start + 1]) {
            if (dataArray[start] > dataArray[end]) {
                return dataArray[start];
            } else {
                return dataArray[end];
            }
        //Else if the trend is an increasing trend or a n-shape trend
        } else {
            while (start < end) {
                int mid = start + (end - start) / 2; //variable to aid in binary search
                //When there are only 2 elements left to compare after searching either half
                if (start == end - 1) {
                    if (dataArray[start] < dataArray[end]) {
                        return dataArray[end];
                    } else {
                        return dataArray[start];
                    }
                //In the case when it is a n-shape trend and the mid
                //is the pivot point, being the maximum
                } else if (dataArray[mid] > dataArray[mid - 1] 
                            && dataArray[mid] > dataArray[mid + 1]) {
                    return dataArray[mid];
                //This is the case in which it is a strictly decreasing trend
                } else if (dataArray[mid] < dataArray[mid - 1]) {
                    end = mid - 1;
                //Otherwise, the maximum will be on the right side    
                } else {
                    start = mid + 1;
                }
            }
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
