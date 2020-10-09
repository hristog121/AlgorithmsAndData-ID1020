import java.util.PriorityQueue;

public class UndirectedWeightedGraph<Item> {
    // Holds the number of vertices
    private int countVertices;
    // Holds the edges between vertices, where each item in the array holds the edges from this vertice to other vertices
    private Bag<WeightedEdge>[] edges;
    // Index table that holds the mapping between items and their indexes, which are used in the Graph
    private IndexTable<Item> indexTable;
    // Holds the shortest path weight to vertice with index corresponding to the array index
    private int[] pathWeight;
    // Holds the vertice used to visit the vertice with index corresponding to the array index (part of the shortest path)
    private int[] visitedFrom;

    // Creates an Undirected Weighted Graph with pre-specified number of vertices
    public UndirectedWeightedGraph(int countVertices) {
        // Initializes the count of vertices
        this.countVertices = countVertices;
        // Initializes a new Bag with edges and their weight. We work with integers for easier representation of the Graph
        this.edges = (Bag<WeightedEdge>[]) new Bag[countVertices];
        // Initializes an index table that will map from items to indexes of type integer, which will be used in the Graph
        this.indexTable = new IndexTable<>(countVertices);
        // Initializes the array to hold path ways
        this.pathWeight = new int[countVertices];
        // Initializes the array to hold last vertice used to visit certain vertice
        this.visitedFrom = new int[countVertices];
    }

    // Adds an edge between the two specified vertices and takes into account the weight of the edge
    public void addPath(Item vertice1, Item vertice2, int weight) {
        // Convert the input items to indexes
        int ver1Index = indexTable.add(vertice1);
        int ver2Index = indexTable.add(vertice2);

        // Initialize the bag with edges if this item has not been added before
        if (edges[ver1Index] == null) {
            edges[ver1Index] = new Bag<>();
        }

        // Add an edge from vertice1 to vertice2 and store its weight
        edges[ver1Index].add(new WeightedEdge(ver2Index, weight));
    }

    // Finds a path between two vertices in the Graph
    public Bag<Item> findPath(Item from, Item to) {
        return findPath(from, to, null);
    }

    // Finds a path between two vertices that passes through another vertice
    public Bag<Item> findPath(Item from, Item to, Item through) {
        // Find the index of the FROM and TO
        int fromIndex = indexTable.add(from);
        int toIndex = indexTable.add(to);

        // Initialize an empty bag to hold the resulting path
        Bag<Item> path = new Bag<>();
        // Add the starting vertice
        path.add(to);

        // Handle the case where we search with THROUGH
        if (through != null) {
            // Find the index of the THROUGH vertice
            int throughIndex = indexTable.add(through);
            // Traverse the Graph starting from the TRHOUGH vertice
            traverse(throughIndex);
            // If the ending vertice is not visited, return an empty path
            if (visitedFrom[toIndex] == -1) return new Bag<>();
            // Starting from the edge which was 1st used to visit the ending vertice, iterate over all edges by looking
            // at which edge was used to visit the current vertice until we reach the THROUGH vertice
            for (int current = visitedFrom[toIndex]; current != throughIndex; current = visitedFrom[current]) {
                path.add(indexTable.getItem(current));
            }

            // Add the THROUGH vertice to the path
            path.add(through);

            // Traverse the Graph starting from the FROM vertice
            traverse(fromIndex);
            // If the THROUGH vertice is not visited, return an empty path
            if (visitedFrom[throughIndex] == -1) return new Bag<>();
            // Starting from the edge which was 1st used to visit the THROUGH vertice, iterate over all edges by looking
            // at which edge was used to visit the current vertice until we reach the beginning vertice
            for (int current = visitedFrom[throughIndex]; current != fromIndex; current = visitedFrom[current]) {
                path.add(indexTable.getItem(current));
            }
        }
        // Handle the case where we search only FROM TO without THROUGH
        else {
            // Traverse the Graph starting from the FROM vertice
            traverse(fromIndex);
            // If the ending vertice is not visited, return an empty path
            if (visitedFrom[toIndex] == -1) return new Bag<>();
            // Starting from the edge which was 1st used to visit the ending vertice, iterate over all edges by looking
            // at which edge was used to visit the current vertice until we reach the beginning vertice
            for (int current = visitedFrom[toIndex]; current != fromIndex; current = visitedFrom[current]) {
                path.add(indexTable.getItem(current));
            }
        }

        // Add the ending vertice
        path.add(from);

        // Return an iterator over the path
        return path;
    }

    // Prints the vertices, edge and their weights
    public void printGraph() {
        // Print all vertices and edges
        for (int i = 0; i < edges.length; i++) {
            // Print the current vertice and its index
            System.out.println(i + " " + indexTable.getItem(i));
            // If there are no edges from the current vertice, print appropriate message
            if (edges[i] == null) {
                System.out.println(indexTable.getItem(i) + " has no edges");
            } else {
                // Print the edges from the current vertice
                for (WeightedEdge weightedEdge : edges[i]) {
                    System.out.println(indexTable.getItem(i) + " - " + indexTable.getItem(weightedEdge.to) + " " + weightedEdge.weight);
                }
            }
        }
    }

    // Traverses that Graph using Dijkstra's Shortest path algorithm
    private void traverse(int fromIndex) {
        // Reset the arrays that holds the path and the weights
        reset();

        // Create a priority queue to hold the next vertice to be processed, starting from the vertice with the smallest
        // weight of the path that is used to reach it
        PriorityQueue<WeightedEdge> priorityQueue = new PriorityQueue<>();
        // Add the first vertice and the weight of the path to reach to it - 0
        priorityQueue.add(new WeightedEdge(fromIndex, 0));
        pathWeight[fromIndex] = 0;

        // While there are vertices to process
        while (priorityQueue.peek() != null) {
            // Pick the vertice on top of the priority queue
            WeightedEdge currentVertice = priorityQueue.poll();
            // Get the currently processed vertice
            int currentVerticeTo = currentVertice.to;
            // Get the weight of the path used to reach the currently processed vertice
            int currentVerticeWeight = currentVertice.weight;

            // Traverse over all the adjacent edges of the current vertice
            for (WeightedEdge weightedEdge : edges[currentVerticeTo]) {
                // Get the adjacent vertice
                int adjEdgeTo = weightedEdge.to;
                // Get the weight of the edge leading to the adjacent vertice
                int adjEdgeWeight = weightedEdge.weight;
                // Determine if the adjacent vertice has been visited
                boolean visited = pathWeight[adjEdgeTo] != Integer.MAX_VALUE;
                // If the weight of the path used to seach the current vertice + the weight of the edge leading to the
                // adjcent vertice is smaller the currently smallest path to the adjacent vertice, then update the
                // weight to the path to be the current smallest and update the current vertice used to reach the
                // adjacent vertice
                if ((adjEdgeWeight + currentVerticeWeight) < pathWeight[adjEdgeTo]) {
                    pathWeight[adjEdgeTo] = adjEdgeWeight + currentVerticeWeight;
                    visitedFrom[adjEdgeTo] = currentVerticeTo;
                }
                // If the adjacent vertice edges have not been traversed yet, add the verticeto the queue for further
                // processing
                if (!visited) {
                    priorityQueue.add(new WeightedEdge(adjEdgeTo, pathWeight[adjEdgeTo]));
                }
            }
        }
    }

    // Resets the arrays holding path weights and vertices on the shortest path
    private void reset() {
        for (int i = 0; i < countVertices; i++) {
            // The path weights are set to the maximum allowed integer to start with in order to allow the update
            // of this array with smaller values
            pathWeight[i] = Integer.MAX_VALUE;
            // Mark -1 as the index used to visit the vertice with certain index - indicating that this vertice has
            // not been visited
            visitedFrom[i] = -1;
        }
    }

    // A structure to hold the edges and its weight
    private static class WeightedEdge implements Comparable {
        // Holds the TO vertice
        private int to;
        // Holds the weight of this edge
        private int weight;

        // Constructs a new Weighted Edge
        public WeightedEdge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        // Implementation of the comparable interface which allows us to order properly the edges in the priority
        // queue used by the Dijkstra algorithm. The smallest wighted has precedence over edges with a higher weight
        @Override
        public int compareTo(Object o) {
            return Integer.valueOf(weight).compareTo(Integer.valueOf(((WeightedEdge)o).weight));
        }
    }
}
