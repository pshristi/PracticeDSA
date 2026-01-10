class MaximalSquare {

    public static void main(String[] args) {
        char[][] matrix = new char[][]{
                {'0', '1'}, {'1', '0'}
        };
        System.out.println(maximalSquare(matrix));
    }

    private static int maximalSquare(char[][] matrix) {
        Integer m = matrix.length;
        Integer n = matrix[0].length;
        int[][] sizeMatrix = new int[m][n];
        Integer maxLength = 0;
        if (m == 0 || n == 0)
            return maxLength;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        sizeMatrix[i][j] = 1;
                    } else {
                        sizeMatrix[i][j] = 1 + Math.min(sizeMatrix[i - 1][j - 1], Math.min(sizeMatrix[i - 1][j], sizeMatrix[i][j - 1]));
                    }
                    maxLength = Math.max(maxLength, sizeMatrix[i][j]);
                }
            }
        }

        return maxLength * maxLength;
    }
}
