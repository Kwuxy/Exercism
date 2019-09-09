import java.util.Arrays;
import java.util.stream.IntStream;

class Matrix {
    private final int[][] rows;
    private final int[][] cols;

    Matrix(String matrixAsString) {
        rows = parseMatrix(matrixAsString);
        cols = transpose();
    }

    int[] getRow(int rowNumber) {
        return rows[rowNumber - 1];
    }

    int[] getColumn(int columnNumber) {
        return cols[columnNumber - 1];
    }

    private int[][] parseMatrix(String matrixAsString) {
        return Arrays.stream(matrixAsString.split("\n"))
                .map(this::parseRows)
                .map(this::parseSingleRow)
                .toArray(int[][]::new);
    }

    private String[] parseRows(String row) {
        return row.split(" ");
    }

    private int[] parseSingleRow(String[] row) {
        return Arrays.stream(row)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private int[][] transpose() {
        return IntStream.range(0, rows[0].length)
                .mapToObj(this::transposeColumn)
                .toArray(int[][]::new);
    }

    private int[] transposeColumn(int columnIndex) {
        return Arrays.stream(rows)
                .mapToInt(row -> row[columnIndex])
                .toArray();
    }
}
