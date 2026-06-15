import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Yacht {
    private final int[] dice;
    private final YachtCategory yachtCategory;

    Yacht(int[] dice, YachtCategory yachtCategory) {
        this.dice = dice;
        this.yachtCategory = yachtCategory;
    }

    int score() {
        return switch (this.yachtCategory) {
            case YACHT -> isYacht() ? 50 : 0;
            case ONES -> this.scoreValue(1);
            case TWOS -> this.scoreValue(2);
            case THREES -> this.scoreValue(3);
            case FOURS -> this.scoreValue(4);
            case FIVES -> this.scoreValue(5);
            case SIXES -> this.scoreValue(6);
            case FULL_HOUSE -> isFullHouse() ? IntStream.of(this.dice).sum() : 0;
            case FOUR_OF_A_KIND -> getFourOfAKind() * 4;
            case LITTLE_STRAIGHT -> isLittleStrait() ? 30 : 0;
            case BIG_STRAIGHT -> isBigStrait() ? 30 : 0;
            case CHOICE -> IntStream.of(this.dice).sum();
        };
    }

    private int scoreValue(int value) {
        return IntStream.of(this.dice).filter(i -> i == value).sum();
    }

    private boolean isYacht() {
        return getSet().size() == 1;
    }

    private boolean isFullHouse() {
        Set<Integer> set = getSet();
        if (set.size() != 2) {
            return false;
        }

        long count = this.countOccurrences(set.iterator().next());
        return count == 2 || count == 3;
    }

    private int getFourOfAKind() {
        return getSet().stream()
                .filter(i -> countOccurrences(i) >= 4)
                .findFirst()
                .orElse(0);
    }

    private boolean isLittleStrait() {
        Arrays.sort(this.dice);
        return Arrays.equals(this.dice, new int[]{1, 2, 3, 4, 5});
    }

    private boolean isBigStrait() {
        Arrays.sort(this.dice);
        return Arrays.equals(this.dice, new int[]{2, 3, 4, 5, 6});
    }

    private Set<Integer> getSet() {
        return IntStream.of(this.dice).boxed().collect(Collectors.toSet());
    }

    private long countOccurrences(int value) {
        return IntStream.of(this.dice).filter(i -> i == value).count();
    }
}
