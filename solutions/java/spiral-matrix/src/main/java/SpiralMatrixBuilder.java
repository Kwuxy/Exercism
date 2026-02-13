class SpiralMatrixBuilder {
    private int size;

    public int[][] buildMatrixOfSize(int size) {
        this.size = size;
        final int[][] matrix = generateMatrix();
        return calculateAllMatrixCellValue(matrix);
    }

    private int[][] calculateAllMatrixCellValue(int[][] matrix) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                matrix[row][col] = calculateMatrixCellValue(row, col);
            }
        }

        return matrix;
    }

    private int[][] generateMatrix() {
        return new int[size][size];
    }

    private int calculateMatrixCellValue(int row, int col) {
        return row <= col ? calculateMatrixCellValueTopRight(row, col) : calculateMatrixCellValueBottomLeft(row, col);
    }

    private int calculateMatrixCellValueBottomLeft(int row, int col) {
        int x = calculateConcentricCercle(row, col);
        return (size * size) - ((size - 2 * x - 2) * (size - 2 * x - 2) + (row - x) + (col - x)) + 1;
    }

    private int calculateMatrixCellValueTopRight(int row, int col) {
        int x = calculateConcentricCercle(row, col);
        return (size * size) - ((size - 2 * x) * (size - 2 * x) - (col - x) - (row - x)) + 1;
    }

    private int calculateConcentricCercle(int row, int col) {
        return Math.min(Math.min(row, col), Math.min(size - row - 1, size - col - 1));
    }
}