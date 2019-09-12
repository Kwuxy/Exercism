class CollatzCalculator {

    int computeStepCount(int start) {
        if(!isValid(start)) throw new IllegalArgumentException("Only natural numbers are allowed");
        if(start == 1) return 0;

        int modifiedValue = computeStep(start);
        return computeStepCount(modifiedValue) + 1;
    }

    private int computeStep(int value) {
        if(value % 2 == 0) {
            return EvenCase(value);
        } else {
            return OddCase(value);
        }
    }

    private int EvenCase(int value) {
        return value / 2;
    }

    private int OddCase(int value) {
        return value * 3 + 1;
    }

    private boolean isValid(int value) {
        return value > 0;
    }
}
