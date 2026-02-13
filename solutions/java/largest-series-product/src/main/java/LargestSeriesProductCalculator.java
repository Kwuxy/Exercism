class LargestSeriesProductCalculator {
    private final String numberAsString;

    LargestSeriesProductCalculator(String inputNumber) {
        if(!stringContainsOnlyNumber(inputNumber)) {
            throw new IllegalArgumentException("String to search may only contain digits.");
        }

        this.numberAsString = inputNumber;
    }

    long calculateLargestProductForSeriesLength(int numberOfDigits) {
        if(numberOfDigits > numberAsString.length()) {
            throw new IllegalArgumentException("Series length must be less than or equal to the length of the string to search.");
        }

        if(numberOfDigits < 0) {
            throw new IllegalArgumentException("Series length must be non-negative.");
        }

        long max = 0;
        for(int i = 0; i <= numberAsString.length() - numberOfDigits; i++) {
            String subNumber = numberAsString.substring(i, i + numberOfDigits);
            long stringProduct = calculateProductOfSubString(subNumber);
            max = stringProduct > max ? stringProduct : max;
        }
        return max;
    }

    private long calculateProductOfSubString(String stringToCalculate) {
        return stringToCalculate.chars()
                .mapToLong(Character::getNumericValue)
                .reduce(1, (a, b) -> a * b);
    }

    private boolean stringContainsOnlyNumber(String inputNumber) {
        return inputNumber.chars().allMatch(Character::isDigit);
    }
}
