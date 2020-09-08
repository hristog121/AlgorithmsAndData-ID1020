

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of generic iterable circular linked list.
 * The implementation contains methods to:
 * addToQBack - adds to the back of the list
 * addToQFront - adds to the front of the list
 * removeFromQBack - removes from the back of the list
 * removeFromQFront - removes from the front of the list
 *
 * @param <Item>
 * @author Hristo Georgiev - 1c3r00t
 */

public class IterableCLLT4<Item> implements Iterable<Item> {

    //Declaration of pointers 'head' and 'tail'.
    private Node<Item> head; // --> first
    private Node<Item> tail; // --> last
    private int numberOfNodes;

    //Constructor for the list. Set initial number of nodes to 0
    public IterableCLLT4() {
        head = null;
        tail = null;
        numberOfNodes = 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new CLLIterator(head);
    }

    //Check if the queue is empty
    private boolean isEmpty() {
        return tail == null && head == null;
    }

    //Add element to the back of the list
    public void addToQBack(Item item) {
        Node<Item> oldNode = tail;
        tail = new Node<Item>();
        tail.item = item;

        if (isEmpty()) {
            head = tail;
            head.next = head;
        } else {
            oldNode.next = tail;
        }
        tail.next = head;
        numberOfNodes++;
        System.out.println(displayBrackets());
    }

    //Add elements to the front of the list
    public void addToQFront(Item item) {
        Node<Item> oldNode = head;
        head = new Node<Item>();
        head.next = oldNode;
        head.item = item;

        if (tail == null) {
            tail = head;
        } else {
            tail.next = head;
        }

        numberOfNodes++;
        System.out.println(displayBrackets());
    }

    //Remove elements from the front of the list
    public void removeFromFront() {
        if (isEmpty()) {
            System.out.println("The queue is empty!");
            return;
        }
        if (head == tail) {
            head = null;
            tail = null;
            numberOfNodes = 0;
            System.out.println(displayBrackets());
            return;
        }
        //Remove the reference to the first element in the list (Will disappear after the garbage collector swips)
        head = head.next;
        tail.next = head;

        numberOfNodes--;
        System.out.println(displayBrackets());
    }

    //Remove elements from the back of the list
    public void removeFromBack() {
        if (isEmpty()) {
            System.out.println("The queue is empty!");
            return;
        }

        if (head == tail) {
            head = null;
            tail = null;
            numberOfNodes = 0;
            System.out.println(displayBrackets());
            return;
        }
        Node nextToLast = head;
        //Find the nextToLast element
        while (nextToLast.next != tail) {
            nextToLast = nextToLast.next;
        }
        //Remove the reference to the last element in the list (Will disappear after the garbage collector swips)
        nextToLast.next = head;
        tail = nextToLast;

        numberOfNodes--;
        System.out.println(displayBrackets());
    }

    /**
     * Use a string builder because of the special output required from the lab task.
     * This is requested by the lab task
     */
    public String displayBrackets() {
        IterableCLLT4.Node node = head;
        int i = 0;
        if (isEmpty()) {
            return "[]";
        }
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

    /**
     * item -> Generic type - It can be Integer, Character ..
     * next -> Reference to the next node
     */
    private static class Node<Item> {
        // prev is not needed because the list is singly linked
        Item item;
        Node next;
    }

    //Iterator implementation to make it iterable - per task request
    private static class CLLIterator<Item> implements Iterator<Item> {
        private Node<Item> head;
        private Node<Item> current;
        private boolean rootVisited = false;

        //Constructor for the iterator
        public CLLIterator(final Node<Item> head) {
            this.head = head;
            //To keep track where we are in the list
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            //Check if there is more elements
            if (current == null || (current == head && rootVisited)) {
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
            //If we are in the end and the first element has been visited
            if (current == head && rootVisited) {
                throw new NoSuchElementException();
            }
            //Mark root as visited if the condition is met
            if (current == head) {
                rootVisited = true;
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}



