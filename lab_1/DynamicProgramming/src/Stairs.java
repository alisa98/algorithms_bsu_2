public class Stairs {

    static int stairs(int lengthOfStaircase, int step)
    {
        int[] numberOfWays = new int[100];
        numberOfWays[0] = 1;

        for (int i = 1; i <= step; i++) {
            for (int j = 0; j <= lengthOfStaircase; j++) {
                if (j >= i) {
                    numberOfWays[j] += numberOfWays[j - i];
                }
            }
        }
        return numberOfWays[lengthOfStaircase];
    }

    public static void main(String argc[]){

        int lengthOfStaircase = 10 ;
        int step = lengthOfStaircase;
        System.out.println(stairs(lengthOfStaircase, step));

    }
}