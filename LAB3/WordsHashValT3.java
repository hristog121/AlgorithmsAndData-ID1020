/**
 * Implementation of a program that shows how evenly the built-in hashCode() function for strings on Java distributes
 * the hashcodes for the words found in the text.
 * As a data structure a SequentialSearchST which is taken from Algorithms 4th edition by Sedgewick, Wayne.
 *
 *
 * The main method is there for testing purposes.
 *
 * @author Hristo Georgiev - 1c3r00t
 **/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordsHashValT3 {
    public static class SeparateChainingHashST<Key, Value> {

        private int n;                                // number of key-value pairs
        private int m;                                // hash table size
        private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables

        /**
         * Initializes an empty symbol table with {@code m} chains.
         * @param m the initial number of chains
         */
        public SeparateChainingHashST(int m) {
            this.m = m;
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
            for (int i = 0; i < m; i++)
                st[i] = new SequentialSearchST();
        }

        /**
         * Returns true if this symbol table contains the specified key.
         *
         * @param  key the key
         * @return {@code true} if this symbol table contains {@code key};
         *         {@code false} otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        /**
         * Returns the value associated with the specified key in this symbol table.
         *
         * @param  key the key
         * @return the value associated with {@code key} in the symbol table;
         *         {@code null} if no such value
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to get() is null");
            int i = hash(key);
            return st[i].get(key);
        }

        /**
         * Inserts the specified key-value pair into the symbol table, overwriting the old
         * value with the new value if the symbol table already contains the specified key.
         * Deletes the specified key (and its associated value) from this symbol table
         * if the specified value is {@code null}.
         *
         * @param  key the key
         * @param  val the value
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("first argument to put() is null");

            // double table size if average length of list >= 10
            if (n >= 10*m) resize(2*m);

            int i = hash(key);
            if (!st[i].contains(key)) n++;
            st[i].put(key, val);
        }

        // resize the hash table to have the given number of chains,
        // rehashing all of the keys
        private void resize(int chains) {
            SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
            for (int i = 0; i < m; i++) {
                for (Key key : st[i].keys()) {
                    temp.put(key, st[i].get(key));
                }
            }
            this.m  = temp.m;
            this.n  = temp.n;
            this.st = temp.st;
        }

        // hash function for keys - returns value between 0 and m-1
        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % m;
        }
    }

    // main
    public static void main(String[] args) throws FileNotFoundException {
        //The number is decided based on the number of words in the text
        int HASH_TABLE_SIZE = 10584;
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST(HASH_TABLE_SIZE);


        int wordsCount = 0;
        //Use delimiter to remove those specific chars from the beginning of the word
        Scanner scanner = new Scanner(new File("src/TestInput.txt")).useDelimiter(",|\\s|\\n|\\r|\\.|:|\\[|\\]|\\*|--|\\(|\\)|!|_|\"|;|\\?|”|“|\\uFEFF");

        while (scanner.hasNext()) {
            String word = scanner.next();
            //dont count the ' and the empty words
            if (word.isEmpty() || word.equals("'")) { continue; }
            word = word.toLowerCase();

            if (st.contains(word)) {
                //If there is a word get it and increment the counter
                Integer wordCount = st.get(word);
                //Update the number of words
                st.put(word, wordCount + 1);
            } else {
                //Add the word for the first time
                wordsCount++;
                st.put(word, 1);
            }
        }
        //MAX_VALUE --> The maximum value of collisions
        int minCount = Integer.MAX_VALUE;
        int maxCount = Integer.MIN_VALUE;
        int averageCount = 0;
        for (SequentialSearchST list : st.st) {
            int size = list.size();
            if (size > maxCount) {
                maxCount = size;
            }
            if (size < minCount && size > 0) {
                minCount = size;
            }
            averageCount += size;
        }
        //Iterate through the hashtable and prints hash values + collisions -> this info will be used in the diagram
        for (int i = 0; i < st.st.length; i++) {
            System.out.println(String.format("%d,%d", i, st.st[i].size()));
        }
        //Calculate and display information to the user
        System.out.println("Different words count " + wordsCount);
        System.out.println("Min collisions count " + minCount);
        System.out.println("Max collisions count " + maxCount);
        System.out.println("Average collisions count " + averageCount / st.st.length);
    }
}