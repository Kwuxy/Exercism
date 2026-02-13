class Queen {
    final int x;
    final int y;

    public Queen(int x, int y) {
        this.x = x;
        this.y = y;

        checkArgumentsValidity();
    }

    private void checkArgumentsValidity() {
        isValid("row", x);
        isValid("column", y);
    }

    private void isValid(String orientation, int value) {
        if(value < 0) {
            throw new IllegalArgumentException("Queen position must have positive " + orientation + ".");
        }

        if(value > 7) {
            throw new IllegalArgumentException("Queen position must have " + orientation + " <= 7.");
        }
    }
}

class QueenAttackCalculator {

    private final Queen white;
    private final Queen black;

    public QueenAttackCalculator(Queen white, Queen black) {
        if(white == null || black == null) {
            throw new IllegalArgumentException("You must supply valid positions for both Queens.");
        }

        if(white.x == black.x && white.y == black.y) {
            throw new IllegalArgumentException("Queens cannot occupy the same position.");
        }

        this.white = white;
        this.black = black;
    }

    public boolean canQueensAttackOneAnother() {
        return canAttackRow() || canAttackColumn() || canAttackDiagonal();
    }

    private boolean canAttackDiagonal() {
        return Math.abs(white.x - black.x) == Math.abs(white.y - black.y);
    }

    private boolean canAttackColumn() {
        return white.y == black.y;
    }

    private boolean canAttackRow() {
        return white.x == black.x;
    }
}