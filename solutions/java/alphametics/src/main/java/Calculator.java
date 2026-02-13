import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Calculator {
    private final Map<String, Integer> precedence = Stream.of(new String[][] {
            {"+", "1"},
            {"-", "1"},
            {"*", "2"},
            {"/", "2"},
            {"==", "3"},
    }).collect(Collectors.toMap(data -> data[0], data -> Integer.valueOf(data[1])));

    private Stack<Integer> operands;
    private Map<Character, Integer> valueMapping;
    private String[] parts = new String[2];

    private int calculate(String rpnExpression) {
        operands = new Stack<>();
        for (String element : rpnExpression.split(" ")) {
            if(element.matches("\\p{Alpha}*")) {
                operands.add(getOperandValue(element));
                continue;
            }
            processOperation(element);
        }
        return operands.pop();
    }

    private void processOperation(String operator) {
        int operand;

        switch(operator) {
            case "+":
                operands.push(operands.pop() + operands.pop());
                break;
            case "-":
                operand = operands.pop();
                operands.push(operands.pop() - operand);
                break;
            case "*":
                operands.push(operands.pop() * operands.pop());
                break;
            case "/":
                operand = operands.pop();
                operands.push(operands.pop() / operand);
        }
    }

    boolean calculateBooleanWithValueMapping(Map<Character, Integer> valueMapping) {
        this.valueMapping = valueMapping;
        return calculate(parts[0]) == calculate(parts[1]);
    }

    private int getOperandValue(String element) {
        int value = 0;
        for (int i = 0; i < element.split("").length; i++) {
            value = value * 10 + valueMapping.get(element.charAt(i));
        }
        return value;
    }

    private String convertToRpnExpression(String arithmeticExpression) {
        final String[] elements = arithmeticExpression.split(" ");
        final Stack<String> operators = new Stack<>();
        final Queue<String> output = new LinkedList<>();

        for (String element : elements) {
            if(element.matches("\\p{Alpha}*")) {
                output.add(element);
                continue;
            }

            while(!operators.empty()) {
                String operator = operators.peek();
                if(precedence.get(operator) <= precedence.get(element)) {
                    output.add(operators.pop());
                }else{
                    break;
                }
            }

            operators.add(element);
        }

        while(!operators.empty()) {
            output.add(operators.pop());
        }

        return String.join(" ", output);
    }

    void calculateRpnExpression(String arithmeticExpression) {
        final String[] p = arithmeticExpression.split("==");
        for (int i = 0; i < p.length; i++) {
            parts[i] = convertToRpnExpression(p[i].trim());
        }
    }
}
