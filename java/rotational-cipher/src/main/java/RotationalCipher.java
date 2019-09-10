class RotationalCipher {
    private final int shiftKey;

    RotationalCipher(int shiftKey) {
        this.shiftKey = shiftKey;
    }

    String rotate(String data) {
        return data.chars()
                .map(this::rotateSingleCharacter)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    private char rotateSingleCharacter(int character) {
        if(Character.isLowerCase(character)) {
            return rotateSingleCharacter(character, 'a');
        } else if(Character.isUpperCase(character)) {
            return rotateSingleCharacter(character, 'A');
        }

        return (char) character;
    }

    private char rotateSingleCharacter(int character, int reference) {
        return (char) ((character - reference + shiftKey) % 26 + reference);
    }
}
