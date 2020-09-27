import java.util.ArrayList;
import java.util.Scanner;

public class IndexWordT4 {
    // checks if input is alphabetical (just a shorthand)
    private static boolean isAlpha(char c) {
        return Character.isLetter(c);
    }

    // main
    public static void main(String[] args) {
        String target = args[0];    // takes first input as target word

        ArrayList<Integer> distance = new ArrayList<>();
        int amount = 0;
        char c;
        StringBuilder s = new StringBuilder();
        while(!StdIn.isEmpty()) {   // goes through all the chars
            c = StdIn.readChar();
            amount++;       // tracks how many chars have passed
            if(isAlpha(c))      // if it's an alphabetical char it's added to a StringBuilder
                s.append(c);
            else if(s.toString() != "") {   // if there's a string and current char isn't alphabetical
                if(s.toString().equals(target))     // checks if string is the target
                    distance.add(amount - s.length());  // saves the distance if it is
                s = new StringBuilder();        // deletes the string
            }
        }

        for(int val : distance)     // prints all distances from start
            System.out.println("Distance in chars: " + val);
    }
}
