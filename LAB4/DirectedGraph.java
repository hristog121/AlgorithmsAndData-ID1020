/**
 * A directed graph implementation to store the vertices and edges of the graph.
 * The data structure uses an array with bag list implementation
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */
import java.util.NoSuchElementException;

// Fata structure representing an Undirected Graph with items
public class DirectedGraph<Item> implements Graph<Item>{
    // Holds the number of vertices
    private int countVertices;
    // Holds the edges between vertices, where each item in the array holds the edges from this vertice to other vertices
    private Bag<Integer>[] edges;
    // Index table that holds the mapping between items and their indexes, which are used in the Graph
    private IndexTable<Item> indexTable;

    // Creates an undirected Graph with pre-specified number of vertices
    public DirectedGraph(int countVertices) {
        this.countVertices = countVertices;
        // Initializes a new Bag with adjacencies. We work with integers for easier representation of the Graph
        this.edges = (Bag<Integer>[]) new Bag[countVertices];
        // Initializes an index table that will map from items to indexes of type integer, which will be used in the Graph
        this.indexTable = new IndexTable<>(countVertices);
    }

    // Adds an edge between the two specified vertices
    public void addPath(Item vertice1, Item vertice2) {
        // Convert the input items to indexes
        int ver1Index = indexTable.add(vertice1);
        int ver2Index = indexTable.add(vertice2);

        // Initialize the bag with edges if this item has not been added before
        if (edges[ver1Index] == null) {
            edges[ver1Index] = new Bag<>();
        }

        // Add an edge from vertice1 to vertice2
        edges[ver1Index].add(ver2Index);
    }

    //Returns a bag with vertices that are adjacent to the input vertice
    public Bag<Item> getPaths(Item vertice) {
        // Get the index of the item and if the item is not in the index return an empty bag
        int verIndex;
        try {
            verIndex = indexTable.getIndex(vertice);
        } catch (NoSuchElementException ex) {
            return new Bag<>();
        }
        // Prepare a bag to hold the adjacent vertices
        Bag<Item> edges = new Bag<>();

        // If there are no edges from the specified vertice, return an empty Bag
        if (this.edges[verIndex] == null) {
            return new Bag<>();
        }

        // Iterate over the adjacent vertices of the input vertice, convert the indexes to items, add the items
        // in the response bag and return that to the caller
        for (Integer integer : this.edges[verIndex]) {
            edges.add(indexTable.getItem(integer));
        }
        return edges;
    }

    // Returns the count of vertices
    public int getCountVertices() {
        return countVertices;
    }

    // Returns the item corresponding to the specified index
    public Item getItem(int index) {
        return indexTable.getItem(index);
    }

    //Returns the index corresponding to the specified item
    public int getIndex(Item item) {
        return indexTable.getIndex(item);
    }

    // Returns all edges
    public Bag<Integer>[] getEdges() {
        return edges;
    }

    // Prints the edges in the graph
    public void printGraph() {
        // Print all vertices and edges
        for (int i = 0; i < edges.length; i++) {
            // Print the current vertice and its index
            System.out.println(i + " " + indexTable.getItem(i));
            // Print the edges from the current vertice
            if (edges[i] != null) {
                for (Integer ind : edges[i]) {
                    System.out.println(indexTable.getItem(i) + " - " + indexTable.getItem(ind));
                }
            }
        }
    }
}
