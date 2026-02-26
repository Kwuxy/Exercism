public class JedliksToyCar {
    int distanceDriven = 0;
    int batteryLevel = 100;

    public static JedliksToyCar buy() {
        return new JedliksToyCar();
    }

    public String distanceDisplay() {
        return "Driven " + distanceDriven + " meters";
    }

    public String batteryDisplay() {
        if (batteryLevel <= 0)
            return "Battery empty";

        return "Battery at " + batteryLevel + "%";
    }

    public void drive() {
        if (batteryLevel <= 0) return;

        distanceDriven += 20;
        batteryLevel -= 1;
    }
}
