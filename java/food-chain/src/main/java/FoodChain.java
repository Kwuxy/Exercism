import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class FoodChain {
    private List<String[]> parts = Stream.of(new String[][] {
            {"fly.", "", ""},
            {"", "spider.\nIt ", "wriggled and jiggled and tickled inside her.\nShe swallowed the spider to catch the "},
            {"bird.\n", "How absurd to swallow a bird!\n", "She swallowed the bird to catch the spider that "},
            {"cat.\n", "Imagine that, to swallow a cat!\n", "She swallowed the cat to catch the "},
            {"dog.\n", "What a hog, to swallow a dog!\n", "She swallowed the dog to catch the "},
            {"goat.\n", "Just opened her throat and swallowed a goat!\n", "She swallowed the goat to catch the "},
            {"cow.\nI don't know how she swallowed a cow!\nShe swallowed the cow to catch the ", "", ""},
    }).collect(Collectors.toList());

    private int verse;

    public String verse(int verse) {
        this.verse = verse;
        String beginPhrase = "I know an old lady who swallowed a ";
        String endPhrase = "\nI don't know why she swallowed the fly. Perhaps she'll die.";
        String horsePhrase = "horse.\nShe's dead, of course!";
        if(verse == 8) return beginPhrase + horsePhrase;

        return beginPhrase + generateVerseBody() + endPhrase;
    }

    private String generateVerseBody() {
        return IntStream.range(0, verse)
                .map(verseNumber -> verse - verseNumber - 1)
                .mapToObj(this::generateVersePart)
                .collect(Collectors.joining(""));
    }

    private String generateVersePart(int verseNumber) {
        String result = parts.get(verseNumber)[0];
        if(verse - 1 == verseNumber) {
            result += parts.get(verseNumber)[1];
        }
        return result + parts.get(verseNumber)[2];
    }

    public String verses(int startVerse, int endVerse) {
        return IntStream.rangeClosed(startVerse, endVerse)
                .mapToObj(this::verse)
                .collect(Collectors.joining("\n\n"));
    }
}