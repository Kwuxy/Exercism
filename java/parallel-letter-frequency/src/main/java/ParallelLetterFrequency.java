import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParallelLetterFrequency {
    private String text;

    public ParallelLetterFrequency(String input) {
        text = input.replaceAll("[^\\p{L}]", "").toLowerCase();
    }

    public Map<Integer, Integer> letterCounts() {
        final ConcurrentMap<Integer, Long> concurrentMap = text.chars()
                .parallel()
                .boxed()
                .collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));

        final HashMap<Integer, Integer> result = new HashMap<>();
        for (Map.Entry<Integer, Long> entry : concurrentMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().intValue());
        }

        return result;
    }
}
