import java.util.HashMap;

public class Solution {
    // TODO: Implement your solution here
    public static int solve(int[] arr) {
        int maxLength = -1;
        int start = 0;
        HashMap<Integer, Integer> hashTable = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (hashTable.get(arr[i]) == null) {
                hashTable.put(arr[i], i);
            } else {
                maxLength = Math.max(maxLength, i - start);
                start = start > hashTable.get(arr[i]) ? start : hashTable.get(arr[i]) + 1;
                hashTable.replace(arr[i], i);
            }
        }
        maxLength = Math.max(maxLength, arr.length - start);
        return maxLength;
    }
}