import java.util.ArrayList;
import java.util.List;

public class Flattener {
    public List<Object> flatten(Object input) {
        final List<Object> result = new ArrayList<>();
        if(!(input instanceof List)) {
            return processSingleValue(result, input);
        }
        return processList(result, (List<?>) input);
    }

    private List<Object> processSingleValue(List<Object> concatenatedList, Object singleValue) {
        if(singleValue != null) {
            concatenatedList.add(singleValue);
        }
        return concatenatedList;
    }

    private List<Object> processList(List<Object> concatenatedList, List<?> list) {
        for(Object o : list) {
            concatenatedList.addAll(flatten(o));
        }
        return concatenatedList;
    }
}