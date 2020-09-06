import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;
import java.util.Scanner;

public class ReversePrintIterativeT2 {
   public static void main(String[] args) {

        System.out.println("Enter a string of chars");
        char input;

        //Initialize the stack
        InputStackResizing charStack = new InputStackResizing();
        //Take the input from the terminal
        while ((input = StdIn.readChar()) != '\n'){
            charStack.push(input);
        }

        //Check if the stack is empty. If not keep popping / printing
        while (!charStack.isEmpty()){
            System.out.print(charStack.pop());
        }

    }

    //ADT - Stack implemented with arrays
    static private class InputStackResizing {
        private char[] inputStack;
        private int numberOfItems = 0;
        //Set a default capacity. The user can change it only here if needed.
        private static final int DEFAULT_CAPACITY = 7;

        public InputStackResizing() {
            inputStack = new char[DEFAULT_CAPACITY];
        }

        //Push on the stack
        public void push(char in){
            if (numberOfItems == inputStack.length){
                extendArray();
            }
            inputStack[numberOfItems] = in;
            numberOfItems ++;
        }

        //Pop from the stack - Returns "String" because of the brackets and commas.
        //This the desired output of the lab task
        public String pop(){
            //may be do it like the push function.
            numberOfItems--;
            //Put ',' until the number of items are bigger then 0. This is to remove the comma after
            //the last element ']'

            if (numberOfItems > 0){
                return "[" +  inputStack[numberOfItems] + "],";
            }

            return "[" +  inputStack[numberOfItems] + "]";
        }

        //A method to increase capacity - Double the current size of the array
        private void extendArray(){
            int newCapacity = inputStack.length * 2;
            inputStack = Arrays.copyOf(inputStack, newCapacity);
        }
        //Check if the stack is empty
        boolean isEmpty() { return numberOfItems == 0; }
        int size() { return numberOfItems; }

    }
}