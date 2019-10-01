import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

class VariableLengthQuantity {

    List<String> encode(List<Long> numbers) {
        List<String> result = new ArrayList<>();

        for (Long number : numbers) {
            List<String> binary7bytesNumber = transformNumbersAsBinary7bytes(number);
            List<String> fullBinaryNumbers = add8thByteToList(binary7bytesNumber);
            final List<String> hexadecimalStringList = convertBinaryStringListToHexadecimalStringList(fullBinaryNumbers);
            result.addAll(hexadecimalStringList);
        }

        return result;
    }

    private List<String> convertBinaryStringListToHexadecimalStringList(List<String> fullBinaryNumbers) {
        return fullBinaryNumbers.stream()
                .map(this::convertBinaryStringToHexadecimalString)
                .collect(Collectors.toList());
    }

    private String convertBinaryStringToHexadecimalString(String fullBinaryNumber) {
        final String draftHexadecimalString = new BigInteger(fullBinaryNumber, 2).toString(16);
        return new StringBuilder(draftHexadecimalString).insert(0, "0x").toString();
    }

    private List<String> add8thByteToList(List<String> binary7bytesNumber) {
        final List<String> binary8BytesString = binary7bytesNumber.stream()
                .map(this::add8thByteToSingleNumber)
                .collect(Collectors.toList());

        transform8thByteOfLastNumber(binary8BytesString);
        return binary8BytesString;
    }

    private void transform8thByteOfLastNumber(List<String> binary8BytesString) {
        final int lastIndex = binary8BytesString.size() - 1;
        final String lastString = binary8BytesString.get(lastIndex);
        final StringBuilder stringBuilder = new StringBuilder(lastString);
        stringBuilder.setCharAt(0, '0');
        binary8BytesString.set(lastIndex, stringBuilder.toString());
    }

    private String add8thByteToSingleNumber(String binaryString) {
        final String binary7bytesString = completeBinaryStringTo7bytes(binaryString);
        return new StringBuilder(binary7bytesString).insert(0, "1").toString();
    }

    private String completeBinaryStringTo7bytes(String binaryString) {
        final StringBuilder builder = new StringBuilder(binaryString);
        while(builder.length() < 7) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }

    private List<String> transformNumbersAsBinary7bytes(Long number) {
        final String binaryString = Long.toBinaryString(number);
        return cutBinaryStringAt7thByte(binaryString);
    }

    private List<String> reverseBinaryStringAt7thByte(List<String> reversedParts) {
        for (int i = 0; i < reversedParts.size(); i++) {
            final String part = reverseString(reversedParts.get(i));
            reversedParts.set(i, part);
        }

        Collections.reverse(reversedParts);
        return reversedParts;
    }

    private List<String> cutBinaryStringAt7thByte(String s) {
        s = reverseString(s);
        final String[] parts = s.split("(?<=\\G.{7})");
        final List<String> reversedParts = Arrays.asList(parts);
        return reverseBinaryStringAt7thByte(reversedParts);
    }

    private String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    List<String> decode(List<Long> bytes) {
        final List<List<Long>> VLQNumbers = splitByNumber(bytes);

        return VLQNumbers.stream()
                .map(this::decodeSingleVLQ)
                .collect(Collectors.toList());
    }

    private String decodeSingleVLQ(List<Long> vlqNumber) {
        final String binaryString = convertSingleVLQToBinaryString(vlqNumber);
        final String draftHexadecimal = new BigInteger(binaryString, 2).toString(16);
        return new StringBuilder(draftHexadecimal).insert(0, "0x").toString();
    }

    private String convertSingleVLQToBinaryString(List<Long> vlqNumber) {
        return vlqNumber.stream()
                .map(Long::toBinaryString)
                .map(this::completeTo8Bytes)
                .map(binary -> binary.substring(1))
                .collect(Collectors.joining());
    }

    private String completeTo8Bytes(String binary) {
        final StringBuilder stringBuilder = new StringBuilder(binary);
        while(stringBuilder.length() < 8) {
            stringBuilder.insert(0, '0');
        }
        return stringBuilder.toString();
    }

    private List<List<Long>> splitByNumber(List<Long> bytes) {
        final List<List<Long>> VLQNumbers = new ArrayList<>();
        List<Long> VLQ = new ArrayList<>();

        if (bytes.get(bytes.size() - 1) >= 0x80) {
            throw new IllegalArgumentException("Invalid variable-length quantity encoding");
        }

        for (Long VLQPart : bytes) {
            VLQ.add(VLQPart);
            if (VLQPart < 0x80) {
                VLQNumbers.add(VLQ);
                VLQ = new ArrayList<>();
            }
        }

        return VLQNumbers;
    }
}
