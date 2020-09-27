/**
 * Implementation of Binary Search Tree (BST) taken from Algorithms 4th edition by Sedgewick, Wayne (Algorithm 3.3) +
 * all methods required for this implementation to run such as Queues
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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BSTT2<Key extends Comparable<Key>, Value> {
    private Node root;      // root of BST

    private class Node {

        private Node left, right;   // links to all subtrees
        private int N;              // # nodes in subtree rooted here
        private Key key;            // key
        private Value val;          // associated value

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean contains(Key key) {
        return contains(root, key);
    }

    private boolean contains(Node x, Key key) {
        if (x == null) return false;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return contains(x.left, key);
        else if (cmp > 0) return contains(x.right, key);
        else return true;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    private Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        keys(root, q, lo, hi);
        return q;
    }

    private void keys(Node x, Queue<Key> q, Key lo, Key hi) {
        if (x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);

        if (cmpLo < 0) keys(x.left, q, lo, hi);
        if (cmpLo <= 0 && cmpHi >= 0) q.enqueue(x.key);
        if (cmpHi > 0) keys(x.right, q, lo, hi);
    }

    private class Queue<Item> implements Iterable<Item> {
        private int size;
        private Node head;
        private Node tail;

        private class Node {
            private Item item;
            private Node next;
            private Node back;

            private Node(Item item, Node next) {
                this.item = item;
                this.next = next;
                back = null;
            }
        }

        public Queue() {
            size = 0;
            head = null;
            tail = null;
        }

        // Adds a new item to the tail of the queue
        public void enqueue(Item item) {
            Node oldTail = tail;
            // creates a new tail with the old tail as the next in the queue
            tail = new Node(item, oldTail);
            // if the queue was empty it makes the head equal the tail
            if (size == 0) head = tail;
                // else it makes the old tail recognize the new tail as next in line
            else oldTail.back = tail;
            size++;
        }

        // implements an iterator
        public Iterator<Item> iterator() {
            return new ListIterator();
        }

        private class ListIterator implements Iterator<Item> {
            private Queue.Node current = head;

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = (Item) current.item;
                current = current.back;
                return item;
            }
        }
    }

    /**
     * To run and test the code use the format bellow
     * java BSTT2.java N < inputFilename
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
        BSTT2<String, Integer> st = new BSTT2<>();
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

            if (!st.contains(word)) st.put(word, 1);
                // saves the word in lowercase format
            else {
                st.put(word, st.get(word) + 1);
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
        long start2 = System.nanoTime();
        // iterates through bst finding the highest value
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }
        long end2 = System.nanoTime();
        //Calculate the time in ms
        long total2 = (long) ((end2 - start2) / 1000000.0);
        System.out.println("Time to get the value: " + total2 + " ms");
        // prints highest key-value pair
        System.out.println(max + "    " + st.get(max));
        System.out.println("distinct = " + distinct);
        System.out.println("words    = " + words);
    }
}
