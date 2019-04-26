
public class LocalSearch {

    public static void localSearch(int cycle[], int[][] costMatrix) {

        int size = cycle.length;
        int newCycle[] = new int[size];

        for (int i = 0; i < size; i++) {
            newCycle[i] = cycle[i];
        }

        boolean improve = true;

        while (improve) {
            int bestCycle = cycleLength(cycle, costMatrix);
            improve = false;
            for (int i = 1; i < size - 1; i++) {
                for (int k = i + 1; k < size - 1; k++) {
                    twoOptSwap(cycle, newCycle, i, k);
                    int new_distance = cycleLength(newCycle, costMatrix);
                    if (new_distance < bestCycle) {
                        improve = true;
                        for (int j = 0; j < size; j++) {
                            cycle[j] = newCycle[j];
                        }
                        bestCycle = new_distance;
                    }
                }
            }
        }
    }

    private static void twoOptSwap(int[] cycle, int[] newCycle, int i, int k) {
        int size = cycle.length;
        for (int c = 0; c <= i - 1; ++c) {
            newCycle[c] = cycle[c];
        }
        int dec = 0;

        for (int c = i; c <= k; ++c) {
            newCycle[c] = cycle[k - dec];
            dec++;
        }

        for (int c = k + 1; c < size; ++c) {
            newCycle[c] = cycle[c];
        }
    }

    private static int cycleLength(int[] cycle, int[][] costMatrix) {
        int distance = 0;
        for (int i = 0; i < cycle.length - 1; i++) {
            distance += costMatrix[cycle[i]][cycle[i + 1]];
        }
        return distance;
    }
}