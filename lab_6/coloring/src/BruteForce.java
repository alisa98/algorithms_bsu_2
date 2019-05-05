import java.util.*;

public class BruteForce {

    private int[][] graph;


    public BruteForce(int[][] graph) {
        this.graph = graph;
    }


    public void BruteForceAlgorithm() {

        Integer[] arr = generatePermutations();
        permutingArray(Arrays.asList(arr), 0);

    }

    private Integer[] generatePermutations(){
        Integer arr[] = new Integer[graph.length];

        for (int i = 0; i < graph.length; i++){

            arr[i] = i+1;
        }

        return arr;
    }


    private void permutingArray(List<Integer> arrayList, int element) {


        for (int i = element; i < arrayList.size(); i++) {

            Collections.swap(arrayList, i, element);
            permutingArray(arrayList, element + 1);
            Collections.swap(arrayList, element, i);

        }


        if (element == arrayList.size() - 1) {
            System.out.println(Arrays.toString(arrayList.toArray()));
        }
    }



    public static void main(String[] args) {

        int[][] graph = {
                {0, 1, 1, 1},
                {1, 0, 0, 1},
                {1, 0, 0, 1},
                {1, 1, 1, 0},
               };

        BruteForce bruteForce = new BruteForce(graph);
        bruteForce.BruteForceAlgorithm();


    }




}
