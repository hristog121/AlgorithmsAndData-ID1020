import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class DoubleCircularQT3<Item> implements Iterable {
    //Main method to test both adding and removing from the list
    public static void main(String[] args) {
        DoubleCircularQT3<Character> list = new DoubleCircularQT3<>();
        list.addToQ('a');
        list.addToQ('c');
        list.addToQ('b');
        //list.removeFromFront();
        Iterator lala =  list.iterator();
        while (lala.hasNext()){
            System.out.print( "[" + lala.next() + "]");
        }

    }

    //Sentinel element
    private Node<Item> sentinel;
    //Size of the list - used for the 'remove' operation
    public static int numberOfNodes;

    public DoubleCircularQT3() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

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
            if (current == root && rootVisited){
                return false;
            }
            return true;
        }

        @Override
        public Item next() {
            if (current == root){
                if (rootVisited){
                    return null;
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

        return new DCLLIterator(sentinel.next);


    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    private boolean isEmpty() {
        return numberOfNodes == 0;
    }

    public void addToQ(Item item) {
        Node<Item> oldNode = new Node();
        oldNode.item = item;
        numberOfNodes++;
        if (sentinel == null) {
            //Loop the pointers
            oldNode.next = oldNode.prev = oldNode;
            return;
        }

        oldNode.next = sentinel.next;
        oldNode.prev = sentinel;
        sentinel.next = oldNode;
        oldNode.next.prev = oldNode;
        StdOut.println(displayBrackets());
    }

    public void removeFromFront(){
        if (numberOfNodes <= 0){
            System.out.println("The queue is empty!");
        } else {
            // Decrease the counter because a node will be removed
            numberOfNodes--;
            Item item = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
        }
        System.out.println(displayBrackets());
    }

    //Use a string bulder because of the special output required from the lab task
    public String displayBrackets() {
        Node node = sentinel.prev;
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


}
