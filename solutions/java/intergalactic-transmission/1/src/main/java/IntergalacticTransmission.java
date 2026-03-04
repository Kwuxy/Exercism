import java.util.LinkedList;
import java.util.List;

public class IntergalacticTransmission {

    public static List<Integer> getTransmitSequence(List<Integer> message) {
        return segment(message, 8, 7).stream()
                .map(binary -> (binary << 1) + Integer.bitCount(binary) % 2)
                .toList();
    }

    public static List<Integer> decodeSequence(List<Integer> sequence) throws IllegalArgumentException {
        boolean wrongParityBit = sequence.stream()
                .anyMatch(binary -> binary % 2 != Integer.bitCount(binary >> 1) % 2);

        if (wrongParityBit)
            throw new IllegalArgumentException("Wrong parity bit");

        List<Integer> sequence7bits = sequence.stream()
                .map(binary -> binary >> 1)
                .toList();

        List<Integer> out =  segment(sequence7bits, 7, 8);
        if (out.isEmpty() || out.getLast() != 0)
            return out;

        return out.subList(0, out.size() - 1);
    }

    private static List<Integer> segment(List<Integer> message, int sourceChunkSize, int targetChunkSize) {
        List<Integer> out = new LinkedList<>();
        int buffer = 0;
        int bufferBytes = 0;

        for (int b : message) {
            buffer = (buffer << sourceChunkSize) | b;
            bufferBytes += sourceChunkSize;

            while (bufferBytes >= targetChunkSize) {
                int shift = bufferBytes - targetChunkSize;
                int mask = ((1 << targetChunkSize) - 1);
                int segment = (buffer >> shift) & mask;
                out.add(segment);

                bufferBytes -= targetChunkSize;
                buffer &= (1 << bufferBytes) - 1;
            }
        }

        if (bufferBytes > 0) {
            buffer <<= (targetChunkSize - bufferBytes);
            out.add(buffer);
        }

        return out;
    }

}
