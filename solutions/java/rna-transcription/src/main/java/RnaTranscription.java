import java.util.stream.Collectors;
import java.util.stream.Stream;

class RnaTranscription {

    String transcribe(String dnaStrand) {
        return Stream.of(dnaStrand.split(""))
                .map(this::transcribeSingle)
                .collect(Collectors.joining());
    }

    String transcribeSingle(String dnaStrand) {
        if (dnaStrand.equals("C")) return "G";
        if (dnaStrand.equals("G")) return "C";
        if (dnaStrand.equals("T")) return "A";
        if (dnaStrand.equals("A")) return "U";

        return "";
    }

}
