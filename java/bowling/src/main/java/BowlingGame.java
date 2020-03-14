import java.util.ArrayList;
import java.util.List;

class BowlingGame {
    private List<Roll> rolls;

    public BowlingGame() {
        this.rolls = new ArrayList<>();
    }

    public void roll(int pins) {
        var currentRoll = getCurrentRoll();
        currentRoll.addThrow(pins);
    }

    public int score() {
        int result = 0;
        for(int rollIndex = 0; rollIndex < rolls.size(); ++rollIndex) {
            final RollBonus rollBonus = getRollBonus(rollIndex);
            int rollScore = rolls.get(rollIndex).score(rollBonus);
            result += rollScore;
        }
        return result;
    }

    private RollBonus getRollBonus(int rollIndex) {
        if(rollIndex == 0) return null;
        if(rollIndex == 1) return calculateRollBonusOfSingleRoll(rollIndex - 1);
        RollBonus bonus = calculateRollBonusOfSingleRoll(rollIndex - 1);
        if (bonus == RollBonus.ONE_STRIKE && calculateRollBonusOfSingleRoll(rollIndex - 2) == RollBonus.ONE_STRIKE) {
            return RollBonus.TWO_STRIKES;
        }
        return bonus;
    }

    private RollBonus calculateRollBonusOfSingleRoll(int rollIndex) {
        if(rollIndex < 0) return RollBonus.NONE;

        Roll roll = rolls.get(rollIndex);
        if(roll.status == RollStatus.STRIKE) return RollBonus.ONE_STRIKE;
        if(roll.status == RollStatus.SPARE) return RollBonus.SPARE;
        return RollBonus.NONE;
    }

    private Roll getCurrentRoll() {
        if(rolls.isEmpty()) {
            return addRoll(0);
        }

        Roll lastRoll = rolls.get(rolls.size() - 1);
        if(lastRoll.status != RollStatus.ON_GOING) {
            lastRoll = addRoll(rolls.size());
        }

        return lastRoll;
    }

    private Roll addRoll(int rollIndex) {
        final Roll newRoll = new Roll(rollIndex);
        rolls.add(newRoll);
        return newRoll;
    }
}