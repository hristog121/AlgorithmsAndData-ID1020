/**
 * Implement a program which takes as input a text file and allows the user to (repeatedly without re-reading the input file) ask questions:
 * i) Which is the k:th most common word
 * ii) Which are the k:th to the k+n:th most common words
 *
 * Data Structure used --> Hash Tables and ordered Array.
 * Insertion Sort is used to sort the array
 *
 * @author Hristo Georgiev - 1c3r00t
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class ExtraTask1 {

    public static class Index {
        // Size of hash table
        private int HASH_TABLE_SIZE = 3000;
        // Array with words and the count of their occurrences ordered in descending order
        private WordOccurrence[] occurrenceIndex;

        // Constructor for the Index. Loads the file and initializes the index
        public Index(String fileName) throws FileNotFoundException {
            // Start timer
            long start = System.nanoTime();
            // Collection to hold the hash table
            ArrayList<LinkedList<WordOccurrence>> positionsHashTable = new ArrayList<>(HASH_TABLE_SIZE);
            // Initializes the hash table with empty values
            for (int i = 0; i < HASH_TABLE_SIZE; i++) {
                positionsHashTable.add(new LinkedList<>());
            }
            // Pattern to recognize ' in the beginning of the word in order to be able to trim it
            Pattern pattern = Pattern.compile("'\\w+");
            // Scanner to read the content of the file
            Scanner scanner = new Scanner(new File(fileName)).useDelimiter(",|\\s|\\n|\\r|\\.|:|\\[|\\]|\\*|--|\\(|\\)|!|_|\"|;|\\?|”|“|\\uFEFF");
            // Count of unique words in the text
            int uniqueWordCount = 0;
            // Read the file and construct the index
            while (scanner.hasNext()) {
                // Sanitize word:
                // - Remove empty words
                // - Remove quotes (outside of words)
                // - Make words lower case
                String word = scanner.next();
                if (word.isEmpty() || word.equals("'")) {
                    continue;
                }
                if (pattern.matcher(word).matches()) {
                    word = word.replace("'", "");
                }
                word = word.toLowerCase();

                // Hash the word
                int hash = hash(word);

                // Find word in hash table
                List<WordOccurrence> wordOccurrences = positionsHashTable.get(hash);
                // If no word is present with this hash, add a new word, with the 1 as count of occurrences
                if (wordOccurrences.isEmpty()) {
                    uniqueWordCount++;
                    wordOccurrences.add(new WordOccurrence(word));
                } else {
                    // If there are words present with this hash, find if this word exists
                    WordOccurrence thisWordOccurrence = null;
                    for (WordOccurrence wordOccurrence : wordOccurrences) {
                        if (wordOccurrence.word.equals(word)) {
                            thisWordOccurrence = wordOccurrence;
                            break;
                        }
                    }
                    // If the word exists, increase the occurrence count
                    if (thisWordOccurrence != null) {
                        thisWordOccurrence.increaseCount();
                    }
                    // If the word does not exist, add a new word, with the 1 as count of occurrences
                    else {
                        uniqueWordCount++;
                        wordOccurrences.add(new WordOccurrence(word));
                    }
                }
            }
            // Close input file reader
            scanner.close();

            // Add the words and the count of their occurrences to an array
            occurrenceIndex = new WordOccurrence[uniqueWordCount];
            int index = 0;
            for (LinkedList<WordOccurrence> wordOccurrences : positionsHashTable) {
                for (WordOccurrence wordOccurrence : wordOccurrences) {
                    occurrenceIndex[index++] = wordOccurrence;
                }
            }

            // Sort the array, so that the word with most occurrences comes 1st in the array
            insertionSort(occurrenceIndex);

            // End timer and display total time for the operation
            long end = System.nanoTime();
            long total = (long) ((end - start)/ 1000000.0);
            System.out.println("Total build time " + total + " ms");
        }

        // Method to find the k:th most common word
        public WordOccurrence getWordByFrequencyOrder(int k) {
            // If we want to search for an order that is not present in the array, throw and exception
            if (k > occurrenceIndex.length) {
                throw new IndexOutOfBoundsException(String.format("The total number of unique words is %s. Please enter a lower number.", occurrenceIndex.length));
            }
            // Return the word that is on the k:th position
            return occurrenceIndex[k - 1];
        }

        // Hashing function
        private int hash(String key) {
            return (key.hashCode() & 0x7fffffff) % HASH_TABLE_SIZE;
        }

        // Structure to hold a word and the number of its occurrences
        public static class WordOccurrence {
            private String word;
            private int count;

            public WordOccurrence(String word) {
                this.word = word;
                this.count = 1;
            }

            public void increaseCount() {
                count++;
            }
        }

        // Insertion sort implementation to sort the array
        public static void insertionSort(WordOccurrence inputArray[]) {
            WordOccurrence temp;
            int j;

            //start the for loop for iterating the array elements in the array
            for (int i = 1; i < inputArray.length; i++) {
                //stores the value at index at i int temp
                temp = inputArray[i];
                //assign i-1 to j
                j = i - 1;
                //while loop to make the swap when inputArray[j]>temp are true
                while ((j > -1) && (inputArray[j].count < temp.count)) {
                    inputArray[j + 1] = inputArray[j];

                    j--;
                }
                //store temp values into  index j+1
                inputArray[j + 1] = temp;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        // Create a new index
        Index index = new Index("src/TestInput2.txt");
        // Function to print menu and execute the user's choice
        while(true) {
            // Get the beginning order of word occurrence that the user wants to search for
            System.out.println("Enter k (enter 0 to exit): ");
            int k = scanner.nextInt();
            // Exit the program if the user enters 0
            if (k == 0) {
                break;
            }
            // Get the ending order of word occurrence that the user wants to search for
            System.out.println("Enter n (enter 0 if you want to get only the k:th most common element): ");
            int n = scanner.nextInt();

            // Get the k:th word and print appropriate message
            if (n < k) {
                try {
                    Index.WordOccurrence wordOccurrence = index.getWordByFrequencyOrder(k);
                    System.out.print(String.format("\"%s\" is the %d:th frequent word with %d occurrences.\n\n\n", wordOccurrence.word, k, wordOccurrence.count));
                } catch (Exception ex) {
                    System.out.print(String.format("%s\n\n\n", ex.getMessage()));
                }
            } // Get the k:th to n:th word and print appropriate message
            else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("The %d to %d most common words are:\n", k, n));
                for (int i = k; i <= n; i++) {
                    Index.WordOccurrence wordOccurrence = index.getWordByFrequencyOrder(i);
                    sb.append(String.format("\"%s\" with %d occurrences\n", wordOccurrence.word, wordOccurrence.count));
                }
                System.out.print(String.format("%s\n\n\n",sb.toString()));
            }
        }
        scanner.close();
    }
}