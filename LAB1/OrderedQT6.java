import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedQT6 {


    public static void main(String[] args) {
        OrderedQT6 list = new OrderedQT6();
        System.out.println("Queue just created");
        System.out.println(list.displayBrackets());
        System.out.println();
        list.addToQSorted(5);
        list.addToQSorted(7);
        list.addToQSorted(3);
        list.addToQSorted(4);
        list.addToQSorted(6);
        System.out.println(list.displayBrackets());


        //Iterator<Integer> emptyIterator = list.iterator();
//        System.out.println(emptyIterator.hasNext());
//        try {
//            emptyIterator.next();
//        } catch (NoSuchElementException ex) {
//            System.out.println("Queue is empty - iterator throws NoSuchElementException");
//        }
//        System.out.println();

        // Add to front
  /*      System.out.println("Add to front");
        list.addToQFront('a');
        list.addToQFront('b');
        list.addToQFront('c');
        System.out.println();*/

        // Add to back
/*        System.out.println("Add to back");
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
        }*/


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

    private OrderedQT6.Node head; // --> first
    private OrderedQT6.Node tail; // --> last
    private int numberOfNodes;

    //Constructor for the list. Set initial number of nodes to 0
    public OrderedQT6() {
        head = null;
        tail = null;
        numberOfNodes = 0;
    }


    private boolean isEmpty() {
        return tail == null && head == null;
    }

    public void addToQSorted(int item) {
        if (isEmpty() || item <= head.item) {
            addToQFront(item);
        } else if (item >= tail.item) {
            addToQBack(item);
        } else {
            Node sentinel = head;
            while (item > sentinel.next.item) {
                sentinel = sentinel.next;
            }
            Node addMe = new Node();
            addMe.item = item;
            addMe.next = sentinel.next;
            sentinel.next = addMe;
        }
        numberOfNodes++;
        System.out.println(displayBrackets());

    }

    //Add element to the back of the Q
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

        //System.out.println(displayBrackets());
    }

    //Add elements to the front of the Q
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


        //System.out.println(displayBrackets());
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

        OrderedQT6.Node nextToLast = head;
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

