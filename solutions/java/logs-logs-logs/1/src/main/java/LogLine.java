public class LogLine {

    private final LogLevel logLevel;
    private final String message;

    public LogLine(String logLine) {
        String levelSymbol = logLine.substring(1, 4);
        logLevel = LogLevel.getLogLevel(levelSymbol);
        message = logLine.substring(7);
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getOutputForShortLog() {
        return logLevel.level + ":" + message;
    }
}
