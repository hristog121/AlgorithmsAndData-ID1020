
import java.util.Iterator;
import java.util.NoSuchElementException;

// Data structure representing a Bag of Items backed by a linked list
public class Bag<Item> implements Iterable<Item>{
    // Holds the head of the linked list
    private Node<Item> head;

    // Adds anew item to the linked list
    public void add(Item item) {
        // If the head is null, initialize the head with the 1st item
        if (head == null) {
            head = new Node<>(item);
        }
        // Otherwise add a new node to the beginning of the linked list
        else {
            head = new Node<>(item, head);
        }
    }

    // Returns whether the Bag is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Returns an iterator for the Bag
    @Override
    public Iterator<Item> iterator() {
        return new BagIterator<>(head);
    }

    // Returns a boolean stating whether an Item is present in the Bag
    public boolean contains(Item item){
        // Iterate over the iteam and check if some of the items in the bag is equal to the specified item
        // Return immediately if an item is found
        for (Item value : this) {
            if (value.equals(item)) {
                return true;
            }
        }
        return false;
    }

    // A node that holds the value of the current item and a pointer to the next value
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;

        public Node(Item item) {
            this.item = item;
        }

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }

    // Implementation of Iterator for the Bag
    private static class BagIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        // Creates a Bag iterator with a link to the 1st item set as current
        public BagIterator(Node<Item> current) {
            this.current = current;
        }

        // Returns whether there are more items in the bag
        @Override
        public boolean hasNext() {
            return current != null;
        }

        // Returns the current item in the bag and points the current pointer to the next item
        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item res = current.item;
            current = current.next;
            return res;
        }
    }
}
