import java.util.stream.IntStream;

class DifferenceOfSquaresCalculator {

    int computeSquareOfSumTo(int input) {
        return computeSquare(IntStream.range(1, input + 1).sum());
    }

    int computeSumOfSquaresTo(int input) {
        return IntStream.range(1, input + 1).map(this::computeSquare).sum();
    }

    private int computeSquare(int input) {
        return (int) Math.pow(input, 2);
    }

    int computeDifferenceOfSquares(int input) {
        return computeSquareOfSumTo(input) - computeSumOfSquaresTo(input);
    }
}
