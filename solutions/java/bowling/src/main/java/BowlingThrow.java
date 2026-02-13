public class BowlingThrow {
    int pins;
    int roll;
    int score;
    SpecialThrow specialThrow;

    BowlingThrow(int throwNumber, int pins) {
        this.roll = throwNumber;
        this.pins = pins;
        this.score = 0;
        this.specialThrow = SpecialThrow.NONE;
    }

    @Override
    public String toString() {
        return "BowlingThrow{" +
                "pins=" + pins +
                ", roll=" + roll +
                ", score=" + score +
                ", specialThrow=" + specialThrow +
                '}';
    }
}
