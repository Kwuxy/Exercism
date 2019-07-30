import java.util.List;
import java.util.stream.Collectors;

public class Anagram {
    private final String reference;

    public Anagram(String reference) {
        this.reference = reference;
    }

    public List<String> match(List<String> words) {
        return words.stream()
                .filter(word -> !word.toLowerCase().equals(this.reference.toLowerCase()))
                .filter(word -> convertWord(word).equals(convertWord(this.reference)))
                .collect(Collectors.toList());
    }

    private String convertWord(String word) {
        return word.toLowerCase()
                .chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
