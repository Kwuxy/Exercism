import java.util.regex.Pattern;

class Line {
    private String value;
    private int number;
    private String filename;

    Line(String value, int number, String filename) {
        this.value = value;
        this.number = number;
        this.filename = filename;
    }

    public String toString() {
        return value;
    }

    boolean matches(String regex, boolean caseSensitive) {
        int flags = caseSensitive ? 0 : Pattern.CASE_INSENSITIVE;
        final Pattern pattern = Pattern.compile(regex, flags);
        return pattern.matcher(value).matches();
    }

    String getValue() {
        return value;
    }

    int getNumber() {
        return number;
    }

    String getFilename() {
        return filename;
    }
}