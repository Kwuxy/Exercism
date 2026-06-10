import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KillerSudokuHelper {

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize, List<Integer> exclude) {
        if (cageSize == 0) return List.of(List.of());

        return IntStream.rangeClosed(1, Math.min(9, cageSum))
                .boxed()
                .filter(i -> !exclude.contains(i))
                .flatMap(possibleValue ->
                        combinationsInCage(cageSum - possibleValue, cageSize - 1, this.addValueToList(exclude, possibleValue))
                                .stream()
                                .map(combination -> this.addValueToList(combination, possibleValue)))
                .filter(list -> this.sum(list).equals(cageSum))
                .distinct()
                .toList();
    }

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize) {
        return combinationsInCage(cageSum, cageSize, List.of());
    }

    private Integer sum(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }

    private List<Integer> addValueToList(List<Integer> list, Integer value) {
        return Stream.concat(list.stream(), Stream.of(value))
                .sorted()
                .toList();
    }

}
