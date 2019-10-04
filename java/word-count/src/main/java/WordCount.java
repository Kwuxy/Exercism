import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class WordCount {
    private final Map<String, Integer> counter;

    WordCount() {
        counter = new HashMap<>();
    }

    public Map<String, Integer> phrase(String phrase) {
        phrase = addSpacesAtBeginningAndEnding(phrase);
        phrase = cleanPhrase(phrase);
        Arrays.stream(phrase.split("\\s"))
                .forEach(this::incrementWord);

        removeEmptyOccurrence();
        return counter;
    }

    private String addSpacesAtBeginningAndEnding(String phrase) {
        return new StringBuilder(phrase)
                .insert(0, ' ')
                .append(' ')
                .toString();
    }

    private String cleanPhrase(String phrase) {
        phrase = phrase.toLowerCase();
        phrase = removeSimpleQuote(phrase);
        phrase = phrase.replaceAll("[^\\w']", " ");
        return phrase;
    }

    private String removeSimpleQuote(String phrase) {
        final char[] letters = phrase.toCharArray();
        
        for (int index = 1; index < letters.length - 1; index++) {
            if (letters[index] == '\'') {
                if(letters[index - 1] == ' ' || letters[index + 1] == ' ') {
                    letters[index] = ' ';
                }
            }
        }
        return String.valueOf(letters);
    }

    private void incrementWord(String word) {
        final Integer wordOccurrence = counter.get(word);

        if(wordOccurrence == null) {
            counter.put(word, 1);
        }else{
            counter.replace(word, counter.get(word) + 1);
        }
    }

    private void removeEmptyOccurrence() {
        counter.remove("");
    }
}