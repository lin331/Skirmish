package Map;

import Player.Unit;

/** Tiles for map */
public class Tile {
	private boolean empty;
	private Unit unit;
    private Position pos;
	
	public Tile() {
		empty = true;
		unit = null;
        pos = null;
	}
	
	/** Check if tile is empty */
	public boolean isEmpty() {
		return empty;
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
		empty = false;
	}
	
	/** Returns unit on tile */
	public Unit getUnit() {
		return unit;
	}
}
