import java.util.stream.Collector;

class Atbash {
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static String backwardAlphabet = new StringBuilder(alphabet).reverse().toString();

    public String encode(String clearPhrase) {
        clearPhrase = cleanPhrase(clearPhrase);
        final String encodedPhrase = encodePhrase(clearPhrase);
        return splitEncodedWords(encodedPhrase);
    }

    private String encodePhrase(String clearPhrase) {
        return clearPhrase.chars()
                .mapToObj(c -> (char) c)
                .map(this::encodeSingleChar)
                .collect(Collector.of(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

    private char encodeSingleChar(char c) {
        if(Character.isDigit(c)) return c;

        final int index = alphabet.indexOf(c);
        return backwardAlphabet.charAt(index);
    }

    private String splitEncodedWords(String encodedPhrase) {
        final String[] parts = encodedPhrase.split("(?<=\\G.{5})");
        return String.join(" ", parts);
    }

    public String decode(String encodedPhrase) {
        encodedPhrase = cleanPhrase(encodedPhrase);
        return decodePhrase(encodedPhrase);
    }

    private String decodePhrase(String encodedPhrase) {
        return encodedPhrase.chars()
                .mapToObj(c -> (char) c)
                .map(this::decodeSingleChar)
                .collect(Collector.of(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

    private char decodeSingleChar(char character) {
        if(Character.isDigit(character)) return character;

        final int index = backwardAlphabet.indexOf(character);
        return alphabet.charAt(index);
    }

    private String cleanPhrase(String clearPhrase) {
        clearPhrase = clearPhrase.toLowerCase();
        clearPhrase = clearPhrase.replaceAll("[\\s,.]", "");
        return clearPhrase;
    }
}