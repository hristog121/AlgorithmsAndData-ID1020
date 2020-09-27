public class WordsHashValT3 {
    public static int getHashValue(String s, int hashSize) {
        //combine the hashCode with mod -> producing integers between 0 and m-1 - page 461
        return (s.hashCode() & 0x7fffffff) % hashSize;
    }

    // main
    public static void main(String[] args) {
        // takes first input as hash size
        int hashSize = Integer.parseInt(args[0]);
        // array to count how many words go where
        int[] wordsIn = new int[hashSize];
        for(int i = 0; i < hashSize; i++)
            wordsIn[i] = 0;
        // takes in all words
        while(!StdIn.isEmpty()) {
            String word = StdIn.readString().toLowerCase();
            int hashVal = getHashValue(word, hashSize);
            // adds one where the word would go
            wordsIn[hashVal]++;
        }

        int total = 0, min = wordsIn[0], max = wordsIn[0];
        // goes through all parts of the hash table
        for(int i = 0; i < hashSize; i++) {
            System.out.println(i + 1 + "  " + wordsIn[i]);
            System.out.println();
            // keeps track of min, max and total values
            total += wordsIn[i];
            if(min > wordsIn[i]) min = wordsIn[i];
            if(max < wordsIn[i]) max = wordsIn[i];
        }
        System.out.println();
        //Prints the value of the min max and avg hash values
        System.out.println("Min, Max and Avg hashValues:");
        System.out.println("Min: " + min);
        System.out.println("Avg: " + total / hashSize);
        System.out.println("Max: " + max);              // prints min, avg and max values

    }
}
