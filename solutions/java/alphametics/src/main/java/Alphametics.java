import java.util.*;
import java.util.stream.Collectors;

class Alphametics {
    private final String expression;

    public Alphametics(String expression) {
        this.expression = expression;
    }

    public Map<Character, Integer> solve() throws UnsolvablePuzzleException {
        final Calculator calculator = new Calculator();
        calculator.calculateRpnExpression(expression);

        Set<Character> characters = getDistinctCharacters(expression);
        Map<Character, Integer> valueMapping = initValueMapping(characters);

        while(!calculator.calculateBooleanWithValueMapping(valueMapping) || firstCharOfAnyWordIsZero(valueMapping)) {
            valueMapping = getNextValidValueMapping(valueMapping);
            if(valueMapping == null) break;
        }

        if(valueMapping == null) {
            throw new UnsolvablePuzzleException();
        }
        return valueMapping;
    }

    private Map<Character, Integer> getNextValidValueMapping(Map<Character, Integer> valueMapping) {
        do {
            valueMapping = calculateNextValueMapping(valueMapping);
        } while(!isValidValueMapping(valueMapping));

        return valueMapping;
    }

    private boolean isValidValueMapping(Map<Character, Integer> valueMapping) {
        if (valueMapping == null) return true;

        final List<Integer> integers = new ArrayList<>(valueMapping.values());
        int[] values = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            values[i] = integers.get(i);
        }
        return !containsDuplication(values);
    }

    private Map<Character, Integer> calculateNextValueMapping(Map<Character, Integer> valueMapping) {
        int updateIndex = valueMapping.entrySet().size() - 1;
        final List<Integer> values = new ArrayList<>(valueMapping.values());

        while(updateIndex != -1 && values.get(updateIndex) == 9) {
            valueMapping.put((Character) valueMapping.keySet().toArray()[updateIndex], 0);
            updateIndex--;
        }

        if(updateIndex == -1) return null;

        valueMapping.put((Character) valueMapping.keySet().toArray()[updateIndex], values.get(updateIndex) + 1);
        return valueMapping;
    }

    private Map<Character, Integer> initValueMapping(Set<Character> characters) {
        final HashMap<Character, Integer> map = new HashMap<>();
        int counter = 0;
        for (Character character : characters) {
            map.put(character, counter++);
        }
        return map;
    }

    private boolean firstCharOfAnyWordIsZero(Map<Character, Integer> valueMapping) {
        final String alphaExpression = expression.replaceAll("[^\\p{Alpha} ]", "");
        return Arrays.stream(alphaExpression.split(" "))
                .filter(word -> word.length() > 0)
                .map(word -> word.charAt(0))
                .anyMatch(firstLetter -> valueMapping.get(firstLetter) == 0);
    }

    private boolean containsDuplication(int[] values) {
        return Arrays.stream(values).distinct().count() != values.length;
    }

    private Set<Character> getDistinctCharacters(String s) {
        s = cleanFromNonAlpha(s);
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    private String cleanFromNonAlpha(String s) {
        return s.replaceAll("[^\\p{Alpha}]", "");
    }
}