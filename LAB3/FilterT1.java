/**
 * Simple filter written in java.
 * Cleans all punctuation and replaces all punctuation sings with " " (empty space) so the size of the text is preserved
 * @author Hristo Georgiev - 1c3r00t
 **/

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FilterT1 {
    public static void main(String[] args) throws FileNotFoundException {

        try {
            //Read the text from the test file
            FileReader reader = new FileReader("src/TestInput.txt", UTF_8);
            //After the cleaning is done put the output in a new file
            FileWriter writer = new FileWriter("src/output.txt");
            //Buffer reader init
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            //read a kline --> apply filter --> write to the output file
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String res2 = charFilter(line);
                writer.write(res2 + '\n');
            }
            reader.close();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String charFilter(String str) {
        //String Builder init
        StringBuilder result = new StringBuilder();
        //Read char by char a line and if it is a char appends it to the string builder
        //is not a char append empty space to keep the size of the original text
        for (int i = 0; i < str.length(); i++) {
            if (Character.isAlphabetic(str.charAt(i))) {
                result.append(str.charAt(i));
            } else {
                result.append(" ");
            }
        }
        return result.toString();
    }
}
