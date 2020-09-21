import java.util.Arrays;
import java.util.Random;

/**
 * This class contains three different algorithms.
 * Insertion Sort - modified to take lo and hi as arguments because it is used in the cutOFF version of MergeSort
 * Merge Sort without CutOff
 * Merge Sort with CutOff to Insertion Sort
 * The goal is to showcase and compare the execution times between the three algorithms. The results are given in ms.
 *
 * @author Hristo Georgiev - 1c3r00t
 **/

public class MergeVsInsertionT5andT6 {
    /**
     * Main function for testing purposes. The output of the main function will cover all algorithms.
     * The algorithms are tested on a randomly generated array with a chosen size.
     * When the array is generated all the algorithms are tested on the same array so comparison can be made by graphing
     * the results.
     *
     * @param a
     */
    //Insertion sort section plus time test
    public static void main(String a[]) {

        // TEST OF THE SORTING WORKS - START
        int[] testArra1 = {1, 2, 4, 3, 5, 0};
        int[] testArra2 = {1, 2, 4, 3, 5, 0};
        MergeSort testMerge = new MergeSort(0);
        testMerge.sortNoCutOff(testArra1, 0, testArra1.length - 1);
        testMerge.sortCutOff(testArra2, 0, testArra2.length - 1);
        System.out.println();
        System.out.println("MergeSort no CutOFF");
        System.out.println("On input [1, 2, 4, 3, 5, 0] expected result is: [0, 1, 2, 3, 4, 5].");
        System.out.println();
        System.out.println("This is the array after sorting: " + Arrays.toString(testArra1));
        System.out.println();
        System.out.println("MergeSort With CutOFF");
        System.out.println("On input [1, 2, 4, 3, 5, 0] expected result is: [0, 1, 2, 3, 4, 5].");
        System.out.println();
        System.out.println("This is the array after sorting: " + Arrays.toString(testArra2));
        // TEST OF THE SORTING WORKS - START
        Random random = new Random();
        //StringBuilder sb = new StringBuilder();

        int minElements = 100;
        int maxElements = 1000000;
        //Change this value - the amount of different arrays tested
        int countTestArrays = 1;

        int minMergeCutOff = 0;
        int maxMergeCutOff = 30;
        int mergeCutOffIncrement = 5;

        // Display header
        System.out.printf("countElements,insertionMid,mergeNoCutMid");
        for (int i = minMergeCutOff; i <= maxMergeCutOff; i += mergeCutOffIncrement) {
            System.out.printf(",mergeCut%dMid", i);
        }
        System.out.printf("\n");
        for (int countElements = minElements; countElements <= maxElements; ) {

            // Control variants of arrays
            double[] resultsInsertion = new double[countTestArrays];
            double[] resultsMerge = new double[countTestArrays];
            double[][] resultsMergeWithCut = new double[maxMergeCutOff + 1][countTestArrays];
            for (int j = 0; j < countTestArrays; j++) {
                int[] inputArray = random.ints(countElements, 1, countElements + 1).toArray();
                resultsInsertion[j] = timeInsertion(inputArray);
                resultsMerge[j] = timeMerge(inputArray);

                for (int k = minMergeCutOff; k <= maxMergeCutOff; k += mergeCutOffIncrement) {
                    resultsMergeWithCut[k][j] = timeMerge(inputArray, k);
                }
            }
            System.out.printf("%d,%.5f,%.5f", countElements, calculateMid(resultsInsertion), calculateMid(resultsMerge));
            for (int i = minMergeCutOff; i <= maxMergeCutOff; i += mergeCutOffIncrement) {
                System.out.printf(",%.5f", calculateMid(resultsMergeWithCut[i]));
            }
            System.out.printf("\n");

            countElements += getIncrement(countElements);
        }

    }

    /**
     * @return [100-1000] -> 100;
     * [1000 - 10000] -> 1000;
     * [10000 - 100000] -> 10000;
     * [100000 - 1000000] -> 100000;
     */
    private static int getIncrement(int countElements) {
        if (countElements < 1000)
            return 100;
        if (countElements < 10000)
            return 1000;
        if (countElements < 100000)
            return 10000;
        if (countElements < 1000000)
            return 100000;
        throw new UnsupportedOperationException("Please enter a number between 100 and 1000000");
    }

    //Calculate The middle value
    private static double calculateMid(double[] results) {
        double mid = 0;
        for (int j = 0; j < results.length; j++) {
            mid = mid + results[j];
        }
        mid = mid / results.length;
        return mid;
    }

    //Function to time Insertion Sort
    public static double timeInsertion(int inputArray[]) {
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        long start = System.nanoTime();
        insertionSort(arr1, 0, inputArray.length);
        long end = System.nanoTime();
        return (end - start) / 1000000.0;
    }

    //Function to time merge sort with NO cut off.
    public static double timeMerge(int inputArray[]) {
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);
        MergeSort m = new MergeSort(0);

        long start2 = System.nanoTime();
        m.sortNoCutOff(arr2, 0, arr2.length - 1);
        long end2 = System.nanoTime();
        return (end2 - start2) / 1000000.0;
    }

    //Function to time merge sort with cut off. The cutoff is given as parameter to the func
    private static double timeMerge(int inputArray[], int cutOff) {
        int[] arr3 = Arrays.copyOf(inputArray, inputArray.length);
        MergeSort m = new MergeSort(cutOff);
        long start3 = System.nanoTime();
        m.sortCutOff(arr3, 0, arr3.length - 1);
        long end3 = System.nanoTime();
        return (end3 - start3) / 1000000.0;
    }


    /**
     * Implementation of the insertion sort algorithm. The methods takes an array of integers and sorts them
     * The integer with lowest value is on the lowest index. This is a modified version of Insertion Sort to take
     * lo and hi as arguments. This is done because this algorithm is used in the CutOff version of Merge Sort
     *
     * @param inputArray
     */
    public static void insertionSort(int inputArray[], int lo, int hi) {
        int j, temp;

        //start the for loop for iterating the array elements in the array
        for (int i = lo + 1; i < hi; i++) {
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


    /**
     * Implementation of Merge Sort. This implementation contains both the "normal" version and the version with
     * a CutOff to Insertion Sort
     */
    public static class MergeSort {
        //variable to control the size of the cut off
        private final int cutOff;

        public MergeSort(final int cutOff) {
            this.cutOff = cutOff;
        }

        //Merging method
        void merge(int inputArray[], int lo, int mid, int hi) {
            // Creating temporary subarrays
            int leftArr[] = new int[mid - lo + 1];
            int rightArr[] = new int[hi - mid];

            // Copying our subarrays into temporaries
            for (int i = 0; i < leftArr.length; i++)
                leftArr[i] = inputArray[lo + i];
            for (int i = 0; i < rightArr.length; i++)
                rightArr[i] = inputArray[mid + i + 1];

            // Containing current index of temp sub arrays
            int iLeft = 0;
            int iRight = 0;

            // Copying from leftArr and rightArr back into array
            for (int i = lo; i < hi + 1; i++) {
                // If there are still un copied elements in rightArr and leftArr, copy minimum of the two
                if (iLeft < leftArr.length && iRight < rightArr.length) {
                    if (leftArr[iLeft] < rightArr[iRight]) {
                        inputArray[i] = leftArr[iLeft];
                        iLeft++;
                    } else {
                        inputArray[i] = rightArr[iRight];
                        iRight++;
                    }
                } else if (iLeft < leftArr.length) {
                    // If all elements have been copied from rightArr, copy rest of leftArr
                    inputArray[i] = leftArr[iLeft];
                    iLeft++;
                } else if (iRight < rightArr.length) {
                    // If all elements have been copied from leftArr, copy rest of rightArr
                    inputArray[i] = rightArr[iRight];
                    iRight++;
                }
            }
        }

        // Sort with NO CutOff Merge Sort
        void sortNoCutOff(int inputArray[], int lo, int hi) {
            if (lo < hi) {
                //Put the cut of here
                int mid = (lo + hi) / 2;
                sortNoCutOff(inputArray, lo, mid);
                sortNoCutOff(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }
        }

        // Sort with with CutOff Merge Sort
        void sortCutOff(int inputArray[], int lo, int hi) {
            if ((hi - lo + 1) <= cutOff) {
                //use insertion sort
                insertionSort(inputArray, lo, hi);
            } else if (lo < hi) {
                int mid = (hi + lo) / 2;
                sortCutOff(inputArray, lo, mid);
                sortCutOff(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }
        }
    }
}