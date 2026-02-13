import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class HandshakeCalculator {

    private final int REVERSE = (int) Math.pow(2, 4);

    List<Signal> calculateHandshake(int number) {
        boolean reverse = false;
        if(number >= REVERSE) {
            reverse = true;
            number -= REVERSE;
        }

        final List<Signal> signals = getSignalsFromNumber(number, new ArrayList<>());
        return reverseList(signals, reverse);
    }

    private Signal getSingleSignalFromPowerOf2Number(double powerOf2Number) {
        return Signal.values()[(int) (Math.log(powerOf2Number) / Math.log(2))];
    }

    private List<Signal> getSignalsFromNumber(int number, List<Signal> signals) {
        for(int i = 3; i >= 0; i--) {
            number = addSingleSignalFromIndex(number, Math.pow(2, i), signals);
        }

        return signals;
    }

    private int addSingleSignalFromIndex(int number, double powerOf2Number, List<Signal> signals) {
        if(powerOf2Number <= number) {
            signals.add(0, getSingleSignalFromPowerOf2Number(powerOf2Number));
            number -= powerOf2Number;
        }

        return number;
    }

    private List<Signal> reverseList(List<Signal> signals, boolean reverse) {
        if(reverse) {
            Collections.reverse(signals);
        }

        return signals;
    }
}
