
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class ExtraTask2 {
    public void quickSort(int[] inputArray, int start, int end) {
        if (start < end) {
            int pi = partitionArr(inputArray, start, end);
            quickSort(inputArray, start, pi - 1);
            quickSort(inputArray, pi + 1, end);
        }
    }

    private int partitionArr(int[] inputArray, int lo, int hi) {
        int pivot = inputArray[lo];
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

    
    public static class Merge {


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
                // If there are still un copied elements in R and L, copy minimum of the two
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

        void sortMerge(int inputArray[], int lo, int hi) {
            if (lo < hi) {
                //Put the cut of here
                int mid = (lo + hi) / 2;
                sortMerge(inputArray, lo, mid);
                sortMerge(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }
        }


    }


    public static void main(String[] args) {


        Random random = new Random();

        int[] inputArray = random.ints(100000, 10, 100001).toArray();
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);
        Merge m = new Merge();
        long start2 = System.currentTimeMillis();
        m.sortMerge(arr2, 0, arr2.length - 1);
        long end2 = System.currentTimeMillis();
        long res2 = (end2 - start2);
        System.out.println("Time for MergeSort: " + res2 + " ms");
        System.out.println();

        ExtraTask2 q = new ExtraTask2();
        long start1 = System.currentTimeMillis();
        q.quickSort(arr1, 0, arr1.length - 1);
        long end1 = System.currentTimeMillis();
        long res1 = (end1 - start1);
        System.out.println("Time for Qicksort: " + res1 + " ms");

        System.out.println();


    }
}

