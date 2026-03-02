class CollatzCalculator {

    int computeStepCount(int start) throws IllegalArgumentException {
        if (start <= 0)
            throw new IllegalArgumentException("Only positive integers are allowed");

        if (start == 1)
            return 0;

        int next = start % 2 == 0 ? start / 2 : 3 * start + 1;
        return 1 + computeStepCount(next);
    }

}
