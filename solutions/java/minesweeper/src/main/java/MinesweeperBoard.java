import java.util.ArrayList;
import java.util.List;

class MinesweeperBoard {

    private final List<String> board;

    public MinesweeperBoard(List<String> board) {
        this.board = board;
    }

    public List<String> withNumbers() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            result.add(transformRow(i));
        }
        return result;
    }

    private String transformRow(int rowIndex) {
        final char[] row = board.get(rowIndex).toCharArray();
        for (int colIndex = 0; colIndex < row.length; colIndex++) {
            row[colIndex] = transformCell(rowIndex, colIndex);

        }

        return String.valueOf(row);
    }

    private char transformCell(int rowIndex, int colIndex) {
        if(board.get(rowIndex).charAt(colIndex) == '*') {
            return '*';
        }

        int mineCounter = countNeighbourMines(rowIndex, colIndex);
        return mineCounter == 0 ? ' ' : Character.forDigit(mineCounter, 10);
    }

    private int countNeighbourMines(int i, int j) {
        return isMine(i - 1, j - 1) + isMine(i - 1, j) + isMine(i - 1, j + 1)
                + isMine(i, j - 1) + isMine(i, j + 1)
                + isMine(i + 1, j - 1) + isMine(i + 1, j) + isMine(i + 1, j + 1);
    }

    private int isMine(int i, int j) {
        try {
            return board.get(i).charAt(j) == '*' ? 1 : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}