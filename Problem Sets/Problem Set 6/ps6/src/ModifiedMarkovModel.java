import java.util.Random;

import java.util.HashMap;

import java.util.ArrayList;

public class ModifiedMarkovModel {
    
    private Random generator = new Random();

    public static final char NOCHARACTER = (char) 0;

    private final int order;

    private HashMap<ArrayList<String>, ArrayList<String>> hashTable;

    public ModifiedMarkovModel(int order, long seed) {
        this.order = order;
        this.hashTable = new HashMap<>();
        generator.setSeed(seed);
    }

    public void initializeText(String text) {
        String[] words = text.split(" ");
        for (int i = 0; i < words.length - order; i++) {
            ArrayList<String> key = new ArrayList<>();
            for (int j = i; j < i + order; j++) {
                key.add(words[j]);
            }
            ArrayList<String> value = hashTable.getOrDefault(key, new ArrayList<>());
            value.add(words[i + order]);
            hashTable.put(key, value);
        }
    }

    public String generate(int length) {
        ArrayList<String> current = new ArrayList<>();
        for (int i = 0; i < order; i++) {
            current.add("");
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ArrayList<String> key = new ArrayList<>(current.subList(1, current.size()));
            ArrayList<String> next = hashTable.get(key);
            if (next == null || next.isEmpty()) {
                break;
            }
            String word = next.get(generator.nextInt(next.size()));
            output.append(word).append(" ");
            current.remove(0);
            current.add(word);
        }
        return output.toString().trim();
    }
}
