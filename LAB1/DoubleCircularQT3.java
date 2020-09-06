import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class DoubleCircularQT3<Item> implements Iterable {
    //Main method to test both adding and removing from the list
    public static void main(String[] args) {
        DoubleCircularQT3<Character> list = new DoubleCircularQT3<>();
        list.addToQ('a');
        list.addToQ('c');
        list.addToQ('b');
        list.removeFromFront();
    }

    //Sentinel element
    private Node head;
    //Size of the list - used for the 'remove' operation
    public static int numberOfNodes;

    public DoubleCircularQT3() {
        head = new Node();
        head.next = head;
        head.prev = head;
    }

    @Override
    public Iterator iterator() {
        return null;
    }


    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private boolean isEmpty() {
        return numberOfNodes == 0;
    }

    public void addToQ(Item item) {
        Node oldNode = new Node();
        oldNode.item = item;
        numberOfNodes++;
        if (head == null) {
            //Loop the pointers
            oldNode.next = oldNode.prev = oldNode;
            System.out.println("\nElement: [" + item.toString() + "] has been enqueued");
            return;
        }

        oldNode.next = head.next;
        oldNode.prev = head;
        head.next = oldNode;
        oldNode.next.prev = oldNode;
        StdOut.println(displayBrackets());
    }

    public void removeFromFront(){
        if (numberOfNodes <= 0){
            System.out.println("The queue is empty!");
        } else {
            // Decrease the counter because a node will be removed
            numberOfNodes--;
            Item item = head.prev.item;
            head.prev.prev.next = head;
            head.prev = head.prev.prev;
        }
        System.out.println(displayBrackets());
    }

    //Use a string bulder because of the special output required from the lab task
    public String displayBrackets() {
        Node node = head.prev;
        int i = 0;
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();

        while (i < numberOfNodes) {
            sb.append("[");
            sb.append(node.item.toString());
            sb.append("],");
            node = node.prev;
            i++;
        }
        //Don't place ',' after the last element
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
