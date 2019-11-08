import java.util.List;
import java.util.stream.Collectors;

class BinarySearch {
    private final List<Integer> list;

    public BinarySearch(List<Integer> list) {
        this.list = list;
    }

    public int indexOf(int valueToSearch) throws ValueNotFoundException {
        return dichotomicSearch(list, valueToSearch);
    }

    private int dichotomicSearch(List<Integer> list, int valueToSearch) throws ValueNotFoundException {
        if(list.size() == 0) {
            throw new ValueNotFoundException("Value not in array");
        }

        int middleIndex = list.size() / 2;
        int middleValue = list.get(middleIndex);

        if(middleValue == valueToSearch) {
            return middleIndex;
        }

        int beginIndex;
        if(middleValue < valueToSearch) {
            beginIndex = middleIndex + 1;
            list = list.subList(beginIndex, list.size());
        } else {
            beginIndex = 0;
            list = list.subList(beginIndex, middleIndex);
        }

        return dichotomicSearch(list, valueToSearch) + beginIndex;
    }
}