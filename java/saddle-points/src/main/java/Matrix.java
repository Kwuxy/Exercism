import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Matrix {
    private final List<List<Integer>> rows;
    private final List<List<Integer>> cols;

    Matrix(List<List<Integer>> values) {
        rows = values;
        cols = translate();
    }

    private List<List<Integer>> translate() {
        if(rows.size() == 0) return null;

        return IntStream.range(0, rows.get(0).size())
                .mapToObj(this::translateColumn)
                .collect(Collectors.toList());
    }

    private List<Integer> translateColumn(int index) {
        return rows.stream()
                .map(row -> row.get(index))
                .collect(Collectors.toList());
    }

    Set<MatrixCoordinate> getSaddlePoints() {
        Set<MatrixCoordinate> result = new HashSet<>();

        if(cols == null) return result;

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            for (int colIndex = 0; colIndex < rows.get(rowIndex).size(); colIndex++) {
                if(isSaddle(rowIndex, colIndex)) {
                    result.add(new MatrixCoordinate(rowIndex + 1, colIndex + 1));
                }
            }
        }

        return result;
    }

    private boolean isSaddle(int rowIndex, int colIndex) {
        return isMaxOfRow(rowIndex, colIndex) && isMinOfCol(rowIndex, colIndex);
    }

    private boolean isMinOfCol(int rowIndex, int colIndex) {
        final ArrayList<Integer> col = new ArrayList<>(cols.get(colIndex));
        col.sort(Comparator.naturalOrder());
        return col.get(0).equals(rows.get(rowIndex).get(colIndex));
    }

    private boolean isMaxOfRow(int rowIndex, int colIndex) {
        final ArrayList<Integer> row = new ArrayList<>(rows.get(rowIndex));
        row.sort(Comparator.naturalOrder());
        return row.get(row.size() - 1).equals(rows.get(rowIndex).get(colIndex));
    }
}
