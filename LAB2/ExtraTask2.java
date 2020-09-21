/**
 * This class contains two different algorithms.
 * Quick Sort
 * Merge Sort without CutOff
 * The goal is to showcase and compare the execution times between the three algorithms. The results are given in ms.
 *
 * @author Hristo Georgiev - 1c3r00t
 **/

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class ExtraTask2 {

    //Insertion sort section plus time test
    public static void main(String a[]) {

        // TEST OF THE SORTING WORKS - START
        int[] testArra1 = {1, 2, 4, 3, 5, 0};
        int[] testArra2 = {1, 2, 4, 3, 5, 0};
        Merge mergeSort = new Merge();
        mergeSort.sortMerge(testArra1, 0, testArra1.length - 1);
        quickSort(testArra2, 0, testArra2.length - 1);
        System.out.println();
        System.out.println("MergeSort no CutOFF");
        System.out.println("On input [1, 2, 4, 3, 5, 0] expected result is: [0, 1, 2, 3, 4, 5].");
        System.out.println();
        System.out.println("This is the array after sorting: " + Arrays.toString(testArra1));
        System.out.println();
        System.out.println("Quick Sort");
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

        // Display header
        System.out.printf("countElements,quickSort,mergeNoCutMid");

        System.out.printf("\n");
        for (int countElements = minElements; countElements <= maxElements; ) {

            // Control variants of arrays
            double[] resultsQuickSort = new double[countTestArrays];
            double[] resultsMerge = new double[countTestArrays];

            for (int j = 0; j < countTestArrays; j++) {
                int[] inputArray = random.ints(countElements, 1, countElements + 1).toArray();
                resultsQuickSort[j] = timeQuickSort(inputArray);
                resultsMerge[j] = timeMerge(inputArray);

            }
            System.out.printf("%d,%.5f,%.5f", countElements, calculateMid(resultsQuickSort), calculateMid(resultsMerge));

            System.out.printf("\n");

            countElements += getIncrement(countElements);
        }
    }

    /**
     * HELPER FUNCTIONS START
     */

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
    public static double timeQuickSort(int inputArray[]) {
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        long start = System.nanoTime();
        quickSort(arr1, 0, inputArray.length - 1);
        long end = System.nanoTime();
        return (end - start) / 1000000.0;
    }

    //Function to time merge sort with NO cut off.
    public static double timeMerge(int inputArray[]) {
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);
        Merge m = new Merge();

        long start2 = System.nanoTime();
        m.sortMerge(arr2, 0, arr2.length - 1);
        long end2 = System.nanoTime();
        return (end2 - start2) / 1000000.0;
    }

    /**
     * HELPER FUNCTIONS END
     */

    //QUICK SORT
    public static void quickSort(int[] inputArray, int start, int end) {
        if (start < end) {
            int pi = partitionArr(inputArray, start, end);
            quickSort(inputArray, start, pi - 1);
            quickSort(inputArray, pi + 1, end);
        }
    }

    //Partitioning of the array and picking pivot element
    private static int partitionArr(int[] inputArray, int lo, int hi) {
        int pivot = inputArray[hi];
        int i = (lo - 1); // index of smaller element
        for (int j = lo; j <= hi - 1; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (inputArray[j] <= pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = inputArray[i];
                inputArray[i] = inputArray[j];
                inputArray[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = inputArray[i + 1];
        inputArray[i + 1] = inputArray[hi];
        inputArray[hi] = temp;

        return i + 1;
    }

    /**
     * Implementation of Merge Sort without CutOff.
     */

    public static class Merge {

        //Merging method
        void merge(int inputArray[], int lo, int mid, int hi) {
            // Creating temporary sub arrays
            int leftArr[] = new int[mid - lo + 1];
            int rightArr[] = new int[hi - mid];

            // Copying our subarrays into temporaries
            for (int i = 0; i < leftArr.length; i++)
                leftArr[i] = inputArray[lo + i];
            for (int i = 0; i < rightArr.length; i++)
                rightArr[i] = inputArray[mid + i + 1];

            // Iterators containing current index of temp sub arrays
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

        // Sort with with CutOff Merge Sort
        void sortMerge(int inputArray[], int lo, int hi) {
            if (lo < hi) {
                int mid = (lo + hi) / 2;
                sortMerge(inputArray, lo, mid);
                sortMerge(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }
        }
    }

}

