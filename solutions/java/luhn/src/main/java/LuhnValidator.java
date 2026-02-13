import java.util.Arrays;

class LuhnValidator {

    private String candidate;

    boolean isValid(String candidate) {
        this.candidate = candidate.replace(" ", "");

        if(!lengthIsValid() || !formatIsValid()) return false;
        return getLuhnSum() % 10 == 0;
    }

    private String reverseCandidate() {
        return new StringBuilder(candidate).reverse().toString();
    }

    private int[] getReverseCandidateAsIntArray() {
        return reverseCandidate()
                .chars()
                .map(Character::getNumericValue)
                .toArray();
    }

    private int doubleIntForLuhnSum(int value) {
        value *= 2;
        return value > 9 ? value - 9 : value;
    }

    private void doubleReverseCandidateAsIntArray(int[] reverseCandidateAsIntArray) {
        for(int i = 0; i < reverseCandidateAsIntArray.length; i++) {
            if(i % 2 == 1) {
                reverseCandidateAsIntArray[i] = doubleIntForLuhnSum(reverseCandidateAsIntArray[i]);
            }
        }
    }

    private int getLuhnSum() {
        int[] reverseCandidateAsIntArray = getReverseCandidateAsIntArray();
        doubleReverseCandidateAsIntArray(reverseCandidateAsIntArray);

        return Arrays.stream(reverseCandidateAsIntArray).sum();
    }

    private boolean formatIsValid() {
        return candidate.chars()
                .allMatch(this::singleCharIsValid);
    }

    private boolean singleCharIsValid(int singleChar) {
        return Character.isDigit(singleChar);
    }

    private boolean lengthIsValid() {
        return candidate.length() > 1;
    }
}
