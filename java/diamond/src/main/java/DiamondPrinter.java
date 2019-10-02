import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DiamondPrinter {

    private char maxLetter;

    List<String> printToList(char a) {
        this.maxLetter = a;
        final List<Map.Entry> configOfDiamond = getConfigOfDiamond();
        return generateDiamond(configOfDiamond);
    }

    private List<String> generateDiamond(List<Map.Entry> configOfDiamond) {
        return configOfDiamond.stream()
                .map(this::generateDiamondLine)
                .collect(Collectors.toList());
    }

    private String generateDiamondLine(Map.Entry entry) {
        final StringBuilder line = new StringBuilder();
        generateExternalSpacesOfLine(line, (maxLetter - 'A') - (Integer) entry.getKey());
        line.append(entry.getValue());

        if((Integer) entry.getKey() != 0) {
            generateInternalSpacesOfLine(line, (Integer) entry.getKey() * 2 - 1);
            line.append(entry.getValue());
        }

        generateExternalSpacesOfLine(line, (maxLetter - 'A') - (Integer) entry.getKey());
        return line.toString();
    }

    private void generateInternalSpacesOfLine(StringBuilder line, Integer numberOfSpaces) {
        line.append(" ".repeat(numberOfSpaces));
    }

    private void generateExternalSpacesOfLine(StringBuilder line, Integer numberOfSpaces) {
        line.append(" ".repeat(Math.max(0, numberOfSpaces)));
    }

    private List<Map.Entry> getConfigOfDiamond() {
        final ArrayList<Map.Entry> configOfDiamond = new ArrayList<>(getConfigOfTopDiamond());
        configOfDiamond.add(createPair(maxLetter - 'A'));
        configOfDiamond.addAll(getConfigOfBottomDiamond());
        return configOfDiamond;
    }

    private List<Map.Entry> getConfigOfTopDiamond() {
        return IntStream.range(0, maxLetter - 'A')
                .mapToObj(this::createPair)
                .collect(Collectors.toList());
    }

    private List<Map.Entry> getConfigOfBottomDiamond() {
        final List<Map.Entry> configOfBottomDiamond = getConfigOfTopDiamond();
        Collections.reverse(configOfBottomDiamond);
        return configOfBottomDiamond;
    }

    private Map.Entry<Integer, Character> createPair(int index) {
        return new AbstractMap.SimpleEntry<>(index, (char) ('A' + index));
    }

}
