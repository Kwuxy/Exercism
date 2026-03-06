import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ParallelLetterFrequency {

    private final String[] texts;

    ParallelLetterFrequency(String[] texts) {
        this.texts = texts;
    }

    Map<Character, Integer> countLetters() {
        return Arrays.stream(texts).parallel()
                .flatMap(ParallelLetterFrequency::convertStringToCharacters)
                .filter(Character::isLetter)
                .collect(Collectors.groupingByConcurrent(
                        c -> c,
                        Collectors.summingInt(c -> 1)));
    }

    private static Stream<Character> convertStringToCharacters(String str) {
        return str.toLowerCase(Locale.ROOT)
                .codePoints()
                .mapToObj(c -> (char) c);
    }

}
