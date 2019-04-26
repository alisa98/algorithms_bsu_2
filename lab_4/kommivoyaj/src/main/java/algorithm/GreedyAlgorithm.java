package algorithm;

import java.util.ArrayList;
import java.util.List;

public class GreedyAlgorithm {
    private List<Integer> path = new ArrayList<Integer>();
    private int pathLength;
    private int iteration;

    public List<Integer> doAlgorithm(int[][] matrix){
        int size = matrix.length;
        int minPoint = size;
        int minValue = Integer.MAX_VALUE;
        path.add(1);
        int previous = 1;

        while (path.size()!=size){
            for (int j=0 ; j< size; j++){
                if (j + 1!=previous && matrix[previous-1][j]!=-1 && !path.contains(j+1)){
                    if (matrix[previous-1][j] <= minValue){
                        minValue = matrix[previous-1][j];
                        minPoint = j + 1;
                    }
                }
                if (j == size - 1){
                    pathLength += minValue;
                    path.add(minPoint);
                    previous = minPoint;
                    minValue = Integer.MAX_VALUE;
                    iteration++;
                }
            }
        }

        if (path.size() == size){
            path.add(1);
            pathLength += matrix[previous-1][0];
        }
        return path;
    }

    public int getPathLength(){
        return pathLength;
    }


    public static void main(String[] args) {
        int[][] matrix =
                {      {0, 11, 0, 0, 1, 0, 11},
                        {11, 0, 1, 1, 0, 0, 1},
                        {0, 1, 0, 11, 0, 0, 0},
                        {0, 1, 11, 0, 0, 11, 1},
                        {1, 0, 0, 0, 0, 11, 11},
                        {0, 0, 0, 11, 11, 0, 1},
                        {11, 1, 0, 1, 11, 1, 0}


                };
        System.out.println("Greedy algorithm : ");
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm();
        System.out.println(greedyAlgorithm.doAlgorithm(matrix) + ", " + greedyAlgorithm.getPathLength()+",");
    }
}
