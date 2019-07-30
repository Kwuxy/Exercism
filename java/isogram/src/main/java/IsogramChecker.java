import java.util.*;

class IsogramChecker {

    boolean isIsogram(String phrase) {
        phrase = phrase.replace("-", "").replace(" ", "").toLowerCase();

        List<String> characters = Arrays.asList(phrase.split(""));
        Set<String> characterCounter = new HashSet<>(characters);
        for (String character: characterCounter) {
            if (Collections.frequency(characters, character) > 1) {
                return false;
            }
        }

        return true;
    }

}
