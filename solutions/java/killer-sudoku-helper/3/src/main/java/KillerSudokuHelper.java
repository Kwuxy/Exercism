import java.util.stream.IntStream;
import java.util.List;
import java.util.stream.Stream;

public class KillerSudokuHelper {
    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize, List<Integer> exclude) {
        return combinationsInCage(cageSum, cageSize, exclude, 0);
    }

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize) {
        return combinationsInCage(cageSum, cageSize, List.of());
    }

    private List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize, List<Integer> exclude, Integer lastSelectedNumber) {
        if (cageSize == 1 && cageSum < 10 && !exclude.contains(cageSum) && lastSelectedNumber < cageSum) {
            return List.of(List.of(cageSum));
        }

        return IntStream.rangeClosed(lastSelectedNumber + 1, Math.min(9, cageSum))
                .filter(possibleValue -> !exclude.contains(possibleValue))
                .mapToObj(possibleValue ->
                        combinationsInCage(cageSum - possibleValue, cageSize - 1, exclude, possibleValue)
                                .stream()
                                .map(combination -> Stream.concat(Stream.of(possibleValue), combination.stream()).toList())
                                .toList()
                )
                .flatMap(List::stream)
                .toList();
    }
}