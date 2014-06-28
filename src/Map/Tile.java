package Map;

import Player.Unit;

/** Tiles for map */
public class Tile {

	private Unit unit;
    private Position pos;
	
	public Tile() {
		unit = null;
        pos = null;
	}
    
    public Tile(Position p) {
        unit = null;
        pos = p;
    }
	
	/** Check if tile is empty */
	public boolean isEmpty() {
		if (unit) {
            return false;
        }
        else {
            return true;
        }
	}
    
    /** Set position of tile */
    public void setPos(Position p) {
        this.pos = p;
    }
    
    /** Get position of tile */
    public Position getPos() {
        return this.pos;
    }
	
	/** Set unit on tile */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/** Returns unit on tile */
	public Unit getUnit() {
		return unit;
	}
    
    @Override
    public String toString() {
        return "(" + Integer.toString(pos.getX()) + ", " + Integer.toString(pos.getY()) + ")";
    }
}
