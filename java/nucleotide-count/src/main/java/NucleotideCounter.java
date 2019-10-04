import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NucleotideCounter {

    private final Map<Character, Integer> occurrence = Stream.of(new Object[]{'A', 'C', 'G', 'T'})
            .collect(Collectors.toMap(nucleotide -> (Character) nucleotide, nucleotide -> 0));

    public NucleotideCounter(String dna) {
        count(dna);
    }

    private void count(String dna) {
        dna.chars().forEach(nucleotide -> incrementNucleotide((char) nucleotide));
    }

    private void incrementNucleotide(Character nucleotide) {
        final Integer occ = occurrence.get(nucleotide);
        if(occ == null) {
            throw new IllegalArgumentException();
        }

        occurrence.replace(nucleotide, occ + 1);
    }

    public Map<Character, Integer> nucleotideCounts() {
        return occurrence;
    }
}