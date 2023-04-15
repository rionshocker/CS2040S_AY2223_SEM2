import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';
    TrieNode root;

    private class TrieNode {
        TrieNode[] presentChars = new TrieNode[62];
        char key;
        boolean end;

        //for root with no children
        public TrieNode() {
            this.key = WILDCARD;
            this.end = false;
        }

        public TrieNode(char c) {
            this.key = c;
            this.end = false;
        }

        public TrieNode find(char key) {
            if ((int) key >= 48 && (int) key <= 57) {
                return presentChars[key - 48];
            } else if ( (int) key >= 65 && (int) key <= 90) {
                return presentChars[key - 54];
            } else {
                return presentChars[key - 60];
            }
        }

        public boolean contains(char key) {
            if ((int) key >= 48 && (int) key <= 57) {
                return presentChars[key - 48] != null;
            } else if ( (int) key >= 65 && (int) key <= 90) {
                return presentChars[key - 54] != null;
            } else {
                return presentChars[key - 60] != null;
            }
        }

        public void insert(TrieNode node) {
            if ((int)node.key >= 48 && (int) node.key <= 57) {
                presentChars[node.key - 48] = node;
            } else if ( (int) node.key >= 65 && (int) node.key <= 90) {
                presentChars[node.key - 54] = node;
            } else {
                presentChars[node.key - 60] = node;
            }
        }

        public void markEnd() {
            this.end = true;
        }
    }
    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        TrieNode node = this.root;
        int length = s.length();
        
        for (int i = 0; i < length; i++) {
            char curr = s.charAt(i);
            if (node.contains(curr)) {
                node = node.find(curr);
            } else {
                TrieNode newNode = new TrieNode(curr);
                node.insert(newNode);
                node = newNode;
            }
        }
        node.markEnd();
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        TrieNode curr = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((int) c < 48 || (int) c > 122) {
                return false;
            } else if (!curr.contains(c)) {
                return false;
            } else {
                curr = curr.find(c);
            }
        }
        return curr.end;
    }

    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        TrieNode node = root;
        StringBuilder str = new StringBuilder("");

        if (s.contains(".")) {
            prefixSearchHelper(s, node, str, results, limit);
        } else {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (node.contains(c)) {
                    str.append(c);
                    node = node.find(c);
                } else {
                    return;
                }
            }
            getRestOfChars(node, str, results, limit);
        }
    }

    void prefixSearchHelper(String s, TrieNode node, StringBuilder str, ArrayList<String> results, int limit) {
        if (s.equals("")) {
            getRestOfChars(node, str, results, limit);
        } else {
            char c = s.charAt(0);
            if (c == WILDCARD) {
                int length = str.length();
                for (int i = 0; i < node.presentChars.length; i++) {
                    if (node.presentChars[i] != null) {
                        char letter = node.presentChars[i].key;
                        str.append(letter);
                        prefixSearchHelper(s.substring(1), node.presentChars[i], str, results, limit);
                        str.delete(length, str.length());
                    }
                }
            } else {
                if (node.contains(c)) {
                    str.append(c);
                    prefixSearchHelper(s.substring(1), node.find(c), str, results, limit);
                }
            }
        }
    }

    public void getRestOfChars(TrieNode node, StringBuilder str, ArrayList<String> results, int limit) {
        if (node.end) {
            if (results.size() < limit) {
                results.add(str.toString());
            }
        }
        for (int i = 0; i < node.presentChars.length; i++) {
            if (node.presentChars[i] != null) {
                str.append(node.presentChars[i].key);
                getRestOfChars(node.presentChars[i], str, results, limit);
                str.deleteCharAt(str.length() - 1);
            }
        }
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.", 10);
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
