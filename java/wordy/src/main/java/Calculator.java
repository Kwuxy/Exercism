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
    }).collect(Collectors.toMap(data -> data[0], data -> Integer.valueOf(data[1])));

    private Stack<Integer> operands;

    int calculate(String rpnExpression) {
        operands = new Stack<>();
        for (String element : rpnExpression.split(" ")) {
            if(isInteger(element)) {
                operands.add(getOperandValue(element));
                continue;
            }
            processOperation(element);
        }
        if(operands.size() > 1) {
            throw new IllegalArgumentException();
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

    private int getOperandValue(String element) {
        return Integer.valueOf(element);
    }

    String convertToRpnExpression(String arithmeticExpression) {
        final String[] elements = arithmeticExpression.split(" ");
        final Stack<String> operators = new Stack<>();
        final Queue<String> output = new LinkedList<>();
        boolean lastCharacterWasNumber = false;

        for (String element : elements) {
            if(isInteger(element)) {
                if(lastCharacterWasNumber) throw new IllegalArgumentException();

                lastCharacterWasNumber = true;
                output.add(element);
                continue;
            }

            lastCharacterWasNumber = false;

            if(!isSupported(element)) {
                throw new IllegalArgumentException();
            }

            while(!operators.empty()) {
                String operator = operators.peek();
                if(precedence.get(operator) > precedence.get(element)) {
                    break;
                }

                output.add(operators.pop());
            }

            operators.add(element);
        }

        while(!operators.empty()) {
            output.add(operators.pop());
        }

        return String.join(" ", output);
    }

    private boolean isSupported(String element) {
        return precedence.containsKey(element);
    }

    private boolean isInteger(String element) {
        return element.matches("[-+]?\\p{Digit}+");
    }
}
