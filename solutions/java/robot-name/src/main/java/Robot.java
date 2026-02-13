import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Robot {
    private static List<String> usedName = new ArrayList<>();
    private String name;
    private Random randomGenerator;

    public Robot() {
        randomGenerator = new Random();
        generateName();
    }

    public String getName() {
        return name;
    }

    private void generateName() {
        generateNonUsedName();
        saveName();
    }

    private void saveName() {
        usedName.add(name);
    }

    private void generateNonUsedName() {
        do {
            name = generateOneName();
        }while(nameIsAlreadyUsed());
    }

    private boolean nameIsAlreadyUsed() {
        return usedName.contains(name);
    }

    private String generateOneName() {
        return generateLetters() + generateNumbers();
    }

    private String generateNumbers() {
        final String digits = "0123456789";
        return generateNamePart(digits, 3);
    }

    private String generateLetters() {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return generateNamePart(alphabet, 2);
    }

    private String generateNamePart(final String alphabet, final int charNumber) {
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < charNumber; i++) {
            builder.append(alphabet.charAt(randomGenerator.nextInt(alphabet.length())));
        }

        return builder.toString();
    }

    public void reset() {
        generateName();
    }
}