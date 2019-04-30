package algorithm;

import java.util.LinkedList;

public class Greedy {
    private int[][] graph;

    private int weight;
    private LinkedList<Integer> vertex = new LinkedList<>();

    public Greedy(int[][] graph) {
        this.graph = graph;
    }

    public int getWeight() {
        return weight;
    }

    public LinkedList<Integer> getVertex() {
        return vertex;
    }

    public void doAlgorithm(){
        vertex.add(0);

        while(vertex.size() < graph.length){
            nextVertex();
        }
        weight += graph[vertex.getLast()][vertex.getFirst()];
    }

    private void nextVertex(){
        int min = Integer.MAX_VALUE;
        int lastVertex = vertex.getLast();
        int bestNew = lastVertex;

        for (int i = 0; i < graph.length; i++){
            final int k = i;
            if (vertex.stream().noneMatch(c -> c == k)){
                if (graph[lastVertex][k] < min){
                    min = graph[lastVertex][k];
                    bestNew = k;
                }
            }
        }
        vertex.add(bestNew);
        weight += min;
    }
}