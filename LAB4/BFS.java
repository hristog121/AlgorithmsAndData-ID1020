/**
 * A implementation of BFS algorithm.
 * The data structure uses a array with bag list implementation
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

// An implementation of Breadth First Search on an Undirected Graph
public class BFS<Item> {
    // Holds an array specifying whether a vertice has already been visited
    private boolean[] visited;
    // Holds the 1st vertice from which each vertice is visited
    private int[] visitedFrom;
    // Holds the undirected Graph over which this Breadth First Search will work
    private Graph<Item> graph;

    // Creates a new Breadth First Search for the specified Graph
    public BFS(Graph<Item> graph) {
        // Initializes all variables with their initial values
        this.visited = new boolean[graph.getCountVertices()];
        this.visitedFrom = new int[graph.getCountVertices()];
        this.graph = graph;
    }

    // Returns an Iterator that can be used to traverse the path between two specified items
    public Iterator<Item> findPath(Item from, Item to) {
        // Finds the indexes of the items
        int fromIndex;
        int toIndex;
        try {
            fromIndex = graph.getIndex(from);
            toIndex = graph.getIndex(to);
        } catch (NoSuchElementException ex) {
            // Returns an empty iterator if elements not found in the Graph
            return new Bag<Item>().iterator();
        }

        // Resets the visited and visited from arrays
        reset();

        // Executes a full Breadth First Search traversal starting from "from"
        traverse(fromIndex);

        // If the specified destination is not visited after the traversal, return an empty iterator
        if (!visited[toIndex]) return new Bag<Item>().iterator();

        // Create a bag to hold the items on the path
        Bag<Item> path = new Bag<>();

        // Add the starting vertice
        path.add(to);

        // Starting from the edge which was 1st used to visit the ending vertice, iterate over all edges by looking
        // at which edge was used to visit the current vertice until we reach the beginning vertice
        for (int current = visitedFrom[toIndex]; current != fromIndex; current = visitedFrom[current]) {
            path.add(graph.getItem(current));
        }

        // Add the ending vertice
        path.add(from);

        // Return an iterator over the path
        return path.iterator();
    }

    // Executes a Breadth First Search traversal starting from the specified vertice
    private void traverse(int startVertice) {
        // Holds a queue of vertices to traverse
        Queue<Integer> toTraverse = new Queue<>();

        // Add the specified vertice to be traversed
        toTraverse.add(startVertice);

        // While there are vertices to be traversed, continue the traversal
        while (toTraverse.peek() != null) {
            // Get the next vertice to be traversed
            Integer currentVertice = toTraverse.poll();
            //Iterate over all adjacent vertices
            if (graph.getEdges()[currentVertice] != null) {
                for (int vertice : graph.getEdges()[currentVertice]) {
                    // If they have not been visited
                    if (!visited[vertice]) {
                        //System.out.println("Traverse " + graph.getItem(vertice) + " from " + graph.getItem(currentVertice) + ":");
                        // Mark the current vertice as the 1st vertice used to visit the current adjacent vertice
                        visitedFrom[vertice] = currentVertice;
                        // Add the vertice in the queue of vertices to be traversed next
                        toTraverse.add(vertice);
                        // Mark the adjacent vertice as visited
                        visited[vertice] = true;
                    }
                }
            }
        }
    }

    // Resets the visited and visitedFrom arrays
    private void reset() {
        // Iterate over the items in visited and visitedFrom and set initial values
        for (int i = 0; i < visited.length; i++) {
            // Mark all nodes as unvisited
            visited[i] = false;
            // Erase all paths
            visitedFrom[i] = -1;
        }
    }
}
