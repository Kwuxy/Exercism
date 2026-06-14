import java.util.Arrays;
import java.util.Collections;
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
            case ONES -> this.getValueSum(1);
            case TWOS -> this.getValueSum(2);
            case THREES -> this.getValueSum(3);
            case FOURS -> this.getValueSum(4);
            case FIVES -> this.getValueSum(5);
            case SIXES -> this.getValueSum(6);
            case FULL_HOUSE -> isFullHouse() ? IntStream.of(this.dice).sum() : 0;
            case FOUR_OF_A_KIND -> getFourOfAKind() * 4;
            case LITTLE_STRAIGHT -> isLittleStrait() ? 30 : 0;
            case BIG_STRAIGHT -> isBigStrait() ? 30 : 0;
            case CHOICE -> IntStream.of(this.dice).sum();
        };
    }

    private int getValueSum(int value) {
        return IntStream.of(this.dice).filter(i -> i == value).sum();
    }

    private boolean isYacht() {
        return IntStream.of(this.dice).distinct().count() == 1;
    }

    private boolean isFullHouse() {
        int[] values = IntStream.of(this.dice).distinct().toArray();
        if (values.length != 2) {
            return false;
        }

        long count = IntStream.of(this.dice).filter(i -> i == values[0]).count();
        return count == 2 || count == 3;
    }

    private int getFourOfAKind() {
        return IntStream.of(this.dice)
                .filter(i -> Collections.frequency(IntStream.of(dice).boxed().toList(), i) >= 4)
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
}
