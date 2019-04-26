
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteForce {

    private List<Integer> minCities = new ArrayList<>();
    private int minDistance = Integer.MAX_VALUE;

    private int[][] graph;

    public BruteForce(int[][] graph) {
        this.graph = graph;
    }


    private Integer[] generateСombination(){
        Integer arr[] = new Integer[graph.length];
        for (int i = 0; i < graph.length; i++){
            arr[i] = i;
        }
        return arr;
    }

    private int countDistance(List<Integer> arr){
        int count = 0;
        for (int i = 0; i < arr.size(); i++){
            int j;
            if (i < arr.size() - 1){
                j = i+1;
            } else {
                j = 0;
            }
            count += graph[arr.get(i)][arr.get(j)];
        }
        return count;
    }

    private void CombinationArray(List<Integer> arrayList, int element) {
        for (int i = element; i < arrayList.size(); i++) {
            Collections.swap(arrayList, i, element);
            CombinationArray(arrayList, element + 1);
            Collections.swap(arrayList, element, i);
        }
        if (element == arrayList.size() - 1) {

            int dist = countDistance(arrayList);
            if (dist < minDistance){
                minDistance = dist;
                minCities = new ArrayList<>();
                for (int i = 0; i < arrayList.size(); i++){
                    minCities.add(i);
                }
            }
        }
    }

}