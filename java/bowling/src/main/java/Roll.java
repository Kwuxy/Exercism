import java.util.ArrayList;
import java.util.List;

class Roll {
    List<Integer> pins;
    RollStatus status;
    int rollNumber;

    Roll(int rollNumber) {
        this.pins = new ArrayList<>();
        status = RollStatus.ON_GOING;
        this.rollNumber = rollNumber;
    }

    void addThrow(int pins) {
        this.pins.add(pins);
        if(isRollOver()) {
            changeRollStatus();
        }
    }

    private void changeRollStatus() {
        if(isStrike()) {
            status = RollStatus.STRIKE;
            return;
        }

        if(isSpare()) {
            status = RollStatus.SPARE;
            return;
        }

        status = RollStatus.FINISHED;
    }

    int score(RollBonus rollBonus) {
        var sum = pins.stream().reduce(0, Integer::sum);

        if(rollBonus == RollBonus.SPARE) {
            sum += pins.get(0);
        }

        if(rollBonus == RollBonus.ONE_STRIKE) {
            sum *= 2;
        }

        if(rollBonus == RollBonus.TWO_STRIKES) {
            sum *= 3;
        }

        return sum;
    }

    private boolean isRollOver() {
        boolean roll0To8IsOver = rollNumber != 9 &&
                (pins.size() == 2 || score(null) >= 10);
        boolean roll9IsOver = rollNumber == 9 &&
                (isStrike() && pins.size() == 2 || isSpare() && pins.size() == 3);

        return roll9IsOver || roll0To8IsOver;
    }

    private boolean isStrike() {
        return pins.size() == 1 && pins.get(0) == 10;
    }

    private boolean isSpare() {
        return !isStrike() && pins.size() == 2 && pins.get(0) + pins.get(1) == 10;
    }
}