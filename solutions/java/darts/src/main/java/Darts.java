import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Darts {
    static class DistanceValuePair {
        int distance;
        int score;

        DistanceValuePair(int distance, int value) {
            this.distance = distance;
            this.score = value;
        }
    }

    private static final List<DistanceValuePair> scoreByDistance = Stream.of(new int[][]{
            {10, 0}, {5, 1}, {1, 5}, {0, 10}
    }).map(pair -> new DistanceValuePair(pair[0], pair[1]))
            .collect(Collectors.toList());
    private final int score;

    Darts(double x, double y) {
        double distance = getDartDistanceToCenter(x, y);

        for (DistanceValuePair pair : scoreByDistance) {
            if (distance > pair.distance) {
                this.score = pair.score;
                return;
            }
        }

        this.score = -1;
    }

    int score() {
        return this.score;
    }

    private double getDartDistanceToCenter(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
