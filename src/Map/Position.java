package Map;

public class Position {
    private int x;
    private int y;

    /* Public methods */
    // Not in use
    public Position() {
        x = -1;
        y = -1;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /* Check if valid position on map */
    public boolean isValid(int width, int height) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return true;
        }
        return false;
    }

    /* Check if position is adjacent */
    public boolean isAdjacent(Position p) {
        if ((Math.abs(this.x - p.x) == 1) && (Math.abs(this.y - p.y) == 0)) {
            return true;
        } else if ((Math.abs(this.y - p.y) == 1)
                && (Math.abs(this.x - p.x) == 0)) {
            return true;
        }
        return false;
    }

    /* Getter */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /* Overrides */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Position p = (Position) obj;
        if (this.x == p.x && this.y == p.y) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
    }
}
