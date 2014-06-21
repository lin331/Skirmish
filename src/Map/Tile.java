package Map;
import Player.Unit;

/**
 * 
 * @author Wells
 *
 * Tiles for map
 */
public class Tile {
	private boolean empty;
	private Unit unit;
	
	public Tile() {
		empty = true;
		unit = null;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
		empty = false;
	}
	
	public Unit getUnit() {
		return unit;
	}
}
