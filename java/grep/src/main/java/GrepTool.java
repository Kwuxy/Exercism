import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class GrepTool {
    private List<Line> lines;
    private List<String> flags;
    private List<String> files;

    public String grep(String searched, List<String> flags, List<String> files) {
        this.flags = flags;
        this.files = files;

        lines = readAllLines();
        search(searched);
        return formatDataForReturn();
    }

    private String formatDataForReturn() {
        if(flags.contains("-l")) return getFileData();
        return getLineData();
    }

    private String getLineData() {
        return lines.stream()
                .map(this::calculateLineValue)
                .collect(Collectors.joining("\n"));
    }

    private String getFileData() {
        return lines.stream()
                .map(Line::getFilename)
                .distinct()
                .collect(Collectors.joining("\n"));
    }

    private String calculateLineValue(Line line) {
        final StringBuilder result = new StringBuilder();

        if(files.size() > 1) {
            result.append(line.getFilename())
                    .append(":");
        }

        if(flags.contains("-n")) {
            result.append(line.getNumber())
                    .append(":");
        }

        return result.append(line.getValue()).toString();
    }

    private void search(String searchedText) {
        boolean caseSensitive = !flags.contains("-i");
        String regex = buildRegex(searchedText);
        lines.removeIf(line -> !line.matches(regex, caseSensitive));
    }

    private String buildRegex(String searchedText) {
        final StringBuilder regexBuilder = new StringBuilder(searchedText);

        if(flags.contains("-v")) {
            return regexBuilder.insert(0, "((?!")
                    .append(").)*").toString();
        }

        if(!flags.contains("-x") && !flags.contains("-v")) {
            regexBuilder.insert(0, ".*")
                    .append(".*");
        }

        return regexBuilder.toString();
    }

    private List<Line> readAllLines() {
        List<Line> result = new ArrayList<>();

        for (String file : files) {
            final List<Line> lines = readLinesOfFile(file);
            result.addAll(lines);
        }

        return result;
    }

    private List<Line> readLinesOfFile(String filename) {
        final List<String> fileLines = readLines(filename);
        List<Line> result = new ArrayList<>();

        for (int i = 0; i < fileLines.size(); i++) {
            result.add(new Line(fileLines.get(i), i + 1, filename));
        }

        return result;
    }

    private List<String> readLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File is unreachable : " + filename);
        }
    }
}