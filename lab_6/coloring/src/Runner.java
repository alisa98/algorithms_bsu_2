
public class Runner {
    public static void main(String[] args) {

        int[][] matrix = {
                {0, 1, 0, 0, 1},
                {1, 0, 1, 1, 1},
                {0, 1, 0, 1, 0},
                {0, 1, 1, 0, 1},
                {1, 1, 0, 1, 0}};

        Graph graph = new Graph(matrix);

        Greedy algorithm = new Greedy();
        DSatur algorithm1 = new DSatur();

        algorithm.GreedyAlgorithm(graph);

        System.out.println("  ");

        algorithm1.dSaturaAlgorithm(graph);

    }


}