import java.util.Arrays;
import java.util.Iterator;

public class GeneralQkthT5<Item> implements Iterable<Item> {

    private Item [] queue;
    private int qSize;

    //Constructor
    public GeneralQkthT5() {
        //Stack items
        queue = (Item[]) new Object[1];
        //Initial size
        qSize = 0;
    }
    public boolean isEmpty(){
        return qSize == 0;
    }
    public Item get(int position){
        return queue[position];
    }

    public String displayBrackets() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < qSize -1){
            sb.append("[" + queue[i++] + "],");
        }
        sb.append("[" + queue[i++] + "]");
        return sb.toString();
    }

    public void addToQ(Item item){
        if (qSize == queue.length){
            extendArray();
        }
        queue[qSize] = item;
        //System.out.println(queue[qSize]);
        qSize++;
        System.out.println(displayBrackets());
    }

    public Item removeFromQ(int k){
        if (isEmpty()){
            System.out.println(displayBrackets());
            return null;
        }
        if (k <= 0 || qSize < k){
            System.out.println("Invalid Index");
        }
        int itemToRemoveIndex = qSize - k;
        Item item = queue[itemToRemoveIndex];
        reIndex(itemToRemoveIndex);
        qSize --;
        if (qSize == queue.length / 4){
            shrinkArray();
        }
        System.out.println(displayBrackets());
        return item;
    }

    private void reIndex(int primeIndex) {
        for (int i = primeIndex; i < qSize - 1; i++) {
            queue[i] = queue[i+1];
        }
        queue[qSize - 1] = null; // Clean up and avoid loitering
    }

    private void shrinkArray() {
        int newShrinkedCapacuty = queue.length / 2;
        queue = Arrays.copyOf(queue, newShrinkedCapacuty);

    }

    private void extendArray() {
        int newExtendedCapacity = queue.length * 2;
        queue = Arrays.copyOf(queue, newExtendedCapacity);
    }

    @Override
    public Iterator<Item> iterator() {
        return new GeneralQkthIterator();
    }

    public void printAll() {
        for(Item i : queue) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private class GeneralQkthIterator implements Iterator<Item> {
        private int index = 0;
        @Override
        public boolean hasNext() {
            return index < qSize;
        }

        @Override
        public Item next() {
            Item item = queue[index];
            index++;
            return item;
        }
    }

    public static void main(String[] args) {
        System.out.println("Add elements");
        GeneralQkthT5<Integer> generalQkthT5 = new GeneralQkthT5<>();
        generalQkthT5.addToQ(0);
        generalQkthT5.addToQ(2);
        generalQkthT5.addToQ(4);
        generalQkthT5.addToQ(5);
        generalQkthT5.addToQ(6);
        generalQkthT5.addToQ(7);
        generalQkthT5.addToQ(9);
        generalQkthT5.addToQ(10);
        System.out.println();

        System.out.println("Remove elements");
        generalQkthT5.removeFromQ(3);
        generalQkthT5.removeFromQ(1);
        generalQkthT5.removeFromQ(2);
        generalQkthT5.removeFromQ(5);
        generalQkthT5.removeFromQ(2);
        generalQkthT5.removeFromQ(2);
        generalQkthT5.removeFromQ(2);
        generalQkthT5.removeFromQ(1);
        System.out.println();

        System.out.println("Test remove with no elements");
        generalQkthT5.removeFromQ(1);
    }
}
