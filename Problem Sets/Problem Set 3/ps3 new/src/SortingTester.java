public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            int randomKey = (int) (Math.random() * size);
            testArray[i] = new KeyValuePair(randomKey, i);
        }
        sorter.sort(testArray);

        for (int i = 0; i < size - 1; i++) {
            KeyValuePair curr = testArray[i];
            KeyValuePair next = testArray[i+1];
            if (curr.compareTo(next) > 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];
            for (int i = 0; i < size / 2; i++) {
                testArray[i] = new KeyValuePair(i, i);
                testArray[size - i - 1] = new KeyValuePair(i, size - i - 1);
            }

            sorter.sort(testArray);
            for (int i = 0; i < size - 1; i++) {
                KeyValuePair curr = testArray[i];
                KeyValuePair next = testArray[i + 1];

                if (curr.compareTo(next) == 0) {
                    if (curr.getValue() > next.getValue()) {
                        return false;
                    }
                }
            }
            return true;
    }

    public static long findBestCase(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(i, i);
        }
        return sorter.sort(testArray);
    }

    public static long findWorstCase(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(size - i - 1, i);
        }

        return sorter.sort(testArray);
    }

    public static long diffCase(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];

        for (int i = 0; i < size - 1; i++) {
            testArray[i] = new KeyValuePair(i + 1, i + 1);
        }
        testArray[size - 1] = new KeyValuePair(0, 0);
        return sorter.sort(testArray); 
    }

    public static void main(String[] args) {
        // To test SorterA.class
        System.out.println("For A:"); //Time complexity around the same: MergeSort
        ISort A = new SorterA();
        System.out.println("Is it a sorting algorithm? : " + checkSort(A, 10000));
        System.out.println("Is it stable? : " + isStable(A, 10000));
        System.out.println("Best case cost? : " + findBestCase(A, 1000));
        System.out.println("Worst case cost? : " + findWorstCase(A, 1000));

        System.out.println("For B:"); //Dr. Evil //only one with false for first criteria
        ISort B = new SorterB();
        System.out.println("Is it a sorting algorithm? : " + checkSort(B, 10000));
        System.out.println("Is it stable? : " + isStable(B, 10000));
        System.out.println("Best case cost? : " + findBestCase(B, 1000));
        System.out.println("Worst case cost? : " + findWorstCase(B, 1000));

        System.out.println("For C:"); //BubbleSort
        ISort C = new SorterC();
        System.out.println("Is it a sorting algorithm? : " + checkSort(C, 10000));
        System.out.println("Is it stable? : " + isStable(C, 10000));
        System.out.println("Best case cost? : " + findBestCase(C, 1000));
        System.out.println("Worst case cost? : " + findWorstCase(C, 1000));

        System.out.println("For D:"); //SelectionSort
        ISort D = new SorterD();
        System.out.println("Is it a sorting algorithm? : " + checkSort(D, 10000));
        System.out.println("Is it stable? : " + isStable(D, 10000));
        System.out.println("Best case cost? : " + findBestCase(D, 1000));
        System.out.println("Worst case cost? : " + findWorstCase(D, 1000));

        System.out.println("For E:"); //not stable? aka QuickSort
        ISort E = new SorterE();
        System.out.println("Is it a sorting algorithm? : " + checkSort(E, 10000));
        System.out.println("Is it stable? : " + isStable(E, 10000));
        System.out.println("Best case cost? : " + findBestCase(E, 1000));
        System.out.println("Worst case cost? : " + findWorstCase(E, 1000));

        System.out.println("For F:"); //InsertionSort
        ISort F = new SorterF();
        System.out.println("Is it a sorting algorithm? : " + checkSort(F, 10000));
        System.out.println("Is it stable? : " + isStable(F, 10000));
        System.out.println("Best case cost? : " + findBestCase(F, 1000));
        System.out.println("Worst case cost? : " + findWorstCase(F, 1000));   
        
        //To differentiate BubbleSort and Insertion Sort
        // System.out.println("Differentiate case cost? : " + diffCase(C, 1));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 20));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 40));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 60));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 80));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 100));
        // System.out.println("Differentiate case cost? : " + diffCase(C,200));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 300));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 400));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 500));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 600));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 700));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 800));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 900));
        // System.out.println("Differentiate case cost? : " + diffCase(C, 1000));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 1));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 20));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 40));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 60));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 80));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 100));
        // System.out.println("Differentiate case cost? : " + diffCase(F,200));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 300));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 400));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 500));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 600));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 700));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 800));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 900));
        // System.out.println("Differentiate case cost? : " + diffCase(F, 1000));

        //To differentiate QuickSort and SelectionSort
        // System.out.println("Best case cost? : " + findBestCase(D, 2000));
        // System.out.println("Best case cost? : " + findBestCase(D, 3000));
        // System.out.println("Best case cost? : " + findBestCase(D, 4000));
        // System.out.println("Best case cost? : " + findBestCase(D, 5000));
        // System.out.println("Worst case cost? : " + findWorstCase(D, 2000));
        // System.out.println("Worst case cost? : " + findWorstCase(D, 3000));
        // System.out.println("Worst case cost? : " + findWorstCase(D, 4000));
        // System.out.println("Worst case cost? : " + findWorstCase(D, 5000));
        // System.out.println("Best case cost? : " + findBestCase(E, 2000));
        // System.out.println("Best case cost? : " + findBestCase(E, 3000));
        // System.out.println("Best case cost? : " + findBestCase(E, 4000));
        // System.out.println("Best case cost? : " + findBestCase(E, 5000));
        // System.out.println("Worst case cost? : " + findWorstCase(E, 2000));
        // System.out.println("Worst case cost? : " + findWorstCase(E, 3000));
        // System.out.println("Worst case cost? : " + findWorstCase(E, 4000));
        // System.out.println("Worst case cost? : " + findWorstCase(E, 5000));
    }
}
