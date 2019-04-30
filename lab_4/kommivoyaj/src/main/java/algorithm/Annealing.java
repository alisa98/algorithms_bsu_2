package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Annealing {
    private int[][] graph;
    private Function function;
    private double maxTemperature;
    private double minTemperature;
    private int iterations;
    private List<Integer> vertex;
    private int distance;

    public Annealing(int[][] graph, double maxTemperature, double minTemperature, Function function) {
        this.graph = graph;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.function = function;
    }

    public int getIterations() {
        return iterations;
    }

    public List<Integer> getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }
    public interface Function {
        double T(double t, int iteration);
    }
    public void doAlgorithm(){

        Greedy greedy = new Greedy(graph);
        greedy.doAlgorithm();
        vertex = greedy.getVertex();
        distance = computeDistance(vertex);
        double t = maxTemperature;

        while(t >= minTemperature){
            iterations++;
            List<Integer> newPath = generatePath();
            int newDistance = computeDistance(newPath);
            int E = newDistance - distance;
            if (E <= 0){
                vertex = newPath;
                distance = newDistance;
            } else {
                double p = Math.exp(E/t);
                if (p > Math.random()){
                    vertex = newPath;
                    distance = newDistance;
                }
            }
            t = function.T(t, iterations);
        }
    }

    private int computeDistance(List<Integer> cities){
        int count = 0;
        for (int i = 0; i < cities.size(); i++){
            int j;
            if (i < cities.size() - 1){
                j = i+1;
            } else {
                j = 0;
            }
            count += graph[cities.get(i)][cities.get(j)];
        }
        return count;
    }

    private List<Integer> generatePath(){
        List<Integer> newPath = new ArrayList<>(vertex);
        int i = new Random().ints(0, vertex.size()-3).limit(1).findFirst().getAsInt();
        int j = new Random().ints(i+2, vertex.size()-1).limit(1).findFirst().getAsInt();
        int start = i;
        while(j>start){
            newPath.set(i+1, vertex.get(j));
            j--;
            i++;
        }
        return newPath;
    }
}