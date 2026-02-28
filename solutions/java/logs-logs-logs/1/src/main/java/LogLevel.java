public enum LogLevel {
    UNKNOWN(0),
    TRACE(1),
    DEBUG(2),
    INFO(4),
    WARNING(5),
    ERROR(6),
    FATAL(42);

    public final int level;
    LogLevel(int level) {
        this.level = level;
    }

    static LogLevel getLogLevel(String symbol) {
        return switch (symbol) {
            case "TRC" -> TRACE;
            case "DBG" -> DEBUG;
            case "INF" -> INFO;
            case "WRN" -> WARNING;
            case "ERR" -> ERROR;
            case "FTL" -> FATAL;
            default -> UNKNOWN;
        };
    }
}
