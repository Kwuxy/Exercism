import java.util.stream.IntStream;

class SumOfMultiples {

    private final int[] set;
    private final int limit;

    SumOfMultiples(int number, int[] set) {
        this.set = cleanSet(set);
        this.limit = number;
    }

    private int[] cleanSet(int[] set) {
        return IntStream.of(set)
                .filter(value -> value != 0)
                .toArray();
    }

    int getSum() {
        return IntStream.range(1, limit)
                .filter(this::isMultipleOfSet)
                .sum();
    }

    private boolean isMultipleOfSet(int i) {
        return IntStream.of(set)
                .anyMatch(value -> i % value == 0);
    }

}
