import java.util.List;
import java.util.stream.IntStream;

record Verses(String animal, String verse, String description) { }

class FoodChain {

    String INITIAL_VERSE = "I know an old lady who swallowed a ";

    List<Verses> VERSES_BY_NAME = List.of(
        new Verses("fly", "", "I don't know why she swallowed the fly. Perhaps she'll die."),
        new Verses("spider", "It wriggled and jiggled and tickled inside her.\n", "She swallowed the spider to catch the fly.\n"),
        new Verses("bird", "How absurd to swallow a bird!\n", "She swallowed the bird to catch the spider that wriggled and jiggled and tickled inside her.\n"),
        new Verses("cat", "Imagine that, to swallow a cat!\n", "She swallowed the cat to catch the bird.\n"),
        new Verses("dog", "What a hog, to swallow a dog!\n", "She swallowed the dog to catch the cat.\n"),
        new Verses("goat", "Just opened her throat and swallowed a goat!\n", "She swallowed the goat to catch the dog.\n"),
        new Verses("cow", "I don't know how she swallowed a cow!\n", "She swallowed the cow to catch the goat.\n"),
        new Verses("horse", "She's dead, of course!", "")
    );

    String verse(int verse) {
        StringBuilder sb = new StringBuilder();

        Verses firstVerse = VERSES_BY_NAME.get(verse - 1);
        sb.append(INITIAL_VERSE)
                .append(firstVerse.animal())
                .append(".\n")
                .append(firstVerse.verse());

        if (verse == 8)
            return sb.toString();

        VERSES_BY_NAME.subList(0, verse)
                .reversed()
                .forEach(v -> sb.append(v.description()));

        return sb.toString();
    }

    String verses(int startVerse, int endVerse) {
        var song = IntStream.rangeClosed(startVerse, endVerse)
                .mapToObj(this::verse)
                .toList();

        return String.join("\n\n", song);
    }

}