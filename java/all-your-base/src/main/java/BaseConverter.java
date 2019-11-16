import java.util.Arrays;

class BaseConverter {

    private final int base10Number;

    public BaseConverter(int fromBase, int[] figures) {
        checkConstructorArgumentsValidity(fromBase, figures);
        this.base10Number = convertToBase10FromBase(fromBase, figures);
    }

    private void checkConstructorArgumentsValidity(int fromBase, int[] figures) {
        checkBaseIsValid(fromBase);
        if(!fromFiguresArePositives(figures)) throw new IllegalArgumentException("Digits may not be negative.");
        if(!fromFiguresAreLessThanBase(figures, fromBase)) {
            throw new IllegalArgumentException("All digits must be strictly less than the base.");
        }
    }

    private void checkBaseIsValid(int base) {
        if(base < 2) throw new IllegalArgumentException("Bases must be at least 2.");
    }

    private boolean fromFiguresAreLessThanBase(int[] figures, int fromBase) {
        return Arrays.stream(figures).allMatch(figure -> figure < fromBase);
    }

    private boolean fromFiguresArePositives(int[] figures) {
        return Arrays.stream(figures).allMatch(figure -> figure >= 0);
    }

    public int[] convertToBase(int toBase) {
        if(base10Number == 0) return new int[1];
        checkBaseIsValid(toBase);

        int referenceNumber = base10Number;
        int maxPower = calculateMaxPower(referenceNumber, toBase);
        int[] toNumber = new int[maxPower];

        for(int power = maxPower - 1; power >= 0; power--) {
            int pow = (int) Math.pow(toBase, power);
            int unit = referenceNumber / pow;
            referenceNumber -= unit * pow;
            toNumber[maxPower - power - 1] = unit;
        }

        return toNumber;
    }

    private int calculateMaxPower(int number, int base) {
        int power = 0;
        while(number >= Math.pow(base, power)) {
            power++;
        }

        return power;
    }

    private int convertToBase10FromBase(int base, int[] fromFigures) {
        int res = 0;

        for (int index = 0; index < fromFigures.length; index++) {
            res += fromFigures[index] * Math.pow(base, fromFigures.length - index - 1);
        }

        return res;
    }
}