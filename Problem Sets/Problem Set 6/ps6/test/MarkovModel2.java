import java.util.*;

public class MarkovModel2 {
    // Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final String NOWORD = "";

	// This field is store the order of the MarkovModel.
	private final int order;

	// This is the hash table to map the kgram to another HashMap that maps
    // a word to an integer representing the number of times it appears
    // after the kgram.
	private HashMap<String, HashMap<String, Integer>> hashTable;
    
    public MarkovModel2(int order, long seed) {
        this.order = order;
        this.hashTable = new HashMap<>();
        generator.setSeed(seed);
    }

    public void initializeText(String text) {

        String[] words = text.split(" ");
        StringBuilderExtended str = new StringBuilderExtended();
        for (int i = 0; i < words.length - 1; i++) {
            int count;
            if (words[i].compareTo("") != 0) {
                count = str.insert(words[i]);
            } else {
                count = str.count;
            }

            if (count == order && words[i + 1].compareTo("") != 0) {
                String tmp = str.toString();
                str.removeFirstWord();
                if (hashTable.containsKey(tmp)) {
                    HashMap<String, Integer> timesStringAppear = hashTable.get(tmp);
                    timesStringAppear.put(words[i + 1], timesStringAppear.getOrDefault(tmp, 0) + 1);
                    hashTable.put(tmp, timesStringAppear);
                } else {
                    HashMap<String, Integer> timesStringAppear = new HashMap<>();
                    timesStringAppear.put(words[i + 1], 1);
                    hashTable.put(tmp, timesStringAppear);
                }
            }
        }
    }

    public int getFrequency(String kgram) {
        if (kgram == null || countNoOfWords(kgram) == 0) {
            throw new IllegalArgumentException();
        } else if (hashTable.containsKey(kgram)) {
            HashMap<String, Integer> timesStringAppear = hashTable.get(kgram);
            int count = 0;
            for (String str : timesStringAppear.keySet()) {
                count += timesStringAppear.get(str);
            }
            return count;
        } else {
            return 0;
        }
    }

    public String nextString(String kgram) {
        int total = getFrequency(kgram);

        if (total <= 0) {
            return NOWORD;
        } else {
            int totalTimesAppear = getFrequency(kgram);
		    int randomNumber = generator.nextInt(totalTimesAppear) + 1;
		    String nextWord = NOWORD;
		    Object[] wordKeys = hashTable.get(kgram).keySet().toArray();
		    Arrays.sort(wordKeys);

		for (Object obj: wordKeys) {
			String word = (String) obj;
			int noOfTimesAppear = hashTable.get(kgram).get(word);
			if (noOfTimesAppear >= randomNumber) {
				nextWord = word;
				break;
			} else {
				randomNumber -= noOfTimesAppear;
			}
		}
		return nextWord;
        }
    }

    static int countNoOfWords(String kgram) {
        if (kgram == null) {
            return 0;
        }
        kgram.strip();
        if (kgram.length() == 0) {
            return 0;
        }
        int count = 1;
        boolean spaceBefore = false;
        for (int i = 0; i < kgram.length(); i++) {
            if (kgram.charAt(i) == ' ') {
                spaceBefore = true;
            } else {
                if (spaceBefore) {
                    count++;
                    spaceBefore = false;
                }
            }
        }
        return count;
    }
}
