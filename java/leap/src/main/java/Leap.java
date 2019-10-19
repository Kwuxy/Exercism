class Leap {

    boolean isLeapYear(int year) {
        return isDivisibleBy4(year) && (xnor(isDivisibleBy100(year), isDivisibleBy400(year)));
    }

    private boolean isDivisibleBy100(int year) {
        return year % 100 == 0;
    }

    private boolean isDivisibleBy4(int year) {
        return year % 4 == 0;
    }

    private boolean isDivisibleBy400(int year) {
        return year % 400 == 0;
    }

    private boolean xnor(boolean a, boolean b) {
        return a == b;
    }
}
