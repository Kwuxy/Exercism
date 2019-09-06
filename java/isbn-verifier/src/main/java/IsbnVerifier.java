class IsbnVerifier {

    boolean isValid(String stringToVerify) {
        String ISBNIntString = convertISBNStringAsIntString(stringToVerify);
        if(!isGoodLength(ISBNIntString)) return false;
        if(!isWellFormatted(ISBNIntString)) return false;

        int[] ISBNIntArray = convertISBNLightStringAsIntArray(ISBNIntString);

        return calculateISBNSum(ISBNIntArray) == 0;
    }

    private boolean isGoodLength(String stringToVerify) {
        return stringToVerify.length() == 10;
    }

    private boolean isWellFormatted(String stringToVerify) {
        final char lastCharacter = stringToVerify.charAt(stringToVerify.length() - 1);
        return areBodyCharactersWellFormatted(stringToVerify) && isLastCharacterWellFormatted(lastCharacter);
    }

    private boolean areBodyCharactersWellFormatted(String stringToVerify) {
        return stringToVerify.chars()
                .limit(stringToVerify.length() - 2)
                .allMatch(this::isSingleBodyCharacterWellFormatted);
    }

    private boolean isSingleBodyCharacterWellFormatted(int characterToVerify) {
        return Character.isDigit(characterToVerify);
    }

    private boolean isLastCharacterWellFormatted(int lastCharacter) {
        return isSingleBodyCharacterWellFormatted(lastCharacter) || lastCharacter == 'X';
    }

    private String convertISBNStringAsIntString(String stringToConvert) {
        return stringToConvert.replace("-", "");
    }

    private int[] convertISBNLightStringAsIntArray(String stringToConvert) {
        return stringToConvert.chars()
                .map(this::convertISBNCharToInt)
                .toArray();
    }

    private Integer convertISBNCharToInt(int character) {
        if(character == 'X') {
            return 10;
        }

        return Character.getNumericValue(character);
    }

    private int calculateISBNSum(int[] ISBNIntArray) {
        int sum = 0;
        for (int i = 0; i < ISBNIntArray.length; i++) {
            sum += (10 - i) * ISBNIntArray[i];
        }

        return sum % 11;
    }

}
