public class FindKeysLowestCost implements IFindKeys {
    @Override
    public int[] findKeys(int N, int k, ITreasureExtractor treasureExtractor) {
        // TODO: Problem 2 -- Implement strategy to find correct keys with minimum cost (least number of keys used in total across all attempts)
        int[] listOfKeys = new int[N];
        int mostRightKey = N;
        int numOfKeysLeft = k;

        while (numOfKeysLeft > 0) {
            int start = 0;
            int end = mostRightKey - 1;

            while (start < end) {
                int mid = start + (end - start) / 2;
                for (int i = 0; i <= end; i++) {
                    listOfKeys[i] = 1;
                }
                for (int i = mid + 1; i <= end; i++) {
                    listOfKeys[i] = 0;
                }

                if (treasureExtractor.tryUnlockChest(listOfKeys)) {
                    end = mid;
                } else {
                    start = mid + 1;
                }
            }
            listOfKeys[start] = 1;
            mostRightKey = start;
            numOfKeysLeft--;
        }
        for (int i = 0; i < mostRightKey; i++) {
            listOfKeys[i] = 0;
        }
        return listOfKeys;
    }
}
