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

public class InsertionSortInversionsT3 {
    public static void main(String a[]) {

        System.out.println("What size array do you want to sort? ");
        Scanner sc = new Scanner(System.in);

        //Takes user input for how big the array will be - per task requirements
        int inputArraySize = sc.nextInt();
        System.out.println("Write your integer and press ENTER: ");
        //init array to store the user input
        int inputArray[] = new int[inputArraySize];

        //read user input and feeds it into inputArray to be sorted later
        for (int i = 0; i < inputArraySize; i++) {
            inputArray[i] = sc.nextInt();
        }

        //Print the number of inversions in the desired format plus the total amount (as an integer)
        System.out.println(countInversions(inputArray));
        //call the insertion_sort function by passing the inputArray as parameter
        //The print is done inside of the implementation of the method bellow
        insertionSort(inputArray);
        System.out.println();
        System.out.println("This is the array after sorting: " + Arrays.toString(inputArray));
        System.out.println("On input [1, 2, 4, 3, 5, 0] expected result is: [0, 1, 2, 3, 4, 5]. Number of swaps: 6" );
        System.out.println();
        System.out.println("The expected inversions: [0,1, 5,0] [1,2, 5,0] [2,4, 3,3] [2,4, 5,0] [3,3, 5,0] [4,5, 5,0]");
    }

    /**
     * Implementation of the insertion sort algorithm. The methods takes an array of integers and sorts them
     * The integer with lowest value is on the lowest index
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

        }
        //Print the number of swaps made after eacg iteration
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

    /**
     * Method to count and display the inversions in the array before it is sorted.
     * The format of the display data is - [i,a[i]], [j, a[j]] where i and j are indices and a[i], a[j]
     * are the values of the elements
     * @param inputArray
     * @return
     */
    private static String countInversions(int[] inputArray) {
        // Variable to count the inversions
        int numberOfInversions = 0;
        //Use string builder to present the data as wanted by the task
        System.out.println("Inversions in format: [i,a[i]], [j, a[j]]");
        StringBuilder sb = new StringBuilder();
        //compare every element with the ones after it to see if they are inverted
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = i + 1; j < inputArray.length; j++) {
                if (inputArray[i] > inputArray[j]){
                    numberOfInversions ++;
                    sb.append(String.format("[%d,%d, %d,%d] ", i, inputArray[i], j, inputArray[j]));//record inversion
                }

            }
        }
        // Display total amount of inversions
        sb.append(String.format("Total amount of inversions: %d", numberOfInversions));
        return sb.toString(); //output final string


    }
}
