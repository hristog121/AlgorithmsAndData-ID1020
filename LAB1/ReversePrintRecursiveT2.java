import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class ReversePrintRecursiveT2 {
    public static void main(String[] args) {
        System.out.println("Enter a string of chars");
        recursiveFunction();
    }


    private static void recursiveFunction() {
        char input = StdIn.readChar();
        if (input != '\n') {
            recursiveFunction();
            StdOut.print("[" + input + "],");
        }


    }

}
