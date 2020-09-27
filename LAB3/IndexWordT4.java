/**
 *"index"-program which allows the user to ask questions "on which positions in the text (i.e. the number of characters
 *  from the beginning) you find the word X". The program should list the position of all occurrences of X as answer to
 *  a query. In this assignment you may use the Java library (built-in) lists. Questions to the index should be answered
 *  in time less or equal to O(log(N)) where N is the number of keys.
 *
 *  The hash function is taken from Algorithms 4th Edition
 *
 * @author Hristo Georgiev - 1c3r00t
 **/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class IndexWordT4 {

    public static class Index {
        // Hash table size - allows for worst case 12 collisions. Log2 of 10584 is ~ 13.
        private int HASH_TABLE_SIZE = 3000;
        // Collection to hold the hash table
        private ArrayList<LinkedList<WordOccurrence>> positionsHashTable = new ArrayList<>(HASH_TABLE_SIZE);
        // Constructor for the Index. Loads the file and initializes the index
        public Index(String fileName) throws FileNotFoundException {
            // Initializes the hash table with empty values
            for (int i = 0; i < HASH_TABLE_SIZE; i++) {
                positionsHashTable.add(new LinkedList<>());
            }

            // Pattern to recognize ' in the beginning of the word in order to be able to trim it
            Pattern pattern = Pattern.compile("'\\w+");
            // Scanner to read the content of the file
            Scanner scanner = new Scanner(new File(fileName)).useDelimiter(",|\\s|\\n|\\r|\\.|:|\\[|\\]|\\*|--|\\(|\\)|!|_|\"|;|\\?|”|“|\\uFEFF");
            // Pointer to hold the current word position
            int position = 0;
            // Read the file and construct the index
            while (scanner.hasNext()) {
                position++;
                // Sanitize word:
                // - Remove empty words
                // - Remove quotes (outside of words)
                // - Make words lower case
                String word = scanner.next();
                if (word.isEmpty() || word.equals("'")) { position--; continue; }
                if (pattern.matcher(word).matches()) { word = word.replace("'", ""); }
                word = word.toLowerCase();

                // Hash word
                int hash = hash(word);

                // Find word in hash table
                List<WordOccurrence> wordOccurrences = positionsHashTable.get(hash);
                // If no word is present with this hash, add a new word, initialize the list with occurrences and
                // add the 1st occurrence
                if (wordOccurrences.isEmpty()) {
                    List<Integer> occurrences = new LinkedList<>();
                    occurrences.add(position);
                    wordOccurrences.add(new WordOccurrence(word, occurrences));
                } else {
                    // If there are words present with this hash, find if this word exists
                    WordOccurrence thisWordOccurrence = null;
                    for (WordOccurrence wordOccurrence : wordOccurrences) {
                        if (wordOccurrence.word.equals(word)) {
                            thisWordOccurrence = wordOccurrence;
                            break;
                        }
                    }
                    // If the word exists, append the new occurrence
                    if (thisWordOccurrence != null) {
                        thisWordOccurrence.occurrence.add(position);
                    }
                    // If the word does not exist, add a new word, initialize the list with occurrences and
                    // add the 1st occurrence
                    else {
                        List<Integer> occurrences = new LinkedList<>();
                        occurrences.add(position);
                        wordOccurrences.add(new WordOccurrence(word, occurrences));
                    }
                }
            }
        }

        // Method to find the positions at which a word is present in the text
        public List<Integer> getPositions(String word) {
            // Prepare the word for searching:
            // - lower case
            // - hash
            word = word.toLowerCase();
            int hash = hash(word);
            // Find the word by its hash
            LinkedList<WordOccurrence> wordOccurrences = positionsHashTable.get(hash);
            //Find the occurrences for a word in the list of word corresponding to the same hash
            for (WordOccurrence wordOccurrence : wordOccurrences) {
                if (wordOccurrence.word.equals(word)) {
                    return wordOccurrence.occurrence;
                }
            }
            // Is the word is not found, return an empty collection
            return Collections.emptyList();
        }

        // Hashing function that will normalize the hash in the range [0-3000]
        private int hash(String key) {
            return (key.hashCode() & 0x7fffffff) % HASH_TABLE_SIZE;
        }

        // Structure to hold a word and its occurrences
        private static class WordOccurrence {
            private String word;
            private List<Integer> occurrence;

            public WordOccurrence(String word, List<Integer> occurrence) {
                this.word = word;
                this.occurrence = occurrence;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Create a new index
        Index index = new Index("src/TestInput.txt");
        // Function to print menu and execute the user's choice
        while(true) {
            // Get the word that the user wants to search
            System.out.println("Enter a word from the text for which you want to find the positions (enter X to exit): ");
            Scanner scanner = new Scanner(System.in);
            String word = scanner.next();
            // Exit the program if the user enters X
            if (word.equals("X")) {
                break;
            }
            // Get the words' positions in the text
            List<Integer> positions = index.getPositions(word);
            // If the word is not present in the text, print an appropriate message
            if (positions.isEmpty()) {
                System.out.println(String.format("The word %s is not found in the text.\n\n\n", word));
            }
            // Is the word is present, print an appropriate message
            else {
                StringBuilder sb = new StringBuilder(String.format("The word %s can be found %d times, on positions: ", word, positions.size()));
                for (int i = 0; i < positions.size(); i++) {
                    sb.append(positions.get(i));
                    if (i < positions.size() - 1) {
                        sb.append(", ");
                    }
                }
                System.out.print(String.format("%s\n\n\n", sb.toString()));
            }
        }
    }
}