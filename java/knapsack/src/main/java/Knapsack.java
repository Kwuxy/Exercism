import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Knapsack {
    private List<Item> items;

    public int maximumValue(int maxWeight, List<Item> items) {
        this.items = items;

        return generateAllCombinations().stream()
                .map(this::concatItems)
                .filter(item -> item.getWeight() <= maxWeight)
                .map(Item::getValue)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    private Item concatItems(List<Item> items) {
        return items.stream().reduce(new Item(0, 0), this::sumItem);
    }

    private Item sumItem(Item a, Item b) {
        return new Item(a.getWeight() + b.getWeight(), a.getValue() + b.getValue());
    }

    private List<List<Item>> generateAllCombinations() {
        return IntStream.range(1, (int) Math.pow(2, items.size()))
                .mapToObj(Integer::toBinaryString)
                .map(this::completeBinaryString)
                .map(this::getCorrespondingItems)
                .collect(Collectors.toList());
    }

    private List<Item> getCorrespondingItems(String binaryString) {
        List<Item> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if(binaryString.charAt(i) == '1') {
                result.add(items.get(i));
            }
        }
        return result;
    }

    private String completeBinaryString(String binaryString) {
        return "0".repeat(items.size() - binaryString.length()) + binaryString;
    }
}