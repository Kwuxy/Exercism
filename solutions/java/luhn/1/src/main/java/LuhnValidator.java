import java.util.Arrays;

class LuhnValidator {

    boolean isValid(String candidate) {
        if (!isLegal(candidate))
            return false;

        int[] values = cleanInput(candidate);
        if (values.length < 2)
            return false;

        for (int i = values.length - 2; i >= 0; i -= 2) {
            int value = values[i] * 2;
            values[i] = value > 9 ? value - 9 : value;
        }

        return Arrays.stream(values).sum() % 10 == 0;
    }

    private boolean isLegal(String str) {
        return str.chars().noneMatch(c -> !Character.isDigit(c) && !Character.isSpaceChar(c));
    }

    private int[] cleanInput(String str) {
        return str.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .toArray();
    }

}
