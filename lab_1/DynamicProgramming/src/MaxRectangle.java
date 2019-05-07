public class MaxRectangle
{
    public static int maxRectangle(int [][] matrix) {

        int rows = matrix.length;
        int columns = matrix[0].length;
        int maxArea = 0;
        int[] column = new int[columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 1) {
                    column[j]++;
                } else column[j] = 0;
                int height = column[j];
                for (int k = j-1; k >= 0; k--) {
                    int length = j-k+1;
                    height = Math.min(height, column[k]);
                    maxArea = Math.max(maxArea, length*height);
                }
                maxArea = Math.max(maxArea, column[j]);
            }
        }
        return maxArea;
    }

    public static void main(String[] args)
    {
        int matrix[][] = {      {0, 1, 1, 0, 1},
                {0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0}};

        System.out.println("Area of maximum rectangle: "+maxRectangle(matrix));
    }
}