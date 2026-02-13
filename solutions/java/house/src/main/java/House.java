import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class House {

    private List<String> verseParts = Stream.of(new String[] {
            "the house that Jack built.",
            "the malt that lay in ",
            "the rat that ate ",
            "the cat that killed ",
            "the dog that worried ",
            "the cow with the crumpled horn that tossed ",
            "the maiden all forlorn that milked ",
            "the man all tattered and torn that kissed ",
            "the priest all shaven and shorn that married ",
            "the rooster that crowed in the morn that woke ",
            "the farmer sowing his corn that kept ",
            "the horse and the hound and the horn that belonged to ",
    }).collect(Collectors.toList());

    public String verse(int verse) {
        return "This is " + IntStream.range(0, verse)
                .map(i -> verse - i - 1)
                .mapToObj(verseNumber -> verseParts.get(verseNumber))
                .collect(Collectors.joining());
    }

    public String verses(int startVerse, int endVerse) {
        return IntStream.rangeClosed(startVerse, endVerse)
                .mapToObj(this::verse)
                .collect(Collectors.joining("\n"));
    }

    public String sing() {
        return verses(1, 12);
    }
}