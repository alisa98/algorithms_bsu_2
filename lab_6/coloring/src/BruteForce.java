
import java.util.*;

public class BruteForce {

    public static int minColor= Integer.MAX_VALUE;

    public static void BruteForce(int [][]graph, int [] color, int[] Nr, int index) {

        int currentColor;

        if (index == color.length)
        {
            boolean flag= true;
            for(int i = 0; i<graph.length;i++) {
                for (int j = 0; j < graph.length; j++) {
                    if (graph[i][j] == 1) {
                        if (color[i] == color[j])
                            flag=false;
                        else continue;
                    } else continue;
                }
                if(flag && i==graph.length-1) {
                    // System.out.println(Arrays.toString(color));
                    currentColor = uniqueCount(color);
                    if (currentColor<minColor) {
                        minColor=currentColor;
                    }
                }
            }
            return ;
        }
        for (int i = 0; i <= Nr[index]; i++)
        {
            color[index] = i;
            BruteForce(graph,color, Nr, index+1);
        }

    }

    public   static int uniqueCount(int[] array) {
        Set<Integer> mySet = new HashSet<>();
        for (int x : array) {
            mySet.add(x);
        }
        return mySet.size();
    }

    public static void main(String[] args) {

        int[][] graph = 
               { 0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
               { 1, 0, 0, 0, 1, 1, 0, 0, 0, 0},
               { 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
               { 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
               { 0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
               { 0, 1, 1, 0, 0, 0, 0, 1, 0, 0},
               { 1, 0, 0, 1, 0, 0, 0, 1, 0, 0},
               { 0, 0, 0, 0, 0, 1, 1, 0, 1, 0},
               { 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
               { 1, 0, 1, 0, 0, 0, 0, 0, 1, 0},

        };

        int length = graph.length;
        int[] color = new int[length];

        int Nr[] = new int [length];
        for ( int i = 0 ; i<length ;i++) {
            Nr[i]=length;
        }
        BruteForce(graph,color, Nr, 0);
        System.out.println("Chromatic number = " + minColor );
    }
}
