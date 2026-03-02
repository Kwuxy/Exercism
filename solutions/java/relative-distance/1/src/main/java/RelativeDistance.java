import java.util.*;

record SeparationCounter(String person, int degree) {}

class RelativeDistance {

    private final Map<String, List<String>> familyTree = new HashMap<>();

    RelativeDistance(Map<String, List<String>> familyTree) {
        for (var entry : familyTree.entrySet()) {
            var relatives = this.familyTree.getOrDefault(entry.getKey(), new LinkedList<>());
            relatives.addAll(entry.getValue());
            this.familyTree.put(entry.getKey(), relatives);

            for (var child : entry.getValue()) {
                var childRelatives = this.familyTree.getOrDefault(child, new LinkedList<>());
                var siblings = entry.getValue().stream().filter(sibling -> !sibling.equals(child)).toList();
                childRelatives.add(entry.getKey());
                childRelatives.addAll(siblings);
                this.familyTree.put(child, childRelatives);
            }
        }
    }

    int degreeOfSeparation(String personA, String personB) {
        Set<String> visited = new HashSet<>();
        Deque<SeparationCounter> persons = new ArrayDeque<>();
        persons.add(new SeparationCounter(personA, 0));

        while (!persons.isEmpty()) {
            var current = persons.pop();
            if (visited.contains(current.person()))
                continue;

            if (current.person().equals(personB))
                return current.degree();

            for (var relative : familyTree.getOrDefault(current.person(), List.of())) {
                persons.add(new SeparationCounter(relative, current.degree() + 1));
            }

            visited.add(current.person());
        }

        return -1;
    }
}
