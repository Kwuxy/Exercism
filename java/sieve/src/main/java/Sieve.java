import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Sieve {
    private final int limit;
    private List<Integer> primes = new ArrayList<>();
    private Map<Integer, Boolean> primesChecker;

    Sieve(int maxPrime) {
        limit = maxPrime;
        calculatePrimes();
    }

    private void calculatePrimes() {
        primesChecker = initPrimesChecker();
        executeSieveOfErathostenes();
    }

    private void executeSieveOfErathostenes() {
        for (Map.Entry<Integer, Boolean> integerBooleanEntry : primesChecker.entrySet()) {
            processSingleNumber(integerBooleanEntry);
        }
    }

    private void processSingleNumber(Map.Entry<Integer, Boolean> pair) {
        if(pair.getValue()) {
            int primeNumber = pair.getKey();
            primes.add(primeNumber);
            unmarkFactorsOfNumber(primeNumber);
        }
    }

    private void unmarkFactorsOfNumber(int primeNumber) {
        for(int i = 0; primeNumber * i <= limit; i++) {
            primesChecker.replace(primeNumber * i, false);
        }
    }

    private Map<Integer, Boolean> initPrimesChecker() {
        return IntStream.rangeClosed(2, limit)
                .boxed()
                .collect(Collectors.toMap(number -> number, number -> true));
    }

    List<Integer> getPrimes() {
        return primes;
    }
}
