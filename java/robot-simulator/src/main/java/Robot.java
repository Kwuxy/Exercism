import java.util.Arrays;

class Robot {

    private GridPosition gridPosition;
    private Orientation orientation;

    public Robot(GridPosition gridPosition, Orientation orientation) {
        this.gridPosition = gridPosition;
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public GridPosition getGridPosition() {
        return this.gridPosition;
    }

    public void turnRight() {
        final int ordinal = getOrientationOrdinal();
        this.orientation = Orientation.values()[(ordinal + 1) % 4];
    }

    public void turnLeft() {
        final int ordinal = getOrientationOrdinal();
        this.orientation = Orientation.values()[(ordinal + 4 - 1) % 4];
    }

    private int getOrientationOrdinal() {
        return Orientation.valueOf(this.orientation.toString()).ordinal();
    }

    public void advance() {
        this.gridPosition = moveForward();
    }

    private GridPosition moveForward() {
        int x = this.gridPosition.x;
        int y = this.gridPosition.y;

        switch(this.orientation) {
            case NORTH:
                y++; break;
            case SOUTH:
                y--; break;
            case EAST:
                x++; break;
            case WEST:
                x--; break;
        }

        return new GridPosition(x, y);
    }

    public void simulate(String sequence) {
        Arrays.stream(sequence.split(""))
                .forEach(this::executeAction);
    }

    private void executeAction(String action) {
        switch(action) {
            case "R":
                turnRight(); break;
            case "L":
                turnLeft(); break;
            case "A":
                advance(); break;
        }
    }
}