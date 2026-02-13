import java.util.Arrays;

class Triangle {

    private double[] sides = new double[3];

    Triangle(double side1, double side2, double side3) throws TriangleException {
        sides[0] = side1;
        sides[1] = side2;
        sides[2] = side3;

        if(!isValid()) throw new TriangleException();
    }

    private boolean isValid() {
        if(isOneSideEqualToZero()) return false;

        return !isInequality();
    }

    private boolean isInequality() {
        for (int counter1 = 0; counter1 < sides.length; counter1++) {
            double sum = 0;
            for (int counter2 = 0; counter2 < sides.length; counter2++) {
                if(counter1 != counter2) {
                    sum += sides[counter2];
                }
            }

            if(sum < sides[counter1]) return true;
        }

        return false;
    }

    boolean isEquilateral() {
        return sides[0] == sides[1] && sides[0] == sides[2];
    }

    boolean isIsosceles() {
        return sides[0] == sides[1] || sides[0] == sides[2] || sides[1] == sides[2];
    }

    boolean isScalene() {
        return sides[0] != sides[1] && sides[0] != sides[2] && sides[1] != sides[2];
    }

    private boolean isOneSideEqualToZero() {
        return Arrays.stream(sides).anyMatch(side -> side == 0);
    }
}
