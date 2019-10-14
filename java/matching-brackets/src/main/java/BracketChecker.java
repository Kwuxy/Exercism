import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BracketChecker {
    private final String phrase;
    private final List<Character> brackets;
    private static final Map<Character, Character> bracketDictionary = Stream.of(new Character[][] {
            {'{', '}'},
            {'(', ')'},
            {'[', ']'}
    }).collect(Collectors.toMap(tuple -> tuple[0], tuple -> tuple[1]));

    public BracketChecker(String phrase) {
        this.phrase = cleanPhrase(phrase);
        this.brackets = new ArrayList<>();
    }

    public boolean areBracketsMatchedAndNestedCorrectly() {
        return areBracketsNestedCorrectly() && bracketsHaveAllBeenProcessedByPair();
    }

    private boolean bracketsHaveAllBeenProcessedByPair() {
        return brackets.size() == 0;
    }

    private boolean areBracketsNestedCorrectly() {
        return phrase.chars()
                .mapToObj(c -> (char) c)
                .allMatch(this::isValidBracket);
    }

    private boolean isValidBracket(Character character) {
        if(isOpeningBracket(character)) {
            return brackets.add(character);
        }

        return isValidClosingBracket(character);
    }

    private boolean isValidClosingBracket(Character character) {
        Character lastBracket = getAndRemoveLastBracket();
        return bracketDictionary.get(lastBracket) == character;
    }
    
    private Character getAndRemoveLastBracket() {
        if(brackets.size() == 0) return null;
        return brackets.remove(brackets.size() - 1);
    }

    private boolean isOpeningBracket(Character character) {
        return character == '{' || character == '[' || character == '(';
    }

    private String cleanPhrase(String phrase) {
        return phrase.replaceAll("[^{}()\\[\\]]", "");
    }
}