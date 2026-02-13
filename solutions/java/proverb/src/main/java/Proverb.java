class Proverb {
    private final String[] words;
    private final StringBuilder proverb = new StringBuilder();

    Proverb(String[] words) {
        this.words = words;
    }

    String recite() {
        if(!isWordsLengthAvailable()) {
            return "";
        }

        return generateProverb();
    }

    private boolean isWordsLengthAvailable() {
        return words.length > 0;
    }

    private String generateProverb() {
        generateProverbBody();
        generateProverbEnding();

        return proverb.toString();
    }

    private void generateProverbBody() {
        for(int i = 0; i < words.length - 1; i++) {
            String PROVERB_LINE = "For want of a %s the %s was lost.\n";
            proverb.append(String.format(PROVERB_LINE, words[i], words[i + 1]));
        }
    }

    private void generateProverbEnding() {
        String END_OF_PROVERB = "And all for the want of a %s.";
        proverb.append(String.format(END_OF_PROVERB, words[0]));
    }
}
