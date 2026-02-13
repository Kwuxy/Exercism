import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Transpose {

    private List<List<String>> transposedMatrix;

    public String transpose(final String input) {
        if(input.length() == 0) return "";

        final String[] lines = input.split("\n");
        final int matrixSize = matrixSize(lines);
        initMatrix(matrixSize);
        transposeLines(lines);
        return convertMatrixToString();
    }

    private int matrixSize(final String[] lines) {
        if(lines.length == 0) return 0;

        return Arrays.stream(lines)
                .map(String::length)
                .max(Integer::compare)
                .get();
    }

    private void initMatrix(final int matrixSize) {
        transposedMatrix = new ArrayList<>();
        for (int i = 0; i < matrixSize; i++) {
            transposedMatrix.add(new ArrayList<>());
        }
    }

    private void transposeLines(final String[] lines) {
        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            transposeSingleLine(lines[lineNumber], lineNumber);
        }
    }

    private void transposeSingleLine(String line, int lineNumber) {
        final String[] characters = line.split("");
        for (int colNumber = 0; colNumber < characters.length; colNumber++) {
            int lineCurrentSize = transposedMatrix.get(colNumber).size();
            if(lineCurrentSize < lineNumber) {
                addComplementarySpaces(colNumber, lineNumber - lineCurrentSize);
            }

            transposedMatrix.get(colNumber).add(characters[colNumber]);
        }
    }

    private void addComplementarySpaces(int colNumber, int spacesToAdd) {
        for(int i = 0; i < spacesToAdd; i++) {
            transposedMatrix.get(colNumber).add(" ");
        }
    }

    private String convertMatrixToString() {
        return transposedMatrix.stream()
                .map(line -> String.join("", line))
                .collect(Collectors.joining("\n"));
    }
}