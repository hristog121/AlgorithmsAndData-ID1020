import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of generic iterable circular linked list.
 * The implementation contains methods to:
 * addToQBack
 * addToQFront
 * removeFromQBack
 * removeFromQFront
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */

public class IterableCLLT4<Item> implements Iterable<Item> {

    public static void main(String[] args) {
        IterableCLLT4<Character> list = new IterableCLLT4<Character>();
        System.out.println("Queue just created");
        System.out.println(list.displayBrackets());
        System.out.println();

        System.out.println("Testing empty iterator");
        Iterator<Character> emptyIterator = list.iterator();
        System.out.println(emptyIterator.hasNext());
        try {
            emptyIterator.next();
        } catch (NoSuchElementException ex) {
            System.out.println("Queue is empty - iterator throws NoSuchElementException");
        }
        System.out.println();

        // Add to front
        System.out.println("Add to front");
        list.addToQFront('a');
        list.addToQFront('b');
        list.addToQFront('c');
        System.out.println();

        // Add to back
        System.out.println("Add to back");
        list.addToQBack('d');
        list.addToQBack('e');
        list.addToQBack('f');
        System.out.println();

        // Remove from front
        System.out.println("Remove from front");
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        System.out.println();

        // Testing remove from back
        System.out.println("Add elements");
        list.addToQFront('a');
        list.addToQFront('b');
        list.addToQFront('c');
        list.addToQBack('d');
        list.addToQBack('e');
        list.addToQBack('f');
        System.out.println();

        System.out.println("Remove from back");
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        System.out.println();

        // Testing the iterator
        System.out.println("Add elements");
        list.addToQFront('a');
        list.addToQFront('b');
        list.addToQFront('c');
        list.addToQBack('d');
        list.addToQBack('e');
        list.addToQBack('f');
        System.out.println();

        System.out.println("Testing the iterator");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print("[" + iterator.next() + "]");
        }
        System.out.println();
        System.out.println();
        System.out.println(iterator.hasNext());
        try {
            iterator.next();
        } catch (NoSuchElementException ex) {
            System.out.println("Queue is empty - iterator throws NoSuchElementException");
        }


    }

    /*Helper method to understand how the nodes are linked with each other.
      The method can be used after each insertion which will help with debugging
      if that is needed (It was needed).
      If you want to use the method just uncomment below and uncomment in main

 */
/*    private void printAll() {
        Node print = head;
        boolean visited = false;

        while (print != null && (print != head || (print == head && !visited))) {
            if (print == head) {
                visited = true;
            }
            System.out.print("Item :" + print.item);
            System.out.println(" Next :" + print.next.item);
            print = print.next;
        }
    }*/

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

    private boolean isEmpty() {
        return tail == null && head == null;
    }

    //Add element to the back of the Q
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

    //Add elements to the front of the Q
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

    //Remove elements from the front of the Q
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

        head = head.next;
        tail.next = head;

        numberOfNodes--;
        System.out.println(displayBrackets());
    }

    //Remove elements from the back of the Q
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
        while (nextToLast.next != tail) {
            nextToLast = nextToLast.next;
        }
        nextToLast.next = head;
        tail = nextToLast;

        numberOfNodes--;
        System.out.println(displayBrackets());
    }

    //Use a string builder because of the special output required from the lab task
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
     *
     * item -> Generic type - It can be Integer, Character ..
     * next -> Reference to the next node
     *
     */
    private static class Node<Item> {
        // prev is not needed because the list is singly linked
        Item item;
        Node next;
    }

    private static class CLLIterator<Item> implements Iterator<Item> {
        private Node<Item> head;
        private Node<Item> current;
        private boolean rootVisited = false;

        public CLLIterator(final Node<Item> head) {
            this.head = head;
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            if (current == null || rootVisited) {
                return false;
            }
            return true;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            if (current == head && rootVisited) {
                throw new NoSuchElementException();
            }
            if (current.next == head) {
                rootVisited = true;
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}



