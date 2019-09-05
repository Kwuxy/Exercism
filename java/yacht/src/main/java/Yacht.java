import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Yacht {

    private int score = 0;
    private int[] dices;

    Yacht(int[] dice, YachtCategory yachtCategory) {
        this.dices = dice;

        calculateScore(yachtCategory);
    }

    private void calculateScore(YachtCategory yachtCategory) {
        switch(yachtCategory) {
            case YACHT: yacht(); break;
            case ONES: ones(); break;
            case TWOS: twos(); break;
            case THREES: threes(); break;
            case FOURS: fours(); break;
            case FIVES: fives(); break;
            case SIXES: sixes(); break;
            case FULL_HOUSE: fullHouse(); break;
            case FOUR_OF_A_KIND: fourOfAKind(); break;
            case LITTLE_STRAIGHT: littleStraight(); break;
            case BIG_STRAIGHT: bigStraight(); break;
            case CHOICE: choice(); break;
        }
    }

    int score() {
        return score;
    }

    private void yacht() {
        if(countDistinctDices() == 1) {
            score = 50;
        }
    }

    private void ones() {
        score = numberCategory(1);
    }

    private void twos() {
        score = numberCategory(2);
    }

    private void threes() {
        score = numberCategory(3);
    }

    private void fours() {
        score = numberCategory(4);
    }

    private void fives() {
        score = numberCategory(5);
    }

    private void sixes() {
        score = numberCategory(6);
    }

    private int numberCategory(int number) {
        return IntStream.of(dices).filter(dice -> dice == number).sum();
    }

    private void fullHouse() {
        if(isFullHouse()) {
            score = IntStream.of(dices).sum();
        }
    }

    private void fourOfAKind() {
        if(isFourOfAKind()) {
            final Optional<Integer> diceValueOccurring4TimesInTheThrow = getDiceValueOccurringAtLeast4TimesInTheThrow();
            diceValueOccurring4TimesInTheThrow.ifPresent(diceValue -> score = diceValue * 4);
        }
    }

    private void littleStraight() {
        if(isLittleStraight()) {
            score = 30;
        }
    }

    private void bigStraight() {
        if(isBigStraight()) {
            score = 30;
        }
    }

    private void choice() {
        score = IntStream.of(dices).sum();
    }

    private boolean isFullHouse() {
        if(countDistinctDices() != 2) return false;

        return eachDiceValueOccurs2or3TimesInTheThrow();
    }

    private boolean isFourOfAKind() {
        return oneDiceValueOccursAtLeast4TimeInTheThrow();
    }

    private boolean isLittleStraight() {
        if(countDistinctDices() != 5) return false;

        final List<Integer> dicesSortedByValue = IntStream.of(dices).sorted().boxed().collect(Collectors.toList());
        return  dicesSortedByValue.get(0) == 1 && dicesSortedByValue.get(4) == 5;
    }

    private boolean isBigStraight() {
        if(countDistinctDices() != 5) return false;

        final List<Integer> dicesSortedByValue = IntStream.of(dices).sorted().boxed().collect(Collectors.toList());
        return  dicesSortedByValue.get(0) == 2 && dicesSortedByValue.get(4) == 6;
    }

    private boolean eachDiceValueOccurs2or3TimesInTheThrow() {
        final Map<Integer, Long> diceOccurrences = getDiceOccurrences();

        for (Integer diceValue : diceOccurrences.keySet()) {
            Long occurrence = diceOccurrences.get(diceValue);
            if(occurrence != 2 && occurrence != 3) {
                return false;
            }
        }

        return true;
    }

    private boolean oneDiceValueOccursAtLeast4TimeInTheThrow() {
        final Map<Integer, Long> diceOccurrences = getDiceOccurrences();
        return diceOccurrences.containsValue(4L) || diceOccurrences.containsValue(5L);
    }

    private Optional<Integer> getDiceValueOccurringAtLeast4TimesInTheThrow() {
        final Map<Integer, Long> diceOccurrences = getDiceOccurrences();

        for (Integer diceValue : diceOccurrences.keySet()) {
            final Long diceOcc = diceOccurrences.get(diceValue);
            if(diceOcc == 4 || diceOcc == 5) {
                return Optional.of(diceValue);
            }
        }

        return Optional.empty();
    }

    private Map<Integer, Long> getDiceOccurrences() {
        return Arrays.stream(dices).boxed()
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
    }

    private long countDistinctDices() {
        return IntStream.of(dices).distinct().count();
    }
}
