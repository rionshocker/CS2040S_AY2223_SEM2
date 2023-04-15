import java.util.HashMap;

import java.util.Random;

import java.util.Arrays;

public class InitialMarkovModel {
    // Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	// This field is store the order of the MarkovModel.
	private final int order;

	// This is the hash table to map the kgram to an integer array that
	// contains the number of times each character appears after a kgram.
	private HashMap<String, HashMap<Character, Integer>> hashTable;

    /**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public InitialMarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.hashTable = new HashMap<>();

		// Initialize the random number generator
		generator.setSeed(seed);
	}

    public void initializeText(String text) {
        for (int i = 0; i < text.length() - this.order; i++) {
            String kgram = text.substring(i, i + this.order); //get the kgram to fit into the hash table
            char nextChar = text.charAt(i + this.order); //get the next character to add to the count
            if (hashTable.containsKey(kgram)) {
            	//if the hash table already contains the key, just access the hashTable to append to the count.
            	HashMap<Character, Integer> asciiToInt = hashTable.get(kgram);
            	if (asciiToInt.containsKey(nextChar)) {
            		//if the ASCII hash table already contains the next character,
            		//get the number of times it appears which is the value in the hash table,
            		//and add 1 to it, changing the value in the hash table.
            		int noOfTimesAppear = asciiToInt.get(nextChar);
            		asciiToInt.put(nextChar, noOfTimesAppear + 1);
            	} else {
            		//otherwise, instantiate a value of 1 to the nextChar in the ASCII hash table.
            		asciiToInt.put(nextChar, 1);
            	}
            } else {
            	//Else create a new hash table to map the ASCII characters to integers
            	HashMap<Character, Integer> asciiToInt = new HashMap<>();
            	asciiToInt.put(nextChar, 1); //put in the count of the nextChar appearing after the kgram
            	hashTable.put(kgram, asciiToInt); //put into the overarching hashtable the kgram and its respective ascii hash table
            }
        }
    }

    /**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (kgram.length() != this.order) {
			//if the kgram given does not have a length that is the same as the order of the 
			//Markov Model, then we can throw an IllegalArgumentException to handle this case.
			throw new IllegalArgumentException();
		} else if (!hashTable.containsKey(kgram)) {
			//else if the hashtable does not contain the kgram, then return 0 as it appears 0 times.
			return 0;
		} else {
			//Otherwise, get the ASCII hash table for the respective kgram.
			HashMap<Character, Integer> asciiToInt = hashTable.get(kgram);
			int noOfTimesAppear = 0;
			//Through the for loop, we can determine the number of times the kgram appears,
			//by adding the number of times the characters after the specific kgram appears.
			for (Character character: asciiToInt.keySet()) {
				noOfTimesAppear = noOfTimesAppear + asciiToInt.get(character);
			}
			return noOfTimesAppear;
		}
    }

    /**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (kgram.length() != this.order) {
			//If the kgram given does not have a length that is the same as the order of the
			//Markov Model, then we throw an IllegalArgumentException to handle this invalid case.
			throw new IllegalArgumentException();
		} else if (!hashTable.containsKey(kgram)) {
			//else if the hash table does not contain this kgram, then return 0.
			return 0;
		} else {
			//Otherwise, retrieve the ASCII hash table of the kgram.
			HashMap<Character, Integer> asciiToInt = hashTable.get(kgram);
			//Return the number of times the character appears after the kgram,
			//Otherwise returns 0 as the default value.
			return asciiToInt.getOrDefault(c, 0);
		}
    }

    /**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (!hashTable.containsKey(kgram)) {
			return NOCHARACTER;
		}
		HashMap<Character, Integer> asciiToInteger = hashTable.get(kgram);
		if (asciiToInteger.isEmpty()) {
			return NOCHARACTER;
		}
		int totalTimesAppear = getFrequency(kgram);
		int randomNumber = generator.nextInt(totalTimesAppear) + 1;
		char nextCharacter = NOCHARACTER;
		Object[] asciiKeys = asciiToInteger.keySet().toArray();
		Arrays.sort(asciiKeys);

		for (Object obj: asciiKeys) {
			char character = (char) obj;
			int noOfTimesAppear = asciiToInteger.get(character);
			if (noOfTimesAppear >= randomNumber) {
				nextCharacter = character;
				break;
			} else {
				randomNumber -= noOfTimesAppear;
			}
		}
		return nextCharacter;
    }
}
