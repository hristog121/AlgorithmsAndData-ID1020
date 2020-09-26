//3.2

public class BinarySearchSTT1<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N;

    public BinarySearchSTT1(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    public int size() { return N; }

    public boolean isEmpty() { return N == 0; }

    public Value get(Key key) {
        if(isEmpty()) return null;
        int i = rank(key);

        if(i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return null;
    }

    public int rank(Key key) {
        int lo = 0, hi = N-1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if(cmp < 0) hi = mid - 1;
            else if(cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public boolean contains(Key key) {
        if(isEmpty()) return false;
        int i = rank(key);
        if(i < N && keys[i].compareTo(key) == 0) return true;
        else return false;
    }

    public void put(Key key, Value val) {
        int i = rank(key);
        if(i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        for(int j = N; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public void delete(Key key) {
        int i = rank(key);
        if(i < N && keys[i].compareTo(key) == 0) {
            for(int j = i + 1; j < N; j++) {
                keys[j - 1] = keys[j];
                vals[j - 1] = vals[j];
            }
        }
    }

    public Key getKeyAt(int pos) {
        if(pos < N) return keys[pos];
        else return null;
    }

    // main for testing, counting the first N*100 words
    public static void main(String[] args) {
        int distinct = 0;
        int words = Integer.parseInt(args[0]) * 100;    // saves the first input *100
        BinarySearchSTT1<String, Integer> st = new BinarySearchSTT1<>(words);
        long start = System.nanoTime();
        for(int i = 0; i < words; i++) {    // iterates through the file until it's over or we've read the given amount of words
            if(StdIn.isEmpty()) break;
            String word = StdIn.readString().toLowerCase();
            if(!st.contains(word)){
                st.put(word, 1);
            }
            else {
                st.put(word, st.get(word) + 1);// saves the word in lowercase format
                distinct ++;
            }
        }
        long end = System.nanoTime();
        long total = (long) ((end - start)/ 1000000.0);
        System.out.println("Insertion time INSERTING: " + total + " ms");
        String max = "";
        st.put(max, 0);
        long start2 = System.nanoTime();
        for(int i = 0; i < st.size(); i++) {    // iterates through st finding the highest value
            String word = st.getKeyAt(i);
            if(st.get(word) > st.get(max))
                max = word;
        }
        long end2 = System.nanoTime();
        long total2 = (long) ((end2 - start2)/ 1000000.0);
        System.out.println("Time to get the desired value: " + total2 + " ms");
        System.out.println(max + "    " + st.get(max));     // prints highest key-value pair
        System.out.println("distinct = " + distinct);
        System.out.println("words    = " + words);

    }
}
