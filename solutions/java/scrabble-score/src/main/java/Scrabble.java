import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class KeyValue {
    String key;
    String value;

    KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return "[ " + key + "; " + value + "]";
    }
}

class Scrabble {
    private final int score;
    private static final Map<Character, Integer> lettersValue = Stream.of(new String[][]{
            {"1", "A", "E", "I", "O", "U", "L", "N", "R", "S", "T"},
            {"2", "D", "G"},
            {"3", "B", "C", "M", "P"},
            {"4", "F", "H", "V", "W", "Y"},
            {"5", "K"},
            {"8", "J", "X"},
            {"10", "Q", "Z"}
    })
            .map(Scrabble::convertStringArraysToKeyValueArray)
            .flatMap(Arrays::stream)
            .collect(Collectors.toMap(keyValue -> keyValue.key.charAt(0),
                    keyValue -> Integer.valueOf(keyValue.value)));

    Scrabble(String word) {
        score = word.toUpperCase()
                .chars()
                .map(character -> lettersValue.get((char) character)).sum();
    }

    private static KeyValue[] convertStringArraysToKeyValueArray(String[] entry) {
        ArrayList<KeyValue> pairs = new ArrayList<>();
        for (String character : Arrays.copyOfRange(entry, 1, entry.length)) {
            pairs.add(new KeyValue(character, entry[0]));
        }

        return pairs.toArray(new KeyValue[0]);
    }

    int getScore() {
        return score;
    }
}