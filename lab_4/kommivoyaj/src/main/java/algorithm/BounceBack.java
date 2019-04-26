import java.util.Arrays;

public class BounceBack {

    static int V = 13;

    static int finalCycle[] = new int[V + 1];

    static boolean visited[] = new boolean[V];

    static int minValue = Integer.MAX_VALUE;

    static void copyToFinalResult(int currentCycle[])
    {
        for (int i = 0; i < V; i++)
            finalCycle[i] = currentCycle[i];
        finalCycle[V] = currentCycle[0];
    }

    static int findFirstMinimumCost(int matrix[][], int i)
    {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < V; k++)
            if (matrix[i][k] < min && i != k) {
                min = matrix[i][k];
            }
        return min;
    }

    static int findSecondMinimumCost(int marix[][], int i)
    {
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        for (int j = 0; j< V; j++)
        {
            if (i == j){
                continue;
            }

            if (marix[i][j] <= first)
            {
                second = first;
                first = marix[i][j];
            }
            else if (marix[i][j] <= second && marix[i][j] != first) {
                second = marix[i][j];
            }
        }
        return second;
    }

    static void HamCycleUtil(int matrix[][], int currentBound, int currentWeight, int level, int curentPath[])
    {
        if (level == V)
        {
            if (matrix[curentPath[level - 1]][curentPath[0]] != 0)
            {
                int currentResult = currentWeight + matrix[curentPath[level-1]][curentPath[0]];
                if (currentResult < minValue)  {
                    copyToFinalResult(curentPath);
                    minValue = currentResult;
                }
            }
            return;
        }

        for (int i = 0; i < V; i++)
        {
            if (matrix[curentPath[level-1]][i] != 0 && visited[i] == false)
            {
                int temp = currentBound;
                currentWeight += matrix[curentPath[level - 1]][i];

                if (level==1) {
                    currentBound -= ((findFirstMinimumCost(matrix, curentPath[level - 1]) + findFirstMinimumCost(matrix, i)) / 2);
                }
                else {
                    currentBound -= ((findSecondMinimumCost(matrix, curentPath[level - 1]) + findFirstMinimumCost(matrix, i)) / 2);
                }

                if (currentBound + currentWeight < minValue)
                {
                    curentPath[level] = i;
                    visited[i] = true;
                    HamCycleUtil(matrix, currentBound, currentWeight, level + 1, curentPath);
                }
                currentWeight -= matrix[curentPath[level-1]][i];
                currentBound = temp;
                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++) {
                    visited[curentPath[j]] = true;
                }
            }
        }
    }

    static void minimalHamiltonianCycle(int marix[][])
    {
        int curentPath[] = new int[V + 1];
        int currentBound = 0;
        Arrays.fill(curentPath, -1);
        Arrays.fill(visited, false);

        for (int i = 0; i < V; i++) {
            currentBound += (findFirstMinimumCost(marix, i) + findSecondMinimumCost(marix, i));
        }

        if (currentBound==1)
        {
            currentBound = (currentBound / 2) + 1;
        }
        else{
            currentBound = currentBound/2;
        }

        visited[0] = true;
        curentPath[0] = 0;
        HamCycleUtil(marix, currentBound, 0, 1, curentPath);
    }


    public static void main(String[] args)
    {
        int matrix[][] = {
                {0, 12, 11, 14, 0, 0, 0, 0, 0, 11, 3, 7, 11},
                {12, 0, 12, 0, 0, 8, 16, 0, 0, 11, 0, 0, 0},
                {11, 12, 0, 0, 6, 3, 0, 0, 0, 0, 0, 0, 0},
                {14, 0, 0, 0, 4, 0, 0, 6, 0, 0, 0, 0, 5},
                {0, 0, 6, 4, 0, 0, 0, 12, 0, 0, 0, 0, 0},
                {0, 8, 3, 0, 0, 0, 0, 16, 6, 0, 0, 0, 0},
                {0, 16, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0},
                {0, 0, 0, 6, 12, 16, 0, 0, 13, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 6, 8, 13, 0, 0, 0, 0, 0},
                {11, 11, 0, 0, 0, 0, 0, 0, 0, 0, 7, 11, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 5, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 0, 11, 5, 0, 11},
                {11, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 11, 0}


        };
        minimalHamiltonianCycle(matrix);
        System.out.println("Minimum lenght : "+  minValue);
        System.out.print("Minimal Hamiltonian cycle : ");
        for (int i = 0; i <= V; i++)
        {
            System.out.print( finalCycle[i]+" ");
        }
    }
}