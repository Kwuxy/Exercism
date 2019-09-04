import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class NaturalNumber {

    private Classification classification;

    NaturalNumber(int number) {
        if(number < 1) {
            throw new IllegalArgumentException("You must supply a natural number (positive integer)");
        }
        
        this.classification = getClassificationOfNumber(number);
    }

    private List<Integer> getFactors(int number) {
        return IntStream.rangeClosed(1, number / 2)
                .filter(n -> number % n == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    private Classification getClassificationOfNumber(int number) {
        int sumOfFactors = getSumOfFactors(number);

        if(sumOfFactors < number) return Classification.DEFICIENT;
        if(sumOfFactors == number) return Classification.PERFECT;
        return Classification.ABUNDANT;
    }

    private int getSumOfFactors(int number) {
         return getFactors(number).stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Classification getClassification() {
        return this.classification;
    }
}
