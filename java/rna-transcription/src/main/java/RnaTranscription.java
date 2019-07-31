class RnaTranscription {

    String transcribe(String dnaStrand) {
        StringBuilder builder = new StringBuilder();

        for (String part: dnaStrand.split("")) {
            builder.append(transcribeSingle(part));
        }

        return builder.toString();
    }

    String transcribeSingle(String dnaStrand) {
        if (dnaStrand.equals("C")) return "G";
        if (dnaStrand.equals("G")) return "C";
        if (dnaStrand.equals("T")) return "A";
        if (dnaStrand.equals("A")) return "U";

        return "";
    }

}
