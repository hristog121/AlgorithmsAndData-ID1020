import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTT2 <Key extends Comparable<Key>, Value>{
    private Node root;      // root of BST

    private class Node {
        private Key key;            // key
        private Value val;          // associated value
        private Node left, right;   // links to subtrees
        private int N;              // # nodes in subtree rooted here

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size() { return size(root); }

    private int size(Node x) {
        if(x == null) return 0;
        else return x.N;
    }

    public Value get(Key key) { return get(root, key); }

    private Value get(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return get(x.left, key);
        else if(cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) { root = put(root, key, val); }

    private Node put(Node x, Key key, Value val) {
        if(x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = put(x.left, key, val);
        else if(cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean contains(Key key) { return contains(root, key); }

    private boolean contains(Node x, Key key) {
        if(x == null) return false;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return contains(x.left, key);
        else if(cmp > 0) return contains(x.right, key);
        else return true;
    }

    public Key min() { return min(root).key; }

    private Node min(Node x) {
        if(x.left == null) return x;
        return min(x.left);
    }

    public Key max() { return max(root).key; }

    private Node max(Node x) {
        if(x.right == null) return x;
        return max(x.right);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if(x == null) return null;
        return x.key;
    }

    public Node floor(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;
        if(cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if(t != null) return t;
        else return x;
    }

    public Key select(int k) { return select(root, k).key; }

    // return Node containing key of rank k
    private Node select(Node x, int k) {
        if(x == null) return null;
        int t = size(x.left);
        if(t > k) return select(x.left, k);
        else if(t < k) return select(x.right, k-t-1);
        else return x;
    }

    public int rank(Key key) { return rank(key, root); }

    // return number of keys less than x.key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return rank(key, x.left);
        else if(cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    public void deleteMin() { root = deleteMin(root); }

    private Node deleteMin(Node x) {
        if(x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) { root = delete(root, key); }

    private Node delete(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = delete(x.left, key);
        else if(cmp > 0) x.right = delete(x.right, key);
        else {
            if(x.right == null) return x.left;
            if(x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() { return keys(min(), max()); }

    private Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        keys(root, q, lo, hi);
        return q;
    }

    private void keys(Node x, Queue<Key> q, Key lo, Key hi) {
        if(x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);

        if(cmpLo < 0) keys(x.left, q, lo, hi);
        if(cmpLo <= 0 && cmpHi >= 0 ) q.enqueue(x.key);
        if(cmpHi > 0) keys(x.right, q, lo, hi);
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
            tail = new Node(item, oldTail); // creates a new tail with the old tail as the next in the queue
            if(size == 0) head = tail;      // if the queue was empty it makes the head equal the tail
            else oldTail.back = tail;       // else it makes the old tail recognize the new tail as next in line
            size++;
        }

        // returns the head of the queue and removes it from the queue
        public Item dequeue() {
            if(size == 0) throw new NoSuchElementException();   // if queue is empty
            Item pass = head.item;      // saves the item to be passed
            if(size == 1) {         // empties queue if this is the last node in queue
                head = null;
                tail = null;
            } else {                // sets the next in the queue to be head
                head = head.back;
                head.next = null;
            }
            size--;
            return pass;
        }

        // implements an iterator
        public Iterator<Item> iterator() { return new ListIterator(); }

        private class ListIterator implements Iterator<Item> {
            private Queue.Node current = head;

            public boolean hasNext() { return current != null; }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = (Item) current.item;
                current = current.back;
                return item;
            }
        }
    }
    // main for testing, counting the first N*100 words
    public static void main(String[] args) {
        int distinct = 0;
        int words = Integer.parseInt(args[0]) * 100;    // saves the first input *100
        BSTT2<String, Integer> st = new BSTT2<>();
        long start = System.nanoTime();
        for(int i = 0; i < words; i++) {    // iterates through the file until it's over or we've read the given amount of words
            if(StdIn.isEmpty()) break;
            String word = StdIn.readString().toLowerCase();
            if(!st.contains(word)) st.put(word, 1);
            else st.put(word, st.get(word) + 1);    // saves the word in lowercase format
        }
        long end = System.nanoTime();
        long total = (long) ((end - start)/ 1000000.0);
        System.out.println("Insertion time INSERTING: " + total + " ms");
        String max = "";
        st.put(max, 0);
        long start2 = System.nanoTime();
        for(String word : st.keys()) {  // iterates through st finding the highest value
            if(st.get(word) > st.get(max))
                max = word;
        }
        long end2 = System.nanoTime();
        long total2 = (long) ((end2 - start2)/ 1000000.0);
        System.out.println("Time to get the value: " + total2 + " ms");
        System.out.println(max + "    " + st.get(max));     // prints highest key-value pair
        System.out.println("distinct = " + distinct);
        System.out.println("words    = " + words);
    }
}
