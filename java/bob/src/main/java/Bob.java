class Bob {

    private String phrase;

    public String hey(String phrase) {
        this.phrase = phrase;

        if(isEmpty()) {
            return "Fine. Be that way!";
        }

        cleanPhrase();

        if(isQuestion() && isYell()) {
            return "Calm down, I know what I'm doing!";
        }

        if(isQuestion()) {
            return "Sure.";
        }

        if(isYell()) {
            return "Whoa, chill out!";
        }

        return "Whatever.";
    }

    private boolean isEmpty() {
        return phrase.replaceAll("\\s", "").isEmpty();
    }

    private boolean isYell() {
        return !phrase.isEmpty() && containsLetters() && phrase.toUpperCase().equals(phrase);
    }

    private boolean isQuestion() {
        return !phrase.isEmpty() && getLastCharacter() == '?';
    }

    private char getLastCharacter() {
        return phrase.charAt(phrase.length() - 1);
    }

    private void cleanPhrase() {
        phrase = phrase.replaceAll("[^\\p{Alpha}?]", "");
    }

    private boolean containsLetters() {
        return phrase.matches(".*\\p{Alpha}+.*");
    }
}