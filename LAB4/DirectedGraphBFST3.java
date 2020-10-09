/**
 * BFS on a directed graph that answers the question if there is a path between any 2 vertices
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class DirectedGraphBFST3 {
    // A function to test Breadth First Search on an Directed Graph based on the data in the states database
    public static void main(String[] args) {
        // Define a new Directed Graph
        DirectedGraph<String> graph;
        // Create a scanner to read the content of the states database file
        try (Scanner scanner = new Scanner(new File("src/main/partialdata.txt"))) {
            // Read the number of vertices specified on the 1st line of the file
            String verticesCountStr = scanner.nextLine();
            int verticesCount = Integer.parseInt(verticesCountStr);

            // Create a new Directed Graph to hold the states database
            graph = new DirectedGraph<>(verticesCount);

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
                // If the path is not empty, print that there is path between FROM and TO
                else {
                    System.out.printf("There is a path between %s and %s ", from, to);
                }
                System.out.println("\n\n");
            }
        }
    }
}
