import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

class DiamondPrinter {

    List<String> printToList(char a) {
        int letters = a - 'A';
        List<String> lines = IntStream.rangeClosed(0, letters)
                .mapToObj(i -> getLineForLetter(i, letters))
                .toList();

        List<String> result = new ArrayList<>();
        result.addAll(lines);
        result.addAll(lines.subList(0, letters).reversed());

        return Collections.unmodifiableList(result);
    }

    private String getLineForLetter(int index, int lineSize) {
        StringBuilder builder = new StringBuilder();
        builder.repeat(" ", lineSize - index);

        if (index == 0) {
            builder.append("A");
        } else {
            char letter = (char) ('A' + index);
            builder.append(letter);
            builder.repeat(" ", index * 2 - 1);
            builder.append(letter);
        }

        builder.repeat(" ", lineSize - index);
        return builder.toString();
    }
}
