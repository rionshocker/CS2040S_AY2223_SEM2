class Sorter {

    public static void sortStrings(String[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int curr = i;
            while (curr >= 1 && isGreaterThan(arr[curr - 1], arr[curr])) {
                String temp = arr[curr - 1];
                arr[curr - 1] = arr[curr];
                arr[curr] = temp;
                curr--;
            }
        }

    }

    public static boolean isGreaterThan(String str1, String str2) {
        char firstChar1 = str1.charAt(0);
        char firstChar2 = str2.charAt(0);

        if (Character.compare(firstChar1, firstChar2) == 0) {
            return Character.compare(str1.charAt(1), str2.charAt(1)) > 0;
        } else {
            return Character.compare(firstChar1, firstChar2) > 0;
        }
    }
}
