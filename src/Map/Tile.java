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
	
	public boolean empty() {
		return empty;
	}
	
	public Unit getUnit() {
		return unit;
	}
}
