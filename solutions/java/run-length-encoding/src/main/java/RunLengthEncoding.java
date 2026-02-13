import java.util.Arrays;
import java.util.stream.Collectors;

class RunLengthEncoding {
    public String encode(String text) {
        return Arrays.stream(text.split("(?<=([\\w\\s]))(?!\\1)"))
                .map(this::buildEncodedPart)
                .collect(Collectors.joining());
    }

    private String buildEncodedPart(String s) {
        if(s.length() < 2) return s;
        return String.valueOf(s.length()) + s.charAt(0);
    }

    public String decode(String encoded) {
        return Arrays.stream(encoded.split("(?<=([a-zA-Z\\s]))"))
                .map(this::buildDecodedPart)
                .collect(Collectors.joining());
    }

    private String buildDecodedPart(String s) {
        if(s.length() < 2) return s;

        final String[] splitString = s.split("[a-zA-Z\\s]");
        final int occurrence = splitString[0].length() == 0 ? 1 : Integer.valueOf(splitString[0]);
        final char character = s.charAt(s.length() - 1);

        return String.valueOf(character).repeat(occurrence);
    }
}