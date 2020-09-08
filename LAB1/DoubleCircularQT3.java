import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleCircularQT3<Item> implements Iterable {
    //Main method to test both adding and removing from the list
    public static void main(String[] args) {
        DoubleCircularQT3<Character> list = new DoubleCircularQT3<>();
        System.out.println("Testing add to back");
        list.addToQ('a');
        list.addToQ('c');
        list.addToQ('b');
       // list.addToQ('e');
       // list.addToQ('d');
        System.out.println();

        list.printAll();
        System.out.println();

        System.out.println("Testing the iterator:");
        Iterator iterator =  list.iterator();
        while (iterator.hasNext()){
            System.out.print( "[" + iterator.next() + "]");
        }
        System.out.println();
        System.out.println();

        System.out.println("Testing remove from front");
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        System.out.println();

        System.out.println("Testing the iterator:");
        Iterator iterator1 =  list.iterator();
        while (iterator1.hasNext()){
            System.out.print( "[" + iterator1.next() + "]");
        }
        //iterator1.next();
    }

    //Sentinel element
    private Node<Item> sentinel;
    //Size of the list - used for the 'remove' operation
    public static int numberOfNodes;

    /*Helper method to understand how the nodes are linked with each other.
      The method can be used after each insertion which will help with debugging
      if that is needed (It was needed).
      If you want to use the method just uncomment below and uncomment in main

    */
    private void printAll() {
        Node print = sentinel.next;
        boolean visited = false;

        while (print != null && (print != sentinel.next || (print == sentinel.next && !visited))) {
            if (print == sentinel.next) {
                visited = true;
            }
            System.out.print("Item :" + print.item);
            System.out.print(" Prev :" + print.prev.item);
            System.out.println(" Next :" + print.next.item);
            print =  print.next;
        }
    }
    /*Iterator implementation. Used to iterate through the list.

    */
    private static class DCLLIterator<Item> implements Iterator<Item>{
        private Node<Item> current;
        private Node<Item> root;
        private boolean rootVisited = false;
        public DCLLIterator(Node root) {
            this.root = root;
            this.current = root;
        }

        @Override
        public boolean hasNext() {
            if (current == null || (current == root && rootVisited)){
                return false;
            }
            return true;
        }

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
    //Remove element from the front of the Q
    public void removeFromFront(){
        if (sentinel == null){
            System.out.println("The queue is empty!");
        } else {
            // Decrease the counter because a node will be removed
            numberOfNodes--;
            if (sentinel == sentinel.next && sentinel == sentinel.prev) {
                sentinel = null;
            } else {
                sentinel.next = sentinel.next.next;
                sentinel.next.next.prev = sentinel;
            }
        }
        //Display the updated list after removing an element - dequeue
        System.out.println(displayBrackets());
    }

    //Use a string builder because of the special output required from the lab task
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
