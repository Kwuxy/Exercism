public class Item {
    private final int weight;
    private final int value;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }

    int getWeight() {
        return weight;
    }

    int getValue() {
        return value;
    }
}
