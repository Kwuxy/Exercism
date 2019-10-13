import java.util.Arrays;
import java.util.List;

class ResistorColorDuo {
    private static final List colors = Arrays.asList("black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white");

    private int getValueOf(String color) {
        return colors.indexOf(color);
    }

    int value(String[] colors) {
        return Arrays.stream(cutArrayToUsefulData(colors))
                .map(this::getValueOf)
                .reduce(0, this::concatNumbers);
    }

    private String[] cutArrayToUsefulData(String[] colors) {
        return Arrays.copyOfRange(colors, 0, 2);
    }

    private int concatNumbers(int a, int b) {
        return a * 10 + b;
    }
}
