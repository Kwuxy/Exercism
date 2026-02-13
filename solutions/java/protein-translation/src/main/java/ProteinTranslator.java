import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ProteinTranslator {

    private final Map<String, String> proteinByCodon = Stream.of(new String[][]{
            {"AUG", "Methionine"},
            {"UAA", "STOP"}, {"UAC", "Tyrosine"}, {"UAG", "STOP"}, {"UAU", "Tyrosine"},
            {"UCA", "Serine"}, {"UCC", "Serine"}, {"UCG", "Serine"}, {"UCU", "Serine"},
            {"UGA", "STOP"}, {"UGC", "Cysteine"}, {"UGG", "Tryptophan"}, {"UGU", "Cysteine"},
            {"UUA", "Leucine"}, {"UUC", "Phenylalanine"}, {"UUG", "Leucine"}, {"UUU", "Phenylalanine"}
    }).collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

    List<String> translate(String rnaSequence) {
        final List<String> proteins = Stream.of(splitRnaSequence(rnaSequence))
                .map(proteinByCodon::get)
                .collect(Collectors.toList());

        return cutProteinList(proteins);
    }

    private List<String> cutProteinList(List<String> proteins) {
        if(proteins.contains("STOP")) {
            return proteins.subList(0, proteins.indexOf("STOP"));
        }

        return proteins;
    }


    private String[] splitRnaSequence(String rnaSequence) {
        return rnaSequence.split("(?<=\\G...)");
    }
}