import java.util.HashMap;

public class Solution {
    public static int solve(int[] arr, int target) {
        int length = arr.length;
        int count = 0;
        if (length <= 1) {
            return count;
        } else {
            HashMap<Integer, Integer> hashTable = new HashMap<>();

            for (int i = 0; i < length; i++) {
                int curr = arr[i];
                int diff = target - curr;

                if (!hashTable.containsKey(diff)) {
                    if (hashTable.containsKey(curr)) {
                        int noOfTimes = hashTable.get(curr) + 1;
                        hashTable.put(curr, noOfTimes);
                    } else {
                        hashTable.put(curr, 1);
                    }
                } else {
                    count++;
                    int noOfIterations = hashTable.get(diff);
                    if (noOfIterations > 1) {
                        hashTable.put(diff, noOfIterations - 1);
                    } else {
                        hashTable.remove(diff);
                    }
                }
            }
            return count;
        }
    }
}
