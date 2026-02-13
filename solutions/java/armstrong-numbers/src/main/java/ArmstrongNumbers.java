import java.util.stream.IntStream;

class ArmstrongNumbers {

	boolean isArmstrongNumber(int numberToCheck) {
		String numberAsString = String.valueOf(numberToCheck);
		int digitNumber = numberAsString.length();

		int sum = numberAsString.chars()
				.map(charDigit -> charDigit - '0')
				.map(digit -> (int) Math.pow(digit, digitNumber))
				.sum();

		return sum == numberToCheck;
	}

}
