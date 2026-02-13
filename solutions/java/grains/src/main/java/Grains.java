import java.math.BigInteger;
import java.util.stream.IntStream;

class Grains {

    BigInteger computeNumberOfGrainsOnSquare(final int square) {
        if(!isValid(square)) throw new IllegalArgumentException("square must be between 1 and 64");

        return new BigInteger("1").shiftLeft(square - 1);
    }

    BigInteger computeTotalNumberOfGrainsOnBoard() {
        return IntStream.rangeClosed(1, 64)
                .mapToObj(this::computeNumberOfGrainsOnSquare)
                .reduce(new BigInteger("0"), BigInteger::add);
    }

    private boolean isValid(final int square) {
        return square > 0 && square < 65;
    }
}
