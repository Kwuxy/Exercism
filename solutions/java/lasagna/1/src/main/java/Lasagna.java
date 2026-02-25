public class Lasagna {
    public int expectedMinutesInOven() {
        return 40;
    }

    public int remainingMinutesInOven(int actualMinutes) {
        return expectedMinutesInOven() - actualMinutes;
    }

    public int preparationTimeInMinutes(int amountLayers) {
        return 2 * amountLayers;
    }

    public int totalTimeInMinutes(int amountLayers, int actualMinutes) {
        return preparationTimeInMinutes(amountLayers) + actualMinutes;
    }
}
