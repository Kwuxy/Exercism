import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PigLatinTranslator {
    private List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

    String translate(String phrase) {
        return Arrays.stream(phrase.split(" "))
                .map(this::translateWord)
                .collect(Collectors.joining(" "));
    }

    private String translateWord(String word) {
        if (Arrays.asList("yt", "xr").contains(word.substring(0, 2))) {
            return addSuffix(word);
        }

        int indexOfY = word.indexOf("y");
        if (indexOfY > 0) {
            if (indexOfY != word.length() - 1 || word.length() == 2) {
                return addSuffix(invertStringAtIndex(word, indexOfY));
            }
        }

        int indexOfQu = word.indexOf("qu");
        if (indexOfQu != -1) {
            if (!stringContainsVowel(word.substring(0, indexOfQu))) {
                return addSuffix(invertStringAtIndex(word, indexOfQu + 2));
            }
        }

        return addSuffix(invertStringAtIndex(word, getIndexOfFirstVowel(word)));
    }

    private Integer getIndexOfFirstVowel(String word) {
        int indexOfFirstVowel = word.length();
        for (Character vowel: vowels) {
            int index = word.indexOf(vowel);
            indexOfFirstVowel = index == -1 ? indexOfFirstVowel : (indexOfFirstVowel > index ? index : indexOfFirstVowel);
        }

        return indexOfFirstVowel;
    }

    private String invertStringAtIndex(String word, Integer index) {
        StringBuilder result = new StringBuilder();

        return result.append(word.substring(index)).append(word, 0, index).toString();
    }

    private String addSuffix(String word) {
        return word + "ay";
    }

    private Boolean stringContainsVowel(String word) {
        for (Character c: word.toCharArray()) {
            if (vowels.contains(c)) {
                return true;
            }
        }
        return false;
    }
}
