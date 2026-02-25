record LogInfos(String level, String message) { }

public class LogLevels {
    
    public static String message(String logLine) {
        return extractLogInfos(logLine).message();
    }

    public static String logLevel(String logLine) {
        return extractLogInfos(logLine).level();
    }

    public static String reformat(String logLine) {
        LogInfos logInfos = extractLogInfos(logLine);
        return logInfos.message() + " (" + logInfos.level() + ")";
    }

    private static LogInfos extractLogInfos(String logLine) {
        int columnIndex = logLine.indexOf(':');
        String level = logLine.substring(1, columnIndex - 1).trim().toLowerCase();
        String message = logLine.substring(columnIndex + 2).trim();
        return new LogInfos(level, message);
    }
}
