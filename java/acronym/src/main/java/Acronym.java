import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Acronym {
    private final String acronym;

    Acronym(String phrase) {
        phrase = phrase.toUpperCase()
                .replace('-', ' ')
                .replace('_', ' ');

        this.acronym = Stream.of(phrase.split(" "))
                .filter(word -> word.length() > 0)
                .map(word -> word.charAt(0))
                .map(Object::toString)
                .collect(Collectors.joining());

    }

    String get() {
        return this.acronym;
    }

}
