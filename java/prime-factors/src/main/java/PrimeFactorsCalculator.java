import java.util.ArrayList;
import java.util.List;

class PrimeFactorsCalculator {

    private long number;
    private List<Long> factors;

    private void initCalculation(long number) {
        this.factors = new ArrayList<>();
        this.number = number;
    }

    public List<Long> calculatePrimeFactorsOf(long number) {
        initCalculation(number);

        while(numberHasFactors()) {
            calculateOneFactor();
        }

        return getFactors();
    }

    private boolean numberHasFactors() {
        return number != 1;
    }

    private void calculateOneFactor() {
        long factor = getOneFactor();
        factors.add(factor);
        this.number /= factor;
    }

    private long getOneFactor() {
        long factor = 2;
        while(number % factor != 0) {
            factor++;
        }

        return factor;
    }

    private List<Long> getFactors() {
        return factors;
    }
}