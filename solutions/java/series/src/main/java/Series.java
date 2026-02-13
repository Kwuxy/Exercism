import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Series {

    private final String sequence;
    private int slicesLength;

    public Series(String sequence) {
        this.sequence = sequence;
    }

    public List<String> slices(int slicesLength) {
        this.slicesLength = slicesLength;
        checkSlicesRequestIsValid();

        int sequenceSlicesNumber = getSequenceSlicesNumber();
        return sliceSequence(sequenceSlicesNumber);
    }

    private List<String> sliceSequence(int sequenceSlicesNumber) {
        return IntStream.range(0, sequenceSlicesNumber)
                .mapToObj(this::sliceSequenceAt)
                .collect(Collectors.toList());
    }

    private String sliceSequenceAt(int index) {
        return sequence.substring(index, index + slicesLength);
    }

    private int getSequenceSlicesNumber() {
        return sequence.length() - slicesLength + 1;
    }

    private void checkSlicesRequestIsValid() {
        checkSlicesLengthIsTooBig();
        checkSlicesLengthIsTooSmall();
    }

    private void checkSlicesLengthIsTooBig() {
        if(slicesLength > sequence.length()) {
            throw new IllegalArgumentException("Slice size is too big.");
        }
    }

    private void checkSlicesLengthIsTooSmall() {
        if(slicesLength < 1) {
            throw new IllegalArgumentException("Slice size is too small.");
        }
    }
}