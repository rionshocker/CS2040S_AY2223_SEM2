class InversionCounter {

    public static long mergeSort(int[] arr, int[] sortedArr, int start, int end) {
        if (start >= end) {
            return 0;
        } else {
            long numOfSwaps = 0;
            int mid = start + (end - start) / 2;
            numOfSwaps += mergeSort(arr, sortedArr, start, mid);
            numOfSwaps += mergeSort(arr, sortedArr, mid + 1, end);
            numOfSwaps += merge(arr, sortedArr, start, mid, mid + 1, end);
            return numOfSwaps;
        }
    }

    public static long countSwaps(int[] arr) {
        return mergeSort(arr, new int[arr.length], 0, arr.length - 1);
    }

    public static long merge(int[] arr, int[] sortedArr, int left1, int right1, int left2, int right2) {
        int start = left1;
        int curr = start;
        long numOfSwaps = 0;

        while (left1 <= right1 && left2 <= right2) {
            if (arr[left1] <= arr[left2]) {
                sortedArr[curr] = arr[left1];
                left1++;
            } else {
                sortedArr[curr] = arr[left2];
                numOfSwaps += (right1 - (left1 - 1));
                left2++;
            }
            curr++;
        }

        while (left1 <= right1) {
            sortedArr[curr] = arr[left1];
            left1++;
            curr++;
        }

        while (left2 <= right2) {
            sortedArr[curr] = arr[left2];
            left2++;
            curr++;
        }

        for (int i = start; i <= right2; i++) {
            arr[i] = sortedArr[i];
        }
        return numOfSwaps;
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        return merge(arr, new int[arr.length], left1, right1, left2, right2);
    }
}
