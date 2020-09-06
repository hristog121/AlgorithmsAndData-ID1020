import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class ReversePrintRecursiveT2 {
    public static void main(String[] args) {
        System.out.println("Enter a string of chars");
        recursiveFunction();
    }


    static private class InputStackResizing {
        private char[] inputStack;
        private int numberOfItems = 0;
        //Set a default capacity. The user can change it only here if needed.
        private static final int DEFAULT_CAPACITY = 7;

        public InputStackResizing() {
            inputStack = new char[DEFAULT_CAPACITY];
        }

        //Push on the stack
        public void push(char in) {
            if (numberOfItems == inputStack.length) {
                extendArray();
            }
            inputStack[numberOfItems] = in;
            numberOfItems++;
        }

        //Pop from the stack - Returns "String" because of the brackets and commas.
        //This the desired output of the lab task
        public char pop() {
            //may be do it like the push function.
            numberOfItems--;
            //Put ',' until the number of items are bigger then 0. This is to remove the comma after
            //the last element ']'


            if (numberOfItems > 0) {
                return inputStack[numberOfItems];
            }

            return inputStack[numberOfItems];
        }

        //A method to increase capacity - Double the current size of the array
        private void extendArray() {
            int newCapacity = inputStack.length * 2;
            inputStack = Arrays.copyOf(inputStack, newCapacity);
        }

        //Check if the stack is empty
        boolean isEmpty() {
            return numberOfItems == 0;
        }

        int size() {
            return numberOfItems;
        }

        int stackLength() {
            return inputStack.length;
        }

    }


    private static void recursiveFunction() {
        InputStackResizing newStack = new InputStackResizing();

        char input = StdIn.readChar();
        if (input != '\n') {
            newStack.push(input);
            recursiveFunction();
        }
        //Condition to limit the printing of the brackets
        if (!newStack.isEmpty()) {
            if (newStack.stackLength() > 0) {
                System.out.print("[" + input + "],");
            } if (newStack.stackLength()-1 == 0){
                System.out.print("[" + input + "]");
            }




        }

    }

}
