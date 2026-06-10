import java.util.Arrays;
import java.util.stream.Stream;

class Matrix {
    private final int[][] matrix;

    Matrix(String matrixAsString) {
        matrix = Arrays.stream(matrixAsString.split("\n"))
                .map(line -> Stream.of(line.split(" ")).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
    }

    int[] getRow(int rowNumber) {
        return matrix[rowNumber - 1];
    }

    int[] getColumn(int columnNumber) {
        return Stream.of(matrix).mapToInt(row -> row[columnNumber - 1]).toArray();
    }
}
