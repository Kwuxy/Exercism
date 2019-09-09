import java.util.Arrays;

class Matrix {
    private final int[][] matrix;

    Matrix(String matrixAsString) {
        matrix = convertStringMatrixToMatrix(matrixAsString);
//        matrix = convertInteger2DArrayToInt2DArray(tempMatrix);
    }

    int[] getRow(int rowNumber) {
        return matrix[rowNumber - 1];
    }

    int[] getColumn(int columnNumber) {
        int[] result = new int[matrix.length];

        for(int i = 0; i < matrix.length; i++) {
            int[] matrixRow = matrix[i];
            result[i] = matrixRow[columnNumber - 1];
        }

        return result;
    }

    private int[][] convertInteger2DArrayToInt2DArray(Integer[][] input) {
        int[][] result = new int[input.length][];

        for (int counter = 0; counter < input.length; counter++) {
            int[] array = new int[input[counter].length];
            Integer[] values = input[counter];

            for (int i = 0; i < values.length; i++) {
                array[i] = values[i];
            }

            result[counter] = array;
        }

        return result;
    }

    private int[][] convertStringMatrixToMatrix(String matrixAsString) {
        return Arrays.stream(matrixAsString.split("\n"))
                .map(this::cutRow)
                .map(this::convertChoppedRowToIntArray)
                .toArray(int[][]::new);
    }

    private String[] cutRow(String row) {
        return row.split(" ");
    }

    private int[] convertChoppedRowToIntArray(String[] choppedRow) {
        return Arrays.stream(choppedRow)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
