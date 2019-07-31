import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PangramChecker {

    public boolean isPangram(String input) {
        final List<String> alphabet = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));

        return input.toLowerCase()
                .chars()
                .mapToObj(c -> (char) c)
                .map(Object::toString)
                .collect(Collectors.toCollection(HashSet::new))
                .containsAll(alphabet);
    }

}
