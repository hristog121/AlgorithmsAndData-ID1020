import java.io.*;

public class FilterT1 {
    public static void main(String[] args) throws FileNotFoundException {

   /*     String input = "Hi! th#s is test!ng!";
        System.out.println("String size: " + input.length());
        String result = charFilter(input);
        System.out.println(result);
        System.out.println("String length after filtering: " + result.length());*/

        try {
            FileReader reader = new FileReader("src/TestInput.txt");
            FileWriter writer = new FileWriter("src/output.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;


            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String res2 = charFilter(line);
                writer.write(res2);
            }
            reader.close();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String charFilter(String str){
        String result ="";
        for (int i = 0; i<str.length(); i++){
            if (Character.isAlphabetic(str.charAt(i))){
                result += str.charAt(i);
            } else {
                result += " ";
            }
        }
        return result;
    }
}
