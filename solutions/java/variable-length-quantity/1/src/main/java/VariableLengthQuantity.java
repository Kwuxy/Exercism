import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

class VariableLengthQuantity {

    List<String> encode(List<Long> numbers) {
        List<String> result = new ArrayList<>();
        for (var number : numbers) {
            List<Long> base128 = toBase128(number);

            for (int i = 0; i < base128.size(); i++) {
                Long num = base128.get(i);
                if (i != base128.size() - 1) {
                    num |= 0x80;
                }

                result.add("0x" + Long.toString(num, 16));
            }
        }

        return result;
    }

    List<String> decode(List<Long> bytes) {
        List<String> result = new ArrayList<>();
        long current = 0L;
        boolean hasIncompleteSequence = false;

        for (var num : bytes) {
            current = (current << 7) | (num & 0x7f);
            hasIncompleteSequence = true;

            if (num < 0x80) {
                result.add("0x" + Long.toString(current, 16));
                current = 0L;
                hasIncompleteSequence = false;
            }
        }

        if (hasIncompleteSequence) {
            throw new IllegalArgumentException("Invalid variable-length quantity encoding");
        }

        return result;
    }

    private List<Long> toBase128(Long number) {
        if (number == 0) {
            return List.of(0L);
        }

        return LongStream.iterate(number, n -> n > 0, n -> n / 128)
                .map(n -> n % 128)
                .boxed()
                .toList()
                .reversed();
    }
}
