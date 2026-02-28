import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class GottaSnatchEmAll {

    static Set<String> newCollection(List<String> cards) {
        return new HashSet<String>(cards);
    }

    static boolean addCard(String card, Set<String> collection) {
        return collection.add(card);
    }

    private static boolean hasUniqueCards(Set<String> myCollection, Set<String> theirCollection) {
        return myCollection.stream().anyMatch(card -> !theirCollection.contains(card));
    }

    static boolean canTrade(Set<String> myCollection, Set<String> theirCollection) {
        if (myCollection.isEmpty() || theirCollection.isEmpty())
            return false;

        return hasUniqueCards(myCollection, theirCollection) && hasUniqueCards(theirCollection, myCollection);
    }

    static Set<String> commonCards(List<Set<String>> collections) {
        if (collections.isEmpty())
            return Set.of();

        return collections.getFirst().stream()
                .filter(card -> collections.stream().allMatch(collection -> collection.contains(card)))
                .collect(Collectors.toSet());
    }

    static Set<String> allCards(List<Set<String>> collections) {
        return collections.stream().flatMap(Set::stream).collect(Collectors.toSet());
    }
}
