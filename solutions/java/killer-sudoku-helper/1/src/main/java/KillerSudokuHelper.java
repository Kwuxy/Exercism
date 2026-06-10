import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class KillerSudokuHelper {

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize, List<Integer> exclude) {
        if (cageSize == 0) return List.of(List.of());

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> possibleValues = IntStream.rangeClosed(1, Math.min(9, cageSum))
                .boxed()
                .filter(i -> !exclude.contains(i))
                .toList();

        for (Integer possibleValue : possibleValues) {
            List<Integer> next = new ArrayList<>(exclude);
            next.add(possibleValue);
            List<List<Integer>> subResults = combinationsInCage(cageSum - possibleValue, cageSize - 1, next);

            for (List<Integer> subResult : subResults) {
                Integer sum = subResult.stream().reduce(0, Integer::sum) + possibleValue;
                if (!sum.equals(cageSum))
                    continue;

                List<Integer> combination = new ArrayList<>(subResult);
                combination.add(possibleValue);
                Collections.sort(combination);

                if (result.contains(combination))
                    continue;

                result.add(combination);
            }
        }

        return result;
    }

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize) {
        return combinationsInCage(cageSum, cageSize, List.of());
    }

}
