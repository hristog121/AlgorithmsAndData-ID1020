

import java.util.Arrays;
import java.util.Iterator;

/**
 * This is an implementation of generalized queue which allows the user to remove the kth element from the queue.
 * Assume the most recently added element has index 1.
 * The implementation contains methods to:
 * addToQ - adds an element to the
 * removeFromQFront - removes an element on the kth index from the queue
 *
 * @param <Item>
 * @author Hristo Georgiev - 1c3r00t
 */

public class GeneralQkthT5<Item> {

    private Item[] queue;
    private int qSize;

    //Constructor
    public GeneralQkthT5() {
        //Stack items
        queue = (Item[]) new Object[1];
        //Initial size
        qSize = 0;
    }

    //Check if the queue is empty
    public boolean isEmpty() {
        return qSize == 0;
    }

    /**
     * Use a string builder because of the special output required from the lab task.
     * This is requested by the lab task
     */
    public String displayBrackets() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < qSize - 1) {
            sb.append("[" + queue[i++] + "],");
        }
        sb.append("[" + queue[i++] + "]");
        return sb.toString();
    }

    public void addToQ(Item item) {
        if (qSize == queue.length) {
            extendArray();
        }
        queue[qSize] = item;
        qSize++;
        //Display the updated list after each adding. This is requested by the lab task
        System.out.println(displayBrackets());
    }

    public Item removeFromQ(int k) {
        if (isEmpty()) {
            System.out.println(displayBrackets());
            return null;
        }
        if (k <= 0 || qSize < k) {
            System.out.println("Invalid Index");
        }
        //Ponts to the place of the array we will remove
        int itemToRemoveIndex = qSize - k;
        //The item at the desired index that will be removed
        Item item = queue[itemToRemoveIndex];
        reIndex(itemToRemoveIndex);
        qSize--;
        if (qSize == queue.length / 4) {
            shrinkArray();
        }
        //Display the updated list after removing an element. This is requested by the lab task
        System.out.println(displayBrackets());
        return item;
    }

    /**
     * This method is used remove the empty slots, so the size can be shrieked.
     *
     * @param primeIndex
     */
    private void reIndex(int primeIndex) {
        for (int i = primeIndex; i < qSize - 1; i++) {
            queue[i] = queue[i + 1];
        }
        // Clean up and avoid loitering
        queue[qSize - 1] = null;
    }

    /**
     * Decreases the size of the array (Capacity) when elements are removed
     */
    private void shrinkArray() {
        int newShrinkedCapacuty = queue.length / 2;
        queue = Arrays.copyOf(queue, newShrinkedCapacuty);

    }

    /**
     * Increases the size of the array (Capacity) when elements are added and the default capacity
     * is reached
     */

    private void extendArray() {
        int newExtendedCapacity = queue.length * 2;
        queue = Arrays.copyOf(queue, newExtendedCapacity);
    }

    public int size() {
        return queue.length;
    }

    public int count() {
        return qSize;
    }

}
