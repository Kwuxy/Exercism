import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class School {

    Map<Integer, List<String>> students = new HashMap<>();

    boolean add(String student, int grade) {
        if (!students.containsKey(grade))
            students.put(grade, new LinkedList<>());

        boolean studentAlreadyInRoster = students.values().stream().flatMap(List::stream).anyMatch(s -> s.equals(student));
        if (studentAlreadyInRoster)
            return false;

        students.get(grade).add(student);
        return true;
    }

    List<String> roster() {
        return students.keySet().stream()
                .sorted()
                .map(this::grade)
                .flatMap(List::stream)
                .toList();
    }

    List<String> grade(int grade) {
        if (!students.containsKey(grade))
            return List.of();

        return students.get(grade).stream()
                .sorted()
                .toList();
    }

}
