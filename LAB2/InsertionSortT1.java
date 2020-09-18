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

import java.util.Scanner;

public class InsertionSortT1 {

    public static void main(String a[]) {
        System.out.println("Maximum number of inputs: ");
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
    }

    /**
     * Implementation of the insertion sort algorithm. The methods takes an array of integers and sorts them
     * The integer with lowest value is on the lowest index
     *
     * @param inputArray
     */
    public static void insertionSort(int inputArray[]) {
        int j, temp;
        //int swaps = 0;
        //start the for loop for iterating the array elements in the array
        for (int i = 1; i < inputArray.length; i++) {
            //stores the value at index at i int temp
            temp = inputArray[i];
            //assign i-1 to j
            j = i - 1;
            //while loop iterates upto j>i-1 and inputArray[j]>temp are true
            while ((j > -1) && (inputArray[j] > temp)) {
                inputArray[j + 1] = inputArray[j];

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
