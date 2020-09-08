import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This is an implementation of generic iterable FIFO-queue based on a double linked circular list.
 * The implementation contains methods to:
 * addToQ - adds an element in the back of the queue - FIFO
 * removeFromQFront - removes an element in the front of the queue - FIFO
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */
public class DoubleCircularQT3<Item> implements Iterable {

    //Sentinel element
    private Node<Item> sentinel;
    //Size of the list - used for the 'remove' operation
    private int numberOfNodes;

    /**
     * Iterator implementation. Used to iterate through the list.
     * @param <Item>
     */


    private static class DCLLIterator<Item> implements Iterator<Item>{
        private Node<Item> current;
        private Node<Item> root;
        private boolean rootVisited = false;

        //Constructor for the iterator
        public DCLLIterator(Node root) {
            this.root = root;
            this.current = root;
        }

        @Override
        public boolean hasNext() {
            //Check if there is more elements
            if (current == null || (current == root && rootVisited)){
                return false;
            }
            return true;
        }
        //Gives the next element in the list
        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            if (current == root){
                if (rootVisited){
                    throw new NoSuchElementException();
                } else {
                    rootVisited = true;
                }
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    @Override
    public Iterator iterator() {
        return new DCLLIterator(sentinel != null ? sentinel.next : null);
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }
    //Method to add new items in the back of the queues. Also add pointers between the beginning and the end of the list
    //To make it circular
    public void addToQ(Item item) {
        Node<Item> newNode = new Node();
        newNode.item = item;
        numberOfNodes++;
        if (sentinel == null) {
            //Loop the pointers - makes the list circular
            newNode.next = newNode.prev = newNode;
        } else {
            newNode.next = sentinel.next;
            newNode.prev = sentinel;
            sentinel.next = newNode;
            newNode.next.prev = newNode;
        }
        sentinel = newNode;
        //Display the updated list after each adding. This is requested by the lab task
        System.out.println(displayBrackets());
    }
    //Remove element from the front of the queue and fix the pointers to keep the list circular
    public void removeFromFront(){
        if (sentinel == null){
            System.out.println("The queue is empty!");
        } else {
            // Decrease the counter because a node will be removed
            numberOfNodes--;
            //If this is the only element remove the references to this element
            if (sentinel == sentinel.next && sentinel == sentinel.prev) {
                sentinel = null;
            } else {
                //Keep the Circle in the list
                sentinel.next = sentinel.next.next;
                sentinel.next.next.prev = sentinel;
            }
        }
        //Display the updated list after removing an element - dequeue. This is requested by the lab task
        System.out.println(displayBrackets());
    }
    /**
     *     Use a string builder because of the special output required from the lab task.
     *     This is requested by the lab task
     */
    public String displayBrackets() {
        if (sentinel == null) {
            return "[]";
        }
        Node node = sentinel.next;
        int i = 0;
        StringBuilder sb = new StringBuilder();

        while (i < numberOfNodes) {
            sb.append("[");
            sb.append(node.item.toString());
            sb.append("],");
            node = node.next;
            i++;
        }
        //Don't place ',' after the last element
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
