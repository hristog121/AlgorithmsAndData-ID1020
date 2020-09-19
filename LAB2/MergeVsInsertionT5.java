import java.util.Arrays;
import java.util.Random;

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

public class MergeVsInsertionT5 {
    //Insertion sort section plus time test
    public static void main(String a[]) {


        //Generate random numbers and put the in the array
        Random random = new Random();

        int[] inputArray = random.ints(200000, 1, 200001).toArray();
        //System.out.println(Arrays.toString(inputArray));
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);
        int[] arr3 = Arrays.copyOf(inputArray, inputArray.length);

        System.out.println("The size of the array is: " + inputArray.length);
        System.out.println();

       System.out.println("Start sorting with Insertion Sort ...");

        long start = System.currentTimeMillis();

        insertionSort(arr1,0,arr1.length);

        long end = System.currentTimeMillis();
        long mili = (end - start);
        System.out.println("This is the time it took to sort with Insertion sort: " + mili + " milliseconds.");
        System.out.println();

        //Set up for mergesort testing with CUTOFF and Without CUTOFF
        MergeSort m = new MergeSort();


        System.out.println("Start sorting with Merge Sort without CUTOFF ...");
        long start2 = System.currentTimeMillis();

        m.sortNoCutOff(arr2, 0, arr2.length - 1);

        long end2 = System.currentTimeMillis();
        long mili2 = (end2 - start2);
        System.out.println();
        System.out.println("This is the time it took to sort with MergeSort without CUTOFF: " + mili2 + " milliseconds.");
        System.out.println("Start sorting with Merge Sort with CUTOFF ...");
        System.out.println();



        long start3 = System.currentTimeMillis();
        m.sortCutOff(arr3, 0, arr3.length - 1);

        long end3 = System.currentTimeMillis();
        long mili3 = (end3 - start3);
        System.out.println("This is the time it took to sort with MergeSort with CUTOFF : " + mili3 + " milliseconds.");
        System.out.println();

    }

    /**
     * Implementation of the insertion sort algorithm. The methods takes an array of integers and sorts them
     * The integer with lowest value is on the lowest index
     *
     * @param inputArray
     */
    public static void insertionSort(int inputArray[] ,int lo, int hi) {
        int j, temp;

        //start the for loop for iterating the array elements in the array
        for (int i = lo+1; i < hi; i++) {
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

        }
    }



    //topdown mergesord
    public static class MergeSort {
        private static final int CUTOFF = 5;

        void merge(int inputArray[], int lo, int mid, int hi) {
            // Creating temporary subarrays
            int leftArray[] = new int[mid - lo + 1];
            int rightArray[] = new int[hi - mid];

            // Copying our subarrays into temporaries
            for (int i = 0; i < leftArray.length; i++)
                leftArray[i] = inputArray[lo + i];
            for (int i = 0; i < rightArray.length; i++)
                rightArray[i] = inputArray[mid + i + 1];

            // Iterators containing current index of temp subarrays
            int indexLeft = 0;
            int indexRight = 0;

            // Copying from leftArray and rightArray back into array
            for (int i = lo; i < hi + 1; i++) {
                // If there are still uncopied elements in R and L, copy minimum of the two
                if (indexLeft < leftArray.length && indexRight < rightArray.length) {
                    if (leftArray[indexLeft] < rightArray[indexRight]) {
                        inputArray[i] = leftArray[indexLeft];
                        indexLeft++;
                    } else {
                        inputArray[i] = rightArray[indexRight];
                        indexRight++;
                    }
                } else if (indexLeft < leftArray.length) {
                    // If all elements have been copied from rightArray, copy rest of leftArray
                    inputArray[i] = leftArray[indexLeft];
                    indexLeft++;
                } else if (indexRight < rightArray.length) {
                    // If all elements have been copied from leftArray, copy rest of rightArray
                    inputArray[i] = rightArray[indexRight];
                    indexRight++;
                }
            }
        }

        void sortNoCutOff(int inputArray[], int lo, int hi) {
            if (lo < hi) {
                //Put the cut of here
                int mid = lo + (hi - lo) / 2;
                sortNoCutOff(inputArray, lo, mid);
                sortNoCutOff(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }
        }

        void sortCutOff(int inputArray[], int lo, int hi) {
            if (hi-lo + 1 <=  CUTOFF ) {
                //Put the cut of here
                insertionSort(inputArray,lo,hi);

            } else if(lo < hi){
                int mid = (hi + lo) / 2;
                sortCutOff(inputArray, lo, mid);
                sortCutOff(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }

        }
    }


}