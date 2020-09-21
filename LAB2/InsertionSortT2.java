
/**
 * This is an implementation of Insertion Sort. The user inputs first the size of the array
 * and inputs ints one by one while pressing enter in between.
 * The implementation contains methods to:
 * insertionSort - sorts array on ints by using insertion sort.
 * displayOutput - Iterates through the inputArray and displays the values. This is combined with
 * a pront statement in the insertionSort method to get the desired output for the lab.
 *
 * @author Hristo Georgiev - 1c3r00t
 **/
import java.util.Arrays;
import java.util.Scanner;

public class InsertionSortT2 {

    public static void main(String a[]) {
        System.out.println("What size array do you want to sort? ");
        Scanner sc = new Scanner(System.in);

        //Take the size of the array from user input - comes from the task
        int inputArraySize = sc.nextInt();
        System.out.println("Write your integer and press ENTER: ");
        //init array to store the user input
        int inputArray[] = new int[inputArraySize];

        //read user input < inputArraySize array inputArray
        for (int i = 0; i < inputArraySize; i++) {
            inputArray[i] = sc.nextInt();
        }
        //call the insertion_sort function by passing the inputArray as parameter
        //The print is done inside of the implementation of the method bellow
        insertionSort(inputArray);
        System.out.println("This is the array after sorting: " + Arrays.toString(inputArray));
        System.out.println("On input [1, 2, 4, 3, 5, 0] expected result is: [0, 1, 2, 3, 4, 5]. Number of swaps: 6" );
    }

    /**
     * Implementation of the insertion sort algorithm. The methods takes an array of integers and sorts them.
     * The integer with lowest value is on the lowest index. This implementation counts the swaps made and give
     * the total amount of swaps. The total amount of swaps are displayed to the user when the sorting is done.
     *
     * @param inputArray
     */
    public static void insertionSort(int inputArray[]) {
        int j, temp;
        //initializing swaps counter and setting it to 0
        int swaps = 0;
        //start the for loop for iterating the array elements in the array
        for (int i = 1; i < inputArray.length; i++) {
            //stores the value at index at i int temp
            temp = inputArray[i];
            //assign i-1 to j
            j = i - 1;
            //while loop iterates upto j>i-1 and inputArray[j]>temp are true
            while ((j > -1) && (inputArray[j] > temp)) {
                inputArray[j + 1] = inputArray[j];
                //A swap is made
                ++swaps;
                j--;
            }
            //store temp values into particular index j+1
            inputArray[j + 1] = temp;
            System.out.print("Iteration " + (i) + " : ");
            //display array values after each inner loop iteration - per lab task
            displayOutput(inputArray);
            //So the iterations are printed on a new line
            System.out.println();
            //Print the number of swaps made after eacg itteration

        }
        //Print the total number of swaps that we get after the final sorting
        System.out.println("Total amount of swaps " + swaps);

    }

    /**
     * Iterates through the inputArray and displays the values.
     *
     * @param inputArray
     */
    public static void displayOutput(int inputArray[]) {
        for (int i = 0; i < inputArray.length; i++) {
            System.out.print(inputArray[i] + " ");
        }

    }
}
