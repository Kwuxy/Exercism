import java.util.stream.IntStream;

class PrimeCalculator {

    int nth(int nth) {
        if(nth == 0) throw new IllegalArgumentException();

        int primeCounter = 0;
        int number = 2;

        while (primeCounter < nth) {
            if (isPrime(number++)) {
                primeCounter++;
            }
        }

        return number - 1;
    }

    private boolean isPrime(int number) {
        int maxFactor = (int) Math.floor(Math.sqrt(number));
        return IntStream.rangeClosed(2, maxFactor)
                .noneMatch(i -> number % i == 0);
    }

}
