import java.util.Arrays;
import java.util.Random;

public class ExtraTask3 {

    public void quickSort(int[] inputArray, int start, int end) {
        int partition = partitionArr(inputArray,start,end);
        if (partition - 1 > start) {
            quickSort(inputArray, start,partition-1);
        }
        if (partition +1 < end){
            quickSort(inputArray, partition +1, end);
        }
    }

    public void quickSort3Way(int[] inputArray, int start, int end) {
        int partition = partition3WayArr(inputArray,start,end);
        if (partition - 1 > start) {
            quickSort3Way(inputArray, start,partition-1);
        }
        if (partition +1 < end){
            quickSort3Way(inputArray, partition +1, end);
        }
    }
    private int partitionArr(int[] inputArray, int lo, int hi) {
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

    private int partition3WayArr(int[] inputArray, int lo, int hi) {
        int[] loMidHi = {inputArray[lo],inputArray[hi - lo /2], inputArray[hi]};
        Arrays.sort(loMidHi);

        int pivot = loMidHi[1];
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


    public static void main(String[] args) {




        Random random = new Random();

        int[] inputArray = random.ints(150000, 10, 150001).toArray();
        int[] arr1 = Arrays.copyOf(inputArray, inputArray.length);
        int[] arr2 = Arrays.copyOf(inputArray, inputArray.length);




        ExtraTask3 q = new ExtraTask3();

        long start1 = System.currentTimeMillis();
        q.quickSort(arr1,0,arr1.length-1);
        long end1 = System.currentTimeMillis();
        long res1 = (end1 - start1);
        System.out.println("Time for Qicksort: " + res1 + " ms");

        System.out.println();
        long start2 = System.currentTimeMillis();
        q.quickSort3Way(arr2,0,arr2.length-1);
        long end2 = System.currentTimeMillis();
        long res2 = (end2 - start2);
        System.out.println("Time for Qicksort Mid: " + res2 + " ms");

        System.out.println();




    }
}
