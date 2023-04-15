import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> high = new PriorityQueue<>();

    public MedianFinder() {       
    }

    public void balance() {
        while (low.size() > high.size() + 1) {
            int e = low.poll();
            high.add(e);
        }
        while (high.size() > low.size() + 1) {
            int e = high.poll();
            low.add(e);
        }
    }

    public void insert(int x) {
        if (low.size() == 0 || x <= low.peek()) {
            low.add(x);
        } else if (high.size() == 0 || x >= high.peek()) {
            high.add(x);
        } else {
            low.add(x);
        }
        balance();
    }

    public int getMedian() {
        int medianValue;
        if (low.size() <= high.size()) {
            medianValue = high.poll();
        } else {
            medianValue = low.poll();
        }
        balance();
        return medianValue;
    }
}
