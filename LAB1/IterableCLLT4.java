


import java.util.Iterator;

public class IterableCLLT4<Item> implements Iterable<Item> {


    public static void main(String[] args) {
        IterableCLLT4<Character> list = new IterableCLLT4<Character>();
        list.addToQFront('a');
        list.addToQFront('b');
        list.addToQFront('c');

        System.out.println("After inserting elements from the front");
        System.out.println(list.displayBrackets());
        System.out.println("Testing the iterator");
        Iterator lala = list.iterator();
        while (lala.hasNext()) {
            System.out.print("[" + lala.next() + "]");
        }
        //list.removeFromFront();
        list.removeFromFront();
        System.out.println();
        System.out.println("After removing from the back");
        System.out.println(list.displayBrackets());

    }

    private Node<Item> head; // --> first
    private Node tail; // --> last
    private int numberOfNodes;

    public IterableCLLT4() {
        head = null;
        tail = null;
        numberOfNodes = 0;
    }

    private class CLLIterator<Item> implements Iterator<Item> {
        private Node<Item> root = (Node<Item>) head;
        private boolean rootVisited = false;


        @Override
        public boolean hasNext() {
            if (rootVisited) {
                return false;
            }
            return true;
        }

        @Override
        public Item next() {
            if (root.next == head) {

                rootVisited = true;

            }
            Item item = root.item;
            root = root.next;
            return item;
        }


    }


    @Override
    public Iterator<Item> iterator() {
        return new CLLIterator();
    }


    private static class Node<Item> {
        // prev is not needed because the list is singly linked
        Item item;
        Node next;
        Node prev;
    }

    private boolean isEmpty() {
        return numberOfNodes == 0;
    }

    public int getSize() {
        return this.numberOfNodes;
    }

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
    }

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

        head.prev = tail;
        numberOfNodes++;
    }

    public void removeFromFront() {

        if (isEmpty()) {
            System.out.println("The queue is empty!");
        } else {
            head = head.next;
            tail.next = head;
        }
        numberOfNodes--;
    }

    public void removeFromBack() {
        Node<Item> oldNode = head;
        if (oldNode.next.next != head) {
            oldNode = oldNode.next;
        }
        tail = oldNode;
        tail.next = head;
        if (isEmpty()) {
            System.out.println("The queue is empty!");
        }
        numberOfNodes--;
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


}



