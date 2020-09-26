public class WordsHashValT3 {
    public static void main(String[] args) {

        //Parse the text up to N words. N should be given as an argument.
        int words = Integer.parseInt(args[0]);

        // iterates through the file until it's over or we've read the given amount of words
        for(int i = 0; i < words; i++) {
            if(StdIn.isEmpty()) break;
            String word = StdIn.readString().toLowerCase();
            WordsHashVal(word);
        };

    }
    //function to display hashcode of each word found in the text.
    public static void WordsHashVal(String str) {

        //spilt the string with the spaces , so that we found the words in the text. It this is not
        //present we will take the hash values only on characters
        for (String val : str.split(" "))
        {
            //calculate hashcode from in-built function hashCode() for strings and store that value in variable wordHashVal.
            int wordHashVal = val.hashCode();

            //print the hashcode "wordHashVal" of each word found in the text.
            System.out.println(val +", "+ wordHashVal);
        }

    }
}
