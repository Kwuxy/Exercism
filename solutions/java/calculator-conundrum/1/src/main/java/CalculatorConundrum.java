class CalculatorConundrum {
    public String calculate(int operand1, int operand2, String operation) throws IllegalOperationException, IllegalArgumentException {
        if (operation == null)
            throw new IllegalArgumentException("Operation cannot be null");
        if (operation.isEmpty())
            throw new IllegalArgumentException("Operation cannot be empty");

        return operand1 + " " + operation + " " + operand2 + " = " + getResult(operand1, operand2, operation);
    }

    private int getResult(int operand1, int operand2, String operation) {
        return switch (operation) {
            case "+" -> add(operand1, operand2);
            case "*" -> multiply(operand1, operand2);
            case "/" -> divide(operand1, operand2);
            default -> throw new IllegalOperationException("Operation '" + operation + "' does not exist");
        };
    }

    private int add(int operand1, int operand2) {
        return operand1 + operand2;
    }

    private int multiply(int operand1, int operand2) {
        return operand1 * operand2;
    }

    private int divide(int operand1, int operand2) throws IllegalOperationException {
        if (operand2 == 0) {
            throw new IllegalOperationException("Division by zero is not allowed", new ArithmeticException());
        }

        return operand1 / operand2;
    }
}
