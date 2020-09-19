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

public class MergeVsInsertionT5andT6 {

    //Insertion sort section plus time test
    public static void main(String a[]) {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        int minElements = 100;
        int maxElements = 1000000;

        int countTestArrays = 10;

        int minMergeCutOff = 0;
        int maxMergeCutOff = 30;
        int mergeCutOffIncrement = 5;

        // Display header
        System.out.printf("countElements,insertionMid,mergeNoCutMid");
        for (int i = minMergeCutOff; i <= maxMergeCutOff; i+= mergeCutOffIncrement) {
            System.out.printf(",mergeCut%dMid", i);
        }
        System.out.printf("\n");
        for (int countElements = minElements; countElements <= maxElements;) {

            // Control variants of arrays
            double[] resultsInsertion = new double[countTestArrays];
            double[] resultsMerge = new double[countTestArrays];
            double[][] resultsMergeWithCut = new double[maxMergeCutOff+1][countTestArrays];
            for (int j = 0; j < countTestArrays; j++) {
                int[] inputArray = random.ints(countElements, 1, countElements + 1).toArray();
                resultsInsertion[j] = timeInsertion(inputArray);
                resultsMerge[j] = timeMerge(inputArray);

                for (int k = minMergeCutOff; k <= maxMergeCutOff; k += mergeCutOffIncrement) {
                    resultsMergeWithCut[k][j] = timeMerge(inputArray, k);
                }
            }
            System.out.printf("%d,%.5f,%.5f", countElements, calculateMid(resultsInsertion), calculateMid(resultsMerge));
            for (int i = minMergeCutOff; i <= maxMergeCutOff; i+= mergeCutOffIncrement) {
                System.out.printf(",%.5f", calculateMid(resultsMergeWithCut[i]));
            }
            System.out.printf("\n");

            countElements += getIncrement(countElements);
        }
    }

    /**
     *
     * @return [100-1000] -> 100;
     *         [1000 - 10000] -> 1000;
     *         [10000 - 100000] -> 10000;
     *         [100000 - 1000000] -> 100000;
     */
    private static int getIncrement(int countElements) {
        if(countElements < 1000)
            return 100;
        if(countElements < 10000)
            return 1000;
        if(countElements < 100000)
            return 10000;
        if(countElements < 1000000)
            return 100000;
        throw new UnsupportedOperationException("Please enter a number between 100 and 1000000");
    }

    private static double calculateMid(double[] results) {
        double mid = 0;
        for (int j = 0; j < results.length; j++) {
            mid = mid + results[j];
        }
        mid = mid / results.length;
        return mid;
    }

    public static double timeInsertion(int inputArray[]) {
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        long start = System.nanoTime();
        insertionSort(arr1, 0, inputArray.length);
        long end = System.nanoTime();
        return  (end - start)/1000000.0;
    }

    public static double timeMerge(int inputArray[]) {
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);
        MergeSort m = new MergeSort(0);

        long start2 = System.nanoTime();
        m.sortNoCutOff(arr2, 0, arr2.length - 1);
        long end2 = System.nanoTime();
        return (end2 - start2)/1000000.0;
    }

    private static double timeMerge(int inputArray[], int cutOff) {
        int[] arr3 = Arrays.copyOf(inputArray, inputArray.length);
        MergeSort m = new MergeSort(cutOff);
        long start3 = System.nanoTime();
        m.sortCutOff(arr3, 0, arr3.length - 1);
        long end3 = System.nanoTime();
        return (end3 - start3)/1000000.0;
    }


    /**
     * Implementation of the insertion sort algorithm. The methods takes an array of integers and sorts them
     * The integer with lowest value is on the lowest index
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

            inputArray[j + 1] = temp;

        }
    }

    //topdown mergesord
    public static class MergeSort {
        private final int cutOff;

        public MergeSort(final int cutOff) {
            this.cutOff = cutOff;
        }

        void merge(int inputArray[], int lo, int mid, int hi) {
            // Creating temporary subarrays
            int leftArr[] = new int[mid - lo + 1];
            int rightArr[] = new int[hi - mid];

            // Copying our subarrays into temporaries
            for (int i = 0; i < leftArr.length; i++)
                leftArr[i] = inputArray[lo + i];
            for (int i = 0; i < rightArr.length; i++)
                rightArr[i] = inputArray[mid + i + 1];

            // Iterators containing current index of temp subarrays
            int iLeft = 0;
            int iRight = 0;

            // Copying from leftArr and rightArr back into array
            for (int i = lo; i < hi + 1; i++) {
                // If there are still uncopied elements in R and L, copy minimum of the two
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

        void sortNoCutOff(int inputArray[], int lo, int hi) {
            if (lo < hi) {
                //Put the cut of here
                int mid = (lo + hi) / 2;
                sortNoCutOff(inputArray, lo, mid);
                sortNoCutOff(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }
        }

        void sortCutOff(int inputArray[], int lo, int hi) {
            if ((hi - lo + 1) <= cutOff) {
                //Put the cut of here
                insertionSort(inputArray, lo, hi);
            } else if (lo < hi){
                int mid = (hi + lo) / 2;
                sortCutOff(inputArray, lo, mid);
                sortCutOff(inputArray, mid + 1, hi);
                merge(inputArray, lo, mid, hi);
            }
        }
    }
}