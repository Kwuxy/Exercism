import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RomanNumeral {

    private final List<Map.Entry<String, Integer>> rosettaStone = Stream.of(new String[][]{
            {"I", "1"},
            {"IV", "4"},
            {"V", "5"},
            {"IX", "9"},
            {"X", "10"},
            {"XL", "40"},
            {"L", "50"},
            {"XC", "90"},
            {"C", "100"},
            {"CD", "400"},
            {"D", "500"},
            {"CM", "900"},
            {"M", "1000"},
    }).map(tuple -> new AbstractMap.SimpleEntry<>(tuple[0], Integer.valueOf(tuple[1])))
            .collect(Collectors.toList());

    private final int number;

    public RomanNumeral(int number) {
        this.number = number;
    }

    public String getRomanNumeral() {
        int actualNumber = number;
        final StringBuilder builder = new StringBuilder();

        while(actualNumber > 0) {
            final Map.Entry<String, Integer> currentSet = getBiggestSetPossible(actualNumber);
            actualNumber -= currentSet.getValue();
            builder.append(currentSet.getKey());
        }
        return builder.toString();
    }

    private Map.Entry<String, Integer> getBiggestSetPossible(int actualNumber) {
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : rosettaStone) {
            if(entry.getValue() > actualNumber) return maxEntry;
            maxEntry = entry;
        }
        return maxEntry;
    }
}