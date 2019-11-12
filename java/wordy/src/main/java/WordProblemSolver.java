import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class WordProblemSolver {

    private String phrase;
    private final Map<String, String> operatorsMapping = Stream.of(new String[][] {
            {"minus", "-"},
            {"plus", "+"},
            {"multiplied by", "*"},
            {"divided by", "/"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public int solve(String phrase) {
        this.phrase = phrase;
        final Calculator calculator = new Calculator();

        final String arithmeticExpression = convertPhraseToArithmeticExpression();
        try {
            final String rpnExpression = calculator.convertToRpnExpression(arithmeticExpression);
            if(rpnExpression.length() == 0) {
                throwError();
            }

            return calculator.calculate(rpnExpression);
        } catch (Exception e) {
            throwError();
            return 0;
        }
    }

    private void throwError() {
        throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
    }

    private String convertPhraseToArithmeticExpression() {
        cutPhraseQuestionFormula();
        replaceArithmeticOperators();
        return phrase;
    }

    private void replaceArithmeticOperators() {
        for (Map.Entry<String, String> entry : operatorsMapping.entrySet()) {
            phrase = phrase.replaceAll(entry.getKey(), entry.getValue());
        }
    }

    private void cutPhraseQuestionFormula() {
        if(phrase.length() > 8) {
            phrase = phrase.substring(8, phrase.length() - 1);
        }
    }
}