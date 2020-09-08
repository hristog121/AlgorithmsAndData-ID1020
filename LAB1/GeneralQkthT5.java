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

    }

    public Item removeFromQ(int k){
        if (isEmpty()){
            System.out.println("Queue is empty");
        }
        if (k <= 0 || qSize < k){
            System.out.println("Invalid Index");
        }
        Item item = queue[k-1];
        reIndex(k);
        qSize --;
        if (qSize == queue.length /4){
            shrinkArray();
        }
        //System.out.println(item);
        return item;
    }

    private void reIndex(int primeIndex) {
        for (int i = primeIndex; i < qSize; i++) {
            queue[i-1] = queue[i];
        }
        queue[qSize -1] = null; // Clean up and avoid loitering
    }

    private void shrinkArray() {
        int newShrinkedCapacuty = queue.length /2;
        queue = Arrays.copyOf(queue,newShrinkedCapacuty);

    }

    private void extendArray() {
        int newExtendedCapacity = queue.length*2;
        queue = Arrays.copyOf(queue,newExtendedCapacity);
    }

    @Override
    public Iterator<Item> iterator() {
        return new GeneralQkthIterato();
    }

    private class GeneralQkthIterato implements Iterator<Item> {
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
        GeneralQkthT5<Integer> generalQkthT5 = new GeneralQkthT5<>();
        generalQkthT5.addToQ(0);
        //System.out.println(generalQkthT5.displayBrackets());
        generalQkthT5.addToQ(2);
        //System.out.println(generalQkthT5.displayBrackets());
        generalQkthT5.addToQ(4);
        //System.out.println(generalQkthT5.displayBrackets());
        System.out.println("Q after insertion");
        System.out.println(generalQkthT5.displayBrackets());
        //System.out.println("Q after removal of element on index 3 :");
        //generalQkthT5.removeFromQ(3);
        //System.out.println(generalQkthT5.displayBrackets());
    }
}
