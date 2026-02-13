import java.util.Arrays;
import java.util.List;

class ResistorColor {
    private final List colors = Arrays.asList("black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white");

    int colorCode(String color) {
        return this.colors.indexOf(color);
    }

    String[] colors() {
        return (String[]) this.colors.toArray();
    }
}
