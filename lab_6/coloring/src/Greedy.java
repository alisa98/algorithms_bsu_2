

import java.util.ArrayList;
import java.util.List;

public class Greedy {

    public void GreedyAlgorithm(Graph graph) {
        int length = graph.getAdjacencyMatrix().length;
        int chromaticNumber=0;
        List<Integer> resultColoring = new ArrayList<>();
        resultColoring.add(0);
        List<Integer> coloredVertexes = new ArrayList<>();
        coloredVertexes.add(1);

        for (int i = 1; i < length; i++){
            boolean[] availableColors = new boolean[length];
            for (int j = 0; j < length; j++){
                availableColors[j] = true;
            }
            int lastColor = 0;
            for (int k = 0; k < coloredVertexes.size(); k++){
                if (Graph.areAdjacent(graph, i, k)){
                    int color = resultColoring.get(k);
                    availableColors[color] = false;
                }
            }

            for (int j = 0; j < availableColors.length; j++){
                if (availableColors[j]){
                    lastColor = j;
                    break;
                }
            }
            resultColoring.add(lastColor);
            coloredVertexes.add(i);
            chromaticNumber++;
        }

        for (int i = 0; i < length; i++)
            System.out.println("Vertex " + i + " --->  Color "
                    + resultColoring.get(i));
        System.out.println("chromatic number= " + chromaticNumber);

    }
}