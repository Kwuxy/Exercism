import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RailFenceCipher {
    private int railNumber;
    public RailFenceCipher(int railNumber) {
        this.railNumber = railNumber;
    }

    public String getEncryptedData(String toEncrypt) {
        final List<String> pattern = generatePattern(toEncrypt);
        return encrypt(pattern);
    }

    public String getDecryptedData(String toDecrypt) {
        String dummy = String.valueOf(generateDummy(toDecrypt.length(), '?'));
        final List<String> pattern = generatePattern(dummy);
        return decrypt(pattern, toDecrypt);
    }

    private String decrypt(List<String> pattern, String data) {
        replaceQuestionMarkByData(pattern, data);
        return collectDecrypt(pattern);
    }

    private String collectDecrypt(List<String> pattern) {
        if(pattern.size() == 0) return "";

        boolean desc = true;
        int line = 0;
        final int length = pattern.get(0).length();
        final StringBuilder builder = new StringBuilder();

        for (int col = 0; col < length; col++) {
            builder.append(pattern.get(line).charAt(col));
            line = desc ? line + 1: line - 1;
            if(line == railNumber - 1 || line == 0) desc = !desc;
        }
        return builder.toString();
    }

    private void replaceQuestionMarkByData(List<String> pattern, String data) {
        for (int i = 0; i < pattern.size(); i++) {
            String line = pattern.get(i);
            while(line.contains("?")) {
                line = line.replaceFirst("\\?", String.valueOf(data.charAt(0)));
                data = data.substring(1);
            }
            pattern.set(i, line);
        }
    }

    private String encrypt(List<String> pattern) {
        return String.join("", pattern).replace(".", "");
    }

    private List<String> generatePattern(String data) {
        List<char[]> rawPattern = generateEmptyPattern(data);
        return placeData(rawPattern, data);
    }

    private List<String> placeData(List<char[]> pattern, String data) {
        int line = 0;
        boolean desc = true;
        for (int col = 0; col < data.length(); col++) {
            pattern.get(line)[col] = data.charAt(col);
            line = desc ? line + 1 : line - 1;
            if(line == 0 || line == railNumber - 1) desc = !desc;
        }

        return pattern.stream().map(String::valueOf).collect(Collectors.toList());
    }

    private List<char[]> generateEmptyPattern(String data) {
        return IntStream.range(0, railNumber)
                .mapToObj(line -> this.generateDummy(data.length(), '.'))
                .collect(Collectors.toList());
    }

    private char[] generateDummy(int size, char c) {
        final char[] chars = new char[size];
        Arrays.fill(chars, c);
        return chars;
    }
}
