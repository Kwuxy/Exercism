import java.util.*;
import java.util.stream.Collectors;

class School {
    private final Map<Integer, List<String>> studentsByGrade;

    School() {
        this.studentsByGrade = new HashMap<>();
    }

    public List<String> roster() {
        return studentsByGrade.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public void add(String student, int grade) {
        initStudentListIfNotExists(grade);
        studentsByGrade.get(grade).add(student);
        studentsByGrade.get(grade).sort(Comparator.naturalOrder());
    }

    private void initStudentListIfNotExists(int grade) {
        studentsByGrade.computeIfAbsent(grade, k -> new ArrayList<>());
    }

    public List<String> grade(int grade) {
        final List<String> students = studentsByGrade.get(grade);
        return students == null ? new ArrayList<>() : students;
    }
}