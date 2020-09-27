/**
 * Implementation of ordered array ST taken from Algorithms 4th edition by Sedgewick, Wayne (Algorithm 3.2)
 * The goal of this implementation is to observer the running time that the algorithm takes to
 * 'put' and 'get' keys.
 * <p>
 * The main method is there for testing purposes. The main method contains a modified code from FrequencyCounter taken
 * from Algorithms 4th edition by Sedgewick, Wayne. The timing is done there.
 *
 * @author Hristo Georgiev - 1c3r00t
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BinarySearchSTT2<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N;

    public BinarySearchSTT2(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Value get(Key key) {
        if (isEmpty()) return null;
        int i = rank(key);

        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return null;
    }

    public int rank(Key key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public boolean contains(Key key) {
        if (isEmpty()) return false;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return true;
        else return false;
    }

    public void put(Key key, Value val) {
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        if (N == keys.length) resize(2 * keys.length);
        for (int j = N; j > i; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    // resize the underlying arrays
    private void resize(int capacity) {
        assert capacity >= N;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    public Key getKeyAt(int pos) {
        if (pos < N) return keys[pos];
        else return null;
    }

    /**
     * To run and test the code use the format bellow
     * java BinarySearchSTT2.java N < inputFilename
     * N -> how many hundred words should be read
     * inputFilename -> the file you will read from. If the file is in another folder the full path should be given
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        int distinct = 0;
        int words = Integer.parseInt(args[0]) * 100;    // saves the first input *100
        //take the file path as the second argument on the terminal
        String filePath = (args[1]);
        //TODO Del this
        //String filePath = "/Users/iceroot/WorkingDev/Algo2020/LAB3/Java/src/TestInput.txt";
        BinarySearchSTT2<String, Integer> st = new BinarySearchSTT2<>(2);
        //Use delimiter to remove those specific chars from the beginning of the word
        Scanner scanner = new Scanner(new File(filePath)).useDelimiter(",|\\s|\\n|\\r|\\.|:|\\[|\\]|\\*|--|\\(|\\)|!|_|\"|;|\\?|”|“|\\uFEFF");

        // Pattern to recognize ' in the beginning of the word in order to be able to trim it
        Pattern pattern = Pattern.compile("'\\w+");

        //Start timing for the insertion of keys
        long start = System.nanoTime();
        // iterates through the file until it's over or we've read the given amount of words
        for (int i = 0; i <= words; i++) {
            if (!scanner.hasNext()) break;
            String word = scanner.next().toLowerCase();
            if (word.isEmpty() || word.equals("'")) {
                i--;
                continue;
            }
            if (pattern.matcher(word).matches()) {
                word = word.replace("'", "");
            }
            if (!st.contains(word)) {
                st.put(word, 1);
            } else {
                st.put(word, st.get(word) + 1);// saves the word in lowercase format
                distinct++;
            }
        }
        long end = System.nanoTime();
        scanner.close();
        //Calculate the time in ms
        long total = (long) ((end - start) / 1000000.0);
        System.out.println("Insertion time INSERTING: " + total + " ms");
        String max = "";
        st.put(max, 0);
        //Start timing for the finding the key with highest frequency
        long start2 = System.nanoTime();
        // iterates through st finding the highest value
        for (int i = 0; i < st.size(); i++) {
            String word = st.getKeyAt(i);
            if (st.get(word) > st.get(max))
                max = word;
        }
        long end2 = System.nanoTime();
        //Calculate the time in ms
        long total2 = (long) ((end2 - start2) / 1000000.0);
        System.out.println("Time to get the desired value: " + total2 + " ms");
        // prints highest key-value pair
        System.out.println(max + "    " + st.get(max));
        System.out.println("distinct = " + distinct);
        System.out.println("words    = " + words);

    }
}
