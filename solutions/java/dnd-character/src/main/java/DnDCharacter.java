import java.util.ArrayList;

class DnDCharacter {
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    DnDCharacter() {
        strength = ability();
        dexterity = ability();
        constitution = ability();
        intelligence = ability();
        wisdom = ability();
        charisma = ability();
    }

    int ability() {
        return throwDicesForAbilityGeneration().stream()
                .sorted()
                .skip(1)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private ArrayList<Integer> throwDicesForAbilityGeneration() {
        final int DICE_THROWS = 4;
        final ArrayList<Integer> throwsResult = new ArrayList<>();

        for(int i = 0; i < DICE_THROWS; i++) {
            throwsResult.add(throwDice());
        }

        return throwsResult;
    }

    private int throwDice() {
        return (int) (Math.random() * 6 + 1);
    }

    int modifier(int input) {
        return (int) Math.floor((input - 10) / 2.);
    }

    int getStrength() {
        return strength;
    }

    int getDexterity() {
        return dexterity;
    }

    int getConstitution() {
        return constitution;
    }

    int getIntelligence() {
        return intelligence;
    }

    int getWisdom() {
        return wisdom;
    }

    int getCharisma() {
        return charisma;
    }

    int getHitpoints() {
        return 10 + modifier(getConstitution());
    }

}
