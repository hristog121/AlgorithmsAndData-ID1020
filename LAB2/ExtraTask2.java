
import java.util.Arrays;
import java.util.Random;

public class ExtraTask2 {
    public void quickSort(int[] inputArray, int start, int end){
       int partition = partitionArr(inputArray,start,end);
        if (partition - 1 > start) {
            quickSort(inputArray, start,partition-1);
        }
        if (partition +1 < end){
            quickSort(inputArray, partition +1, end);
        }
    }

    private int partitionArr(int[] inputArray, int start, int end){
        int pivot = inputArray[end];
        for(int i = start; i<end; i++){
            if (inputArray[i] < pivot){
                int temp= inputArray[start];
                inputArray[start]=inputArray[i];
                inputArray[i]=temp;
                start++;
            }
        }
        //place pivot at its correct position
        int temp = inputArray[start];
        inputArray[start] = pivot;
        inputArray[end] = temp;

        return start;
    }
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
    }


    public static void main(String[] args) {
        Random random = new Random();

        int[] inputArray = random.ints(200000, 1, 200001).toArray();
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);
        MergeSort m = new MergeSort();
        long start2 = System.currentTimeMillis();
        m.sortNoCutOff(arr2,0,arr2.length-1);
        long end2 = System.currentTimeMillis();
        long res2 = (end2 - start2);
        System.out.println("Time for MergeSort: " + res2 + " ms");
        System.out.println();
        ExtraTask2 q = new ExtraTask2();
        long start1 = System.currentTimeMillis();
        q.quickSort(inputArray,0,arr1.length-1);
        long end1 = System.currentTimeMillis();
        long res1 = (end1 - start1);
        System.out.println("Time for Qicksort: " + res1 + " ms");
        //System.out.println(Arrays.toString(inputArray));
        System.out.println();



    }
}

