import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class TwelveDays {
    private final Map<Integer, String[]> dayAndSpecificationByDay = Stream.of(new String[][]{
            {"1",  "first",    "a Partridge in a Pear Tree."},
            {"2",  "second",   "two Turtle Doves, and "},
            {"3",  "third",    "three French Hens, "},
            {"4",  "fourth",   "four Calling Birds, "},
            {"5",  "fifth",    "five Gold Rings, "},
            {"6",  "sixth",    "six Geese-a-Laying, "},
            {"7",  "seventh",  "seven Swans-a-Swimming, "},
            {"8",  "eighth",   "eight Maids-a-Milking, "},
            {"9",  "ninth",    "nine Ladies Dancing, "},
            {"10", "tenth",    "ten Lords-a-Leaping, "},
            {"11", "eleventh", "eleven Pipers Piping, "},
            {"12", "twelfth",   "twelve Drummers Drumming, "},
    }).collect(Collectors.toMap(entry -> Integer.valueOf(entry[0]), entry -> Arrays.copyOfRange(entry, 1, 3)));

    String verse(int verseNumber) {
        String endOfVerse = getVerseEnd(verseNumber);
        String dayNumber = getDayNumber(verseNumber);
        String BASE_VERSE = "On the %s day of Christmas my true love gave to me: %s\n";
        return String.format(BASE_VERSE, dayNumber, endOfVerse);
    }

    private String getVerseEnd(int verseNumber) {
        return Stream.of(dayAndSpecificationByDay.entrySet().toArray())
                .map(entry -> (Map.Entry) entry)
                .filter(entry -> (Integer) entry.getKey() <= verseNumber)
                .map(entry -> ((String[]) entry.getValue())[1])
                .reduce("", (first, second) -> second + first);
    }

    private String getDayNumber(int verseNumber) {
        return dayAndSpecificationByDay.get(verseNumber)[0];
    }

    String verses(int startVerse, int endVerse) {
        return IntStream.rangeClosed(startVerse, endVerse)
                .mapToObj(this::verse)
                .collect(Collectors.joining("\n"));
    }
    
    String sing() {
        return verses(1, 12);
    }
}
