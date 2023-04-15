import java.util.Random;

import java.util.HashMap;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	// This field is store the order of the MarkovModel.
	private final int order;

	// This is the hash table to map the kgram to an integer array that
	// contains the number of times each character appears after a kgram.
	private HashMap<String, int[]> hashTable;

	// This field is store the total number of ASCII characters available.
	private int noOfASCIICharacters = 256;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.hashTable = new HashMap<>();

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		for (int i = 0; i < text.length() - this.order; i++) { 
			String kgram = text.substring(i, i + this.order); //get the kgram to fit into the hashtable as the key.
			char nextChar = text.charAt(i + this.order); //get the next character to add to the count.
			if (hashTable.containsKey(kgram)) {
				//if the hashtable already contains the kgram,
				//access the int array linked to it to change the count of the nextChar.
				int[] asciiArr = this.hashTable.get(kgram);
				asciiArr[nextChar] = asciiArr[nextChar] + 1;
			} else {
				//Otherwise, create a new int array to link the kgram to, with the count of the nextChar being 1.
				//Then insert it into the hashtable.
				int[] asciiArr = new int[256];
				asciiArr[(int) nextChar] = 1;
				this.hashTable.put(kgram, asciiArr);
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
			throw new IllegalArgumentException("Invalid kgram length!");
		} else if (!this.hashTable.containsKey(kgram)) {
			//else if the hashtable does not contain the kgram, then return 0 as it appears 0 times.
			return 0;
		} else {
			//Otherwise get the int array linked to the kgram.
			int[] asciiArr = this.hashTable.get(kgram);
			int noOfTimesAppear = 0;
			//Through the for loop, we can determine the number of times the kgram appears,
			//by adding the number of times characters after the specific kgram appears.
			for (int i = 1; i < noOfASCIICharacters; i++) {
				if (asciiArr[i] > 0) {
					noOfTimesAppear += asciiArr[i];
				}
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
			throw new IllegalArgumentException("Invalid kgram length!");
		} else if (!this.hashTable.containsKey(kgram)) {
			//else if the hash table does not contain this kgram, then return 0.
			return 0;
		} else {
			//Otherwise, retrieve the int array linked to the kgram.
			int[] asciiArr = this.hashTable.get(kgram);
			return asciiArr[(int) c]; //Return the number of times the character appears after the kgram
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

		if (this.hashTable.containsKey(kgram)) {
			//if the hashtable contains the kgram, then continue.
			int totalTimesAppear = this.getFrequency(kgram);
			//Retrieve a random number from [0, totalTimesAppear - 1]
			int randomNumber = this.generator.nextInt(totalTimesAppear);
			int[] asciiArr = this.hashTable.get(kgram);
			char currCharacter;
			int noOfTimesAppear;

			//Iterate through the asciiArr in alphabetical order.
			for (int i = 1; i < this.noOfASCIICharacters; i++) {
				currCharacter = (char) i;
				noOfTimesAppear = asciiArr[i];
				if (noOfTimesAppear > 0) {
					if (randomNumber > noOfTimesAppear - 1) {
						//if randomNumber is more than the number of times the character appears, then
						//we take the number of times away from random number, restricting the scope
						//such that the randomNumber will be less than or equal to the number of times
						//a character appears, in which means the probability of the character is equal to
						//the frequency that that character follows the kgram.
						randomNumber -= noOfTimesAppear;
					} else {
						//return the currCharacter when the probability of this character
						//is equal to the frequency that this character follows the
						//kgram.
						return currCharacter;
					}
				}
			}
		}
		return NOCHARACTER; //return NOCHARACTER otherwise
	}
}
