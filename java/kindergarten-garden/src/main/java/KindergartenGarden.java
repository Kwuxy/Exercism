import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class KindergartenGarden {

    private final List<List<Plant>> plants;

    KindergartenGarden(String garden) {
        final List<List<String>> lines = cutLines(garden);
        final List<List<String>> linesByStudent = regroupByStudent(lines);
        plants = parsePlants(linesByStudent);
    }

    private List<List<String>> cutLines(String garden) {
        return Stream.of(garden.split("\n"))
                .map(line -> line.split("(?<=\\G..)"))
                .map(Arrays::asList)
                .collect(Collectors.toList());
    }

    /*
     * [[2, 3], [5, 6]]         -> [[2, 5], [3, 6]]
     * [[1, 2, 3], [4, 5, 6]]   -> [[1, 4], [2, 5], [3, 6]]
     * [[1, 2], [3, 4], [5, 6]] -> [[1, 3, 5], [2, 4, 6]]
     * */
    private List<List<String>> regroupByStudent(List<List<String>> lists) {
        List<List<String>> result = new ArrayList<>();

        if(lists.size() < 1) return result;

        for (int i = 0; i < lists.get(0).size(); i++) {
            result.add(new ArrayList<>());
        }

        for (List<String> arg: lists) {
            for (int i = 0; i < arg.size(); i++) {
                result.get(i).add(arg.get(i));
            }
        }

        return result;
    }

    private List<List<Plant>> parsePlants(List<List<String>> lines) {
        return lines.stream()
                .map(this::mergeList)
                .map(this::convertStringToPlants)
                .collect(Collectors.toList());
    }

    private String mergeList(List<String> list) {
        return String.join("", list);
    }

    private List<Plant> convertStringToPlants(String symbols) {
        return symbols.chars()
                .mapToObj(plantCode -> Plant.getPlant((char) plantCode))
                .collect(Collectors.toList());
    }

    List<Plant> getPlantsOfStudent(String student) {
        return plants.get(studentIndex(student));
    }

    private int studentIndex(String student) {
        return (int) student.charAt(0) - (int) 'A';
    }

}
