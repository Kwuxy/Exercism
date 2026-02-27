class SqueakyClean {
    static final String[] leetspeakChars = {"43017", "aeolt"};

    static String clean(String identifier) {
        StringBuilder result = new StringBuilder();
        boolean uppercaseNext = false;

        for (char raw : identifier.toCharArray()) {
            char c = normalizeWhitespace(raw);
            if (c == '-') {
                uppercaseNext = true;
                continue;
            }

            c = applyUppercaseIfNeeded(c, uppercaseNext);
            uppercaseNext = false;

            c = deLeet(c);

            if (!isValid(c))
                continue;

            result.append(c);
        }

        return result.toString();
    }

    static char normalizeWhitespace(char c) {
        return Character.isWhitespace(c) ? '_' : c;
    }

    static char applyUppercaseIfNeeded(char c, boolean toUppercase) {
        return toUppercase ? Character.toUpperCase(c) : c;
    }

    static char deLeet(char c) {
        int leetspeakIndex = leetspeakChars[0].indexOf(c);
        return leetspeakIndex == -1 ? c : leetspeakChars[1].charAt(leetspeakIndex);
    }

    static boolean isValid(char c) {
        return Character.isLetter(c) || c == '_';
    }
}
