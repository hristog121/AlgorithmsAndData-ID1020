import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class ShortestPathT4 {

    public static void main(String args[]) {
        // Defines a new UndirectedWeighted Graph
        UndirectedWeightedGraph<Integer> graph;
        // Creates a scanner to read the content of the states database file
        try (Scanner scanner = new Scanner(new File("src/main/NYC.txt"))) {
            // Reads the number of vertices specified on the 1st line of the file
            String verticesCountStr = scanner.nextLine();
            int verticesCount = Integer.parseInt(verticesCountStr);

            // Creates a new Undirected Graph to hold the states database
            graph = new UndirectedWeightedGraph<>(verticesCount);

            // Read the next line that contains the number of edges and ignores it
            scanner.nextLine();

            // Add all paths specified on a new row in the states database to the graph
            while (scanner.hasNextLine()) {
                // Read the next line
                String path = scanner.nextLine();
                // Get the abbreviations of the states
                String[] vertices = path.split(" ");
                // Add the path to the graph
                graph.addPath(Integer.valueOf(vertices[0]), Integer.valueOf(vertices[1]), Integer.valueOf(vertices[2]));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return;
        }

        // Define a scanner to read user input
        try (Scanner scannerInput = new Scanner(System.in)) {
            while(true) {
                // Get the FROM state from the user
                System.out.println("Enter street FROM which you want to find path, or 0 for exit: ");
                int from = scannerInput.nextInt();

                // Exit in case the user enters X
                if (from == 0) {
                    return;
                }

                //Get the TO state from the user
                System.out.println("Enter street TO which you want to find path: ");
                int to = scannerInput.nextInt();

                //Get the THROUGH state from the user
                System.out.println("Enter street THROUGH which you want to find path, or 0 for direct path search: ");
                int through = scannerInput.nextInt();

                // Find the path between FROM and TO
                Iterator<Integer> path = graph.findPath(from, to, (through == 0 ? null : through)).iterator();

                // If path is empty, print that there is no path between FROM and TO
                if (!path.hasNext()) {
                    if (through == 0) {
                        System.out.printf("There is no path between %d and %d", from, to);
                    } else {
                        System.out.printf("There is no path between %d and %d (through %d)", from, to, through);
                    }
                } else {

                    if (through == 0) {
                        System.out.printf("The shortest path between between %d and %d is\n", from, to);
                    } else {
                        System.out.printf("The shortest path between between %s and %s (through %s) is\n", from, to, through);
                    }

                    // Print the path between FROM and TO
                    while (path.hasNext()) {
                        System.out.print(String.format("%s ", path.next()));
                    }
                }
                System.out.println("\n\n");
            }
        }
    }
}
