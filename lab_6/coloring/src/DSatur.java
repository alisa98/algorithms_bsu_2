
import java.util.*;


public class DSatur {

    public void dSaturaAlgorithm(Graph graph) {
        int length = graph.getAdjacencyMatrix().length;
        Map<Integer, Integer> resultColoring = new LinkedHashMap<>();
        List<Integer> coloredVertexes = new ArrayList<>();
        int[] coloring = new int[length];
        for (int i = 0; i< length; i++){
            coloring[i] = -1;
        }
        List<Integer> withoutColor = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            withoutColor.add(i);
        }

        int highestDegreeVertex = getVertexWithHighestDegree(graph);
        coloring[highestDegreeVertex] = 0;
        coloredVertexes.add(highestDegreeVertex);
        resultColoring.put(highestDegreeVertex, 0);
        withoutColor.remove((Object)highestDegreeVertex);

        while (withoutColor.size() > 0) {
            int vertex = getHighestSaturation(graph, coloring);
            while (resultColoring.containsKey(vertex)){
                vertex = getHighestSaturation(graph, coloring);
            }

            boolean[] availableColors = new boolean[length];
            for (int j = 0; j < length; j++){
                availableColors[j] = true;
            }

            int lastColor = 0;

            for (int k = 0; k < coloredVertexes.size(); k++){
                int currentVertex = coloredVertexes.get(k);
                if (Graph.areAdjacent(graph, vertex, currentVertex)){
                    int color = resultColoring.get(currentVertex);
                    availableColors[color] = false;
                }
            }
            for (int j = 0; j < availableColors.length; j++){
                if (availableColors[j]){
                    lastColor = j;
                    break;
                }
            }

            resultColoring.put(vertex, lastColor);
            withoutColor.remove((Object)vertex);
            coloredVertexes.add(vertex);
            coloring[vertex] = lastColor;
        }
        System.out.println(resultColoring);
    }

    public static int getVertexWithHighestDegree(Graph graph){
        int vertexWithHighestDegree = 0;
        int numberOfAdjacent = 0;
        int[][] matrix = graph.getAdjacencyMatrix();
        for (int i = 0; i < matrix.length; i++){
            int tempNumberAdjacent = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j]!=0) tempNumberAdjacent++;
            }
            if (tempNumberAdjacent > numberOfAdjacent){
                numberOfAdjacent = tempNumberAdjacent;
                vertexWithHighestDegree = i;
            }
        }
        return vertexWithHighestDegree;
    }

    public static int getHighestSaturation(Graph graph, int[] coloring)
    {
        int maxSaturation = 0;
        int vertexWithMaxSaturation = 0;
        int length = graph.getAdjacencyMatrix().length;

        for(int i = 0; i < length; i++) {
            if (coloring[i] == -1) {
                Set<Integer> colors = new TreeSet<>();
                for (int j = 0; j < length; j++) {
                    if (Graph.areAdjacent(graph, i, j) && coloring[j] != -1) {
                        colors.add(coloring[j]);
                    }
                }
                int tempSaturation = colors.size();
                if (tempSaturation > maxSaturation) {
                    maxSaturation = tempSaturation;
                    vertexWithMaxSaturation = i;
                } else if (tempSaturation == maxSaturation && calculateDegree(graph.getAdjacencyMatrix(), i)
                        >= calculateDegree(graph.getAdjacencyMatrix(), vertexWithMaxSaturation))
                {
                    vertexWithMaxSaturation = i;
                }
            }
        }
        System.out.println("Saturation = " + maxSaturation + ", vertex = " + vertexWithMaxSaturation);
        return vertexWithMaxSaturation;
    }

    public static int calculateDegree(int[][] matrix, int vertex){
        int degree = 0;
        for(int i = 0 ; i< matrix[vertex].length; i++){
            if (matrix[vertex][i]!=0){
                degree++;
            }
        }
        return degree;
    }
}