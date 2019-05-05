public class Graph {
    private int[][] adjacencyMatrix;


    public Graph(int [][] adjacencyMatrix){
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

       public static boolean areAdjacent(Graph graph, int vertex1, int vertex2) {
        return graph.getAdjacencyMatrix()[vertex1][vertex2] != 0;
    }

}