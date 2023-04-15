public class Guessing {

    // Your local variables here
    private int low = 0;
    private int high = 1000;
    private int mid;

    /**
     * Implement how your algorithm should make a guess here
     */
    public int guess() {
        mid = low + (high - low) / 2;
        return mid;
    }

    /**
     * Implement how your algorithm should update its guess here
     */
    public void update(int answer) {
        if (answer == 1) {
            high = mid - 1;
        } else {
            low = mid + 1;
        }
    }
}
