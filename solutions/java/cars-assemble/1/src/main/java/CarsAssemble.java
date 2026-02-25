public class CarsAssemble {

    private final int BASE_PRODUCTION = 221;

    public double productionRatePerHour(int speed) {
        double ratio;
        if (speed <= 4) ratio = 1.0;
        else if (speed <= 8) ratio = 0.9;
        else if (speed == 9) ratio = 0.8;
        else ratio = 0.77;

        return BASE_PRODUCTION * speed * ratio;
    }

    public int workingItemsPerMinute(int speed) {
        return (int) (productionRatePerHour(speed) / 60);
    }
}
