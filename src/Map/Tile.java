package Map;

import Player.Unit;

/** Tiles for map */
public class Tile {
    private boolean empty;
    private Unit unit;

    public Tile() {
        empty = true;
        unit = null;
    }

    /** Check if tile is empty */
    public boolean isEmpty() {
        return empty;
    }

    /** Set unit on tile */
    public void setUnit(Unit unit) {
        this.unit = unit;
        empty = false;
    }

    /** Returns unit on tile */
    public Unit getUnit() {
        return unit;
    }
}
