import java.util.TreeSet;

public class Solution {
    TreeSet<Quest> listOfQuest;

    public class Quest implements Comparable<Quest> {
        long energy;
        long value;

        public Quest(long energy, long value) {
            this.energy = energy;
            this.value = value;
        }

        @Override
        public int compareTo(Quest q) {
            if (energy != q.energy) {
                return (int) (energy - q.energy);
            } else {
                return (int) (value - q.value);
            }
        }
    }

    public Solution() {
        listOfQuest = new TreeSet<>();
    }

    void add(long energy, long value) {
        Quest quest = new Quest(energy, value);
        listOfQuest.add(quest);
    }

    long query(long remainingEnergy) {
        long valueToReturn = 0;
        while (!listOfQuest.isEmpty() && remainingEnergy > 0) {
            Quest compare = new Quest(remainingEnergy, Long.MAX_VALUE);
            Quest curr = listOfQuest.floor(compare);
            if (curr != null) {
                valueToReturn += curr.value;
                remainingEnergy -= curr.energy;
                listOfQuest.remove(curr);
            } else {
                break;
            }
        }
        return valueToReturn;
    }

}
