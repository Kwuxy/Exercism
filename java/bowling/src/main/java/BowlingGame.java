import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class BowlingGame {
    private List<BowlingThrow> bowlingThrows;
    private int currentRoll;

    public BowlingGame() {
        bowlingThrows = new ArrayList<>();
        currentRoll = 0;
    }

    public void roll(int pins) {
        bowlingThrows.add(new BowlingThrow(currentRoll, pins));
        updateCurrentRoll();
    }

    public int score() {
        calculateThrowsScore();

        return bowlingThrows.stream()
                .mapToInt(t -> t.score)
                .sum();
    }

    private void updateCurrentRoll() {
        final List<BowlingThrow> throwsInRoll = getThrowsOfRoll(currentRoll).collect(Collectors.toList());
        if((currentRoll != 9 && (throwsInRoll.size() >= 2 || throwsInRoll.get(0).pins == 10)) ||
                (currentRoll == 9 && isRoll9Over(throwsInRoll)) ||
                currentRoll > 9) {
            currentRoll++;
        }
    }

    private boolean isRoll9Over(List<BowlingThrow> throwsInRoll) {
        final int size = throwsInRoll.size();
        if(size == 0 || size == 1) return false;
        if(size == 2 && throwsInRoll.get(0).pins == 10) return false;
        if(size == 2 && throwsInRoll.get(0).pins + throwsInRoll.get(1).pins == 10) return false;
        return true;
    }

    private void calculateThrowsScore() {
        checkGameValidity();
        determineSpecialThrows();
        List<BowlingThrow> reversedThrows = reverseThrows();
        int throwBonus1 = 0;
        int throwBonus2 = 0;
        int totalBonus;

        for (BowlingThrow t : reversedThrows) {
            isThrowValid(t);

            if(t.specialThrow == SpecialThrow.SPARE && t.roll != 9) {
                totalBonus = throwBonus1;
            } else if(t.specialThrow == SpecialThrow.STRIKE) {
                totalBonus = throwBonus1 + throwBonus2;
                if(t.roll == 9) {
                    totalBonus = 0;
                }
            } else {
                totalBonus = 0;
            }
            t.score = t.pins + totalBonus;

            throwBonus2 = throwBonus1;
            throwBonus1 = t.pins;
        }
    }

    private void isThrowValid(BowlingThrow t) {
        if(t.pins < 0) {
            throw new IllegalStateException("Negative roll is invalid");
        }

        if(t.pins > 10) {
            throw new IllegalStateException("Pin count exceeds pins on the lane");
        }
    }

    private void checkGameValidity() {
        if(currentRoll < 9) {
            throw new IllegalStateException("Score cannot be taken until the end of the game");
        }
        if(currentRoll > 10) {
            throw new IllegalStateException("Cannot roll after game is over");
        }
        if(!isRoll9Over(getThrowsOfRoll(9).collect(Collectors.toList()))) {
            throw new IllegalStateException("Score cannot be taken until the end of the game");
        }

        checkRollValidity();
    }

    private void checkRollValidity() {
        final boolean roll0To8WorthMoreThan10Pins = IntStream.range(0, 9)
                .mapToObj(this::getThrowsOfRoll)
                .anyMatch(t -> t.mapToInt(th -> th.pins).sum() > 10);
        final boolean isRoll9Valid = isRoll9Valid();

        if(roll0To8WorthMoreThan10Pins || !isRoll9Valid) {
            throw new IllegalStateException("Pin count exceeds pins on the lane");
        }
    }

    private boolean isRoll9Valid() {
        final List<BowlingThrow> bowlingThrows = getThrowsOfRoll(9).collect(Collectors.toList());
        if(bowlingThrows.size() == 3 &&
            bowlingThrows.get(0).pins == 10 &&
            bowlingThrows.get(1).pins != 10 &&
            bowlingThrows.get(1).pins + bowlingThrows.get(2).pins > 10) {
            return false;
        }

        return true;
    }

    private void determineSpecialThrows() {
        IntStream.range(0, 10)
                .mapToObj(this::getThrowsOfRoll)
                .forEach(this::updateSpecialThrow);
    }

    private void updateSpecialThrow(Stream<BowlingThrow> bowlingThrowStream) {
        final List<BowlingThrow> throwList = bowlingThrowStream.collect(Collectors.toList());
        final int pinsValue = throwList.stream().mapToInt(t -> t.pins).sum();
        final long throwsNumber = throwList.size();

        if(throwsNumber == 2 && pinsValue == 10) {
            setSpecialThrow(throwList, SpecialThrow.SPARE);
        } else if(throwsNumber == 1 && pinsValue == 10) {
            setSpecialThrow(throwList, SpecialThrow.STRIKE);
        }
    }

    private void setSpecialThrow(List<BowlingThrow> throwList, SpecialThrow specialThrow) {
        throwList.get(throwList.size() - 1).specialThrow = specialThrow;
    }

    private Stream<BowlingThrow> getThrowsOfRoll(int roll) {
        return bowlingThrows.stream().filter(t -> t.roll == roll);
    }

    private List<BowlingThrow> reverseThrows() {
        List<BowlingThrow> reversedThrows = new ArrayList<>(bowlingThrows);
        Collections.reverse(reversedThrows);

        return reversedThrows;
    }
}