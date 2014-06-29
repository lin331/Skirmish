package Map;

import Player.Unit;

/** Tiles for map */
public class Tile {

    private Unit unit;
    private int x;
    private int y;

    public Tile(int x, int y) {
        unit = null;
        this.x = x;
        this.y = y;
    }

    /** Check if tile is empty */
    public boolean isEmpty() {
        if (unit != null) {
            return false;
        }
        return true;
    }

    /** Check if tile is adjacent */
    public boolean isAdjacent(Tile t) {
        if ((Math.abs(this.x - t.x) == 1) && (Math.abs(this.y - t.y) == 0)) {
            return true;
        } else if ((Math.abs(this.y - t.y) == 1)
                && (Math.abs(this.x - t.x) == 0)) {
            return true;
        }
        return false;
    }

    /** Set unit on tile */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /** Getter methods below */
    public Unit getUnit() {
        return unit;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** Overrides */
    public String toString() {
        return "(" + Integer.toString(this.x) + ", " + Integer.toString(this.y)
                + ")";
    }
}
