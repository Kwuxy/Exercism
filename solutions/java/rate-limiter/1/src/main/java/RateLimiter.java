import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

class ClientWindow {
    public int connexions;
    public Instant endTime;

    ClientWindow(Instant endTime) {
        this.reset(endTime);
    }

    void reset(Instant endTime) {
        this.connexions = 0;
        this.endTime = endTime;
    }

    void connect() {
        connexions++;
    }
}

public class RateLimiter<K> {
    private final int limit;
    private final Duration windowSize;
    private final TimeSource timeSource;

    private final Map<K, ClientWindow> clientWindows = new HashMap<>();

    public RateLimiter(int limit, Duration windowSize, TimeSource timeSource) {
        this.limit = limit;
        this.windowSize = windowSize;
        this.timeSource = timeSource;
    }

    public boolean allow(K clientId) {
        ClientWindow clientWindow = getClientWindow(clientId);

        if (!isWithinWindow(clientWindow)) {
            clientWindow.reset(computeWindowEndTime());
        }

        if (clientWindow.connexions >= limit)
            return false;

        clientWindow.connect();
        return true;
    }

    private boolean isWithinWindow(ClientWindow clientWindow) {
        return timeSource.now().isBefore(clientWindow.endTime);
    }

    private ClientWindow getClientWindow(K clientId) {
        return clientWindows.computeIfAbsent(clientId, _ -> new ClientWindow(computeWindowEndTime()));
    }

    private Instant computeWindowEndTime() {
        return timeSource.now().plus(windowSize);
    }
}
