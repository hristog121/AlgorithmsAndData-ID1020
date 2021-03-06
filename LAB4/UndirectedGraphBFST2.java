/**
 * A main function to run BFS on the desired input.
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class UndirectedGraphBFST2 {
    // A function to test Breadth First Search on an Undirected Graph based on the data in the states database
    public static void main(String[] args) {
        // Defines a new Undirected Graph
        UndirectedGraph<String> graph;
        // Creates a scanner to read the content of the states database file
        try (Scanner scanner = new Scanner(new File("src/main/database.txt"))) {
            // Reads the number of vertices specified on the 1st line of the file
            String verticesCountStr = scanner.nextLine();
            int verticesCount = Integer.parseInt(verticesCountStr);

            // Creates a new Undirected Graph to hold the states database
            graph = new UndirectedGraph<>(verticesCount);

            // Read the next line that contains the number of edges and ignores it
            scanner.nextLine();

            // Add all paths specified on a new row in the states database to the graph
            while (scanner.hasNextLine()) {
                // Read the next line
                String path = scanner.nextLine();
                // Get the abbreviations of the states
                String[] vertices = path.split(" ");
                // Add the path to the graph
                graph.addPath(vertices[0], vertices[1]);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return;
        }

        // Create a new Breadth First Search
        BFS<String> search = new BFS<>(graph);

        // Define a scanner to read user input
        try (Scanner scannerInput = new Scanner(System.in)) {
            while(true) {
                // Get the FROM state from the user
                System.out.println("Enter state FROM which you want to find path, or X for exit: ");
                String from = scannerInput.next();

                // Exit in case the user enters X
                if (from.equals("X")) {
                    return;
                }

                //Get the TO state from the user
                System.out.println("Enter state TO which you want to find path: ");
                String to = scannerInput.next();

                // Find the path between FROM and TO
                Iterator<String> path = search.findPath(from, to);

                // If path is empty, print that there is no path between FROM and TO
                if (!path.hasNext()) {
                    System.out.printf("There is no path between %s and %s", from, to);
                }

                // Print the path between FROM and TO
                while (path.hasNext()) {
                    System.out.print(String.format("%s ", path.next()));
                }
                System.out.println("\n\n");
            }
        }
    }
}
