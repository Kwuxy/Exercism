import java.util.stream.IntStream;

class PascalsTriangleGenerator {

    public int[][] generateTriangle(int lines) {
        return IntStream.rangeClosed(1, lines)
                .mapToObj(this::generateTriangleLine)
                .toArray(int[][]::new);
    }

    private int[] generateTriangleLine(int line) {
        int[] result = initLine(line);

        for(int i = 1; i < line; i++) {
            result[i] = result[i - 1] * (line - i) / i;
        }

        return result;
    }

    private int[] initLine(int size) {
        int[] line = new int[size];
        line[0] = 1;
        return line;
    }
}