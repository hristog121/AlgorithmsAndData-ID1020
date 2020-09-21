

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExtraTask3 {

    public void quickSort(int[] inputArray, int start, int end) {
        // Partition array by putting pivot at the right place
        int partition = partitionArr(inputArray,start,end);
        // Sort left part of array
        if (partition - 1 > start) {
            quickSort(inputArray, start, partition);
        }
        // Sort right part of array
        if (partition + 1 < (end - 1)){
            quickSort(inputArray, partition +1, end);
        }
    }

    public void quickSort3Way(int[] inputArray, int start, int end) {
        // Partition array by putting pivot at the right place
        int partition = partition3WayArr(inputArray,start,end);
        // Sort left part of array
        if (partition - 1 > start) {
            quickSort3Way(inputArray, start,partition-1);
        }
        // Sort right part of array
        if (partition +1 < end){
            quickSort3Way(inputArray, partition +1, end);
        }
    }

    private int partitionArr(int[] inputArray, int lo, int hi) {
        // Choose pivot as the lowest element
        int pivot = inputArray[lo];
        int i = lo;
        for (int j = lo + 1; j < hi; j++) {
            // If current element is smaller than the pivot
            if (inputArray[j] < pivot) {
                // swap arr[i] and arr[j]
                i++;
                if (j != i) {
                    int temp = inputArray[i];
                    inputArray[i] = inputArray[j];
                    inputArray[j] = temp;
                }
            }
        }
        if (i != lo) {
            // Put pivot on the right place
            int temp = inputArray[i];
            inputArray[i] = inputArray[lo];
            inputArray[lo] = temp;
        }
        return i;
    }

    private int partition3WayArr(int[] inputArray, int lo, int hi) {
        // Choose pivot as the median of the low, mid and high element
        int[] loMidHi = {inputArray[lo],inputArray[(hi - lo)/2], inputArray[hi]};
        Arrays.sort(loMidHi);

        int pivot = loMidHi[1];
        int pivotMarker = hi;
        int i = (lo - 1);
        for (int j = lo; j <= hi; j++) {
            // If current element is smaller than pivot
            if (inputArray[j] < pivot) {
                i++;

                if (j != i) {
                    // swap arr[i] and arr[j]
                    int temp = inputArray[i];
                    inputArray[i] = inputArray[j];
                    inputArray[j] = temp;
                }
            }
            if (inputArray[j] == pivot) {
                pivotMarker = j;
            }
        }

        // Put pivot on the right place
        int temp = inputArray[i + 1];
        inputArray[i + 1] = inputArray[pivotMarker];
        inputArray[pivotMarker] = temp;
        return i + 1;
    }


    public static void main(String[] args) {

        // Create original list with ordered numbers
        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < 2000000; i++) {
            resultList.add(i);
        }

        // Store original array to compare results
        int[] resultArray = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            resultArray[i] = resultList.get(i);
        }

        // Shuffle original list
        Collections.shuffle(resultList);

        // Store input array with shuffled numbers
        int[] inputArray = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            inputArray[i] = resultList.get(i);
        }

        //Copy array to use in the two sorting methods
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);

        ExtraTask3 q = new ExtraTask3();

        // Time the execution of quick sort with start element selected as pivot
        long start1 = System.currentTimeMillis();
        q.quickSort(arr1,0, arr1.length);
        long end1 = System.currentTimeMillis();
        long res1 = (end1 - start1);
        System.out.println("Time for Qicksort: " + res1 + " ms");

        // Verify that the sorted array is the same as the original array
        for (int i = 0; i <= resultArray.length; i++) {
            assert(resultArray[i] == arr1[i]);
        }

        System.out.println();
        // Time the execution of quick sort with end element selected as pivot
        long start2 = System.currentTimeMillis();
        q.quickSort3Way(arr2,0,arr2.length-1);
        long end2 = System.currentTimeMillis();
        long res2 = (end2 - start2);
        System.out.println("Time for Qicksort Mid: " + res2 + " ms");

        // Verify that the sorted array is the same as the original array
        for (int i = 0; i <= resultArray.length; i++) {
            assert(resultArray[i] == arr2[i]);
        }
    }
}
