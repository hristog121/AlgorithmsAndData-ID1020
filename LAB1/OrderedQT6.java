/**
 * This is an implementation of ordered queue based on circular linked list.
 * The implementation contains methods to:
 * addToQSorted - placing the element on the right place in the list
 * addToQBack - adds to the back of the list
 * addToQFront - adds to the front of the list
 * removeFromQBack - removes from the back of the list
 * removeFromQFront - removes from the front of the list
 * @author Hristo Georgiev - 1c3r00t
 *
 */

public class OrderedQT6 {

    //Declaration of pointers 'head' and 'tail'.
    private OrderedQT6.Node head; // --> first
    private OrderedQT6.Node tail; // --> last
    private int numberOfNodes;

    //Constructor for the list. Set initial number of nodes to 0
    public OrderedQT6() {
        head = null;
        tail = null;
        numberOfNodes = 0;
    }

    //Check if the queue is empty
    private boolean isEmpty() {
        return tail == null && head == null;
    }

    /**
     * Checking what is the right place for the node in the list and inserting it there
     *
     */
    public void addToQSorted(int item) {
        //Check if the queue is empty or item is smaller then the item in head
        if (isEmpty() || item <= head.item) {
            addToQFront(item);
        } else if (item >= tail.item) {
            addToQBack(item);
        } else {
            //Sentinel is used to iterate over the elements in order to find the right place
            Node sentinel = head;
            while (item > sentinel.next.item) {
                sentinel = sentinel.next;
            }
            Node addMe = new Node();
            addMe.item = item;
            //Point the new node to the next node
            addMe.next = sentinel.next;
            sentinel.next = addMe;
        }
        numberOfNodes++;
        System.out.println(displayBrackets());

    }

    //Add element to the back of the list
    public void addToQBack(int item) {
        OrderedQT6.Node oldNode = tail;
        tail = new OrderedQT6.Node();
        tail.item = item;

        if (isEmpty()) {
            head = tail;
            head.next = head;
        } else {
            oldNode.next = tail;
        }
        tail.next = head;

    }

    //Add element to the front of the list
    public void addToQFront(int item) {
        OrderedQT6.Node oldNode = head;
        head = new OrderedQT6.Node();
        head.next = oldNode;
        head.item = item;

        if (tail == null) {
            tail = head;
        } else {
            tail.next = head;
        }

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

        OrderedQT6.Node nextToLast = head;
        while (nextToLast.next != tail) {
            nextToLast = nextToLast.next;
        }
        nextToLast.next = head;
        tail = nextToLast;

        numberOfNodes--;
        System.out.println(displayBrackets());
    }

    /**
     *     Use a string builder because of the special output required from the lab task.
     *     This is requested by the lab task
     */
    public String displayBrackets() {
        OrderedQT6.Node node = head;
        int i = 0;
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();

        while (i < numberOfNodes) {
            sb.append("[");
            sb.append(node.item);
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
    private static class Node {
        // prev is not needed because the list is singly linked
        int item;
        OrderedQT6.Node next;
    }


}

