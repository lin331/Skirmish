package Map;

import Player.Unit;
import java.util.ArrayList;

public class Path {
    private Pathtype type;
    private ArrayList<Tile> tiles; // Positions in path
    private int maxMoves; // Total moves in path
    private int delay;
    
    public Path(Unit unit) {
        this.type = Pathtype.STATIONARY;
        this.tiles = new ArrayList<Tile>();
        // this.tiles.add(unit.getTile());
        this.maxMoves = unit.getMove();
        delay = 0;
    }

    /* Add position to path */
    public void add(Tile t) {
        if (isValid(t)) {
            tiles.add(t);
        }
    }

    /* Get next tile in path */
    public Tile getNext() {
        if (tiles.isEmpty()) {
            return null;
        }
        Tile t = tiles.get(0);
        return t;
    }

    /* Delete next tile in path */
    public Tile remove() throws Exception {
        if (type == Pathtype.STATIONARY) {
            throw new Exception("Path statioanry");
        }
        Tile t = tiles.remove(0);
        if (tiles.isEmpty()) {
            type = Pathtype.STATIONARY;
        }
        return t;
    }

    /* Check if move is valid */
    public boolean isValid(Tile t) {
        if (tiles.size() == 0) {
            // System.out.println("Valid");
            return true;
        }
        if (maxMoves == tiles.size()) {
            System.out.println("Not valid: path full");
            return false;
        }
        if (t.isAdjacent(tiles.get(tiles.size() - 1))) {
            for (Tile tile : tiles) {
                if (t.equals(tile)) {
                    System.out.println("Not valid: Already moving there");
                    return false;
                }
            }
            // System.out.println("Valid");
            return true;
        }
        System.out.println("Not valid: not adjacent");
        return false;
    }

    /* Clear path */
    public void clear() {
        this.tiles.clear();
        this.type = Pathtype.STATIONARY;
    }

    /* Setters below */
    public void setType(Pathtype type) {
        this.type = type;
    }

    public void decreaseDelay() {
        this.delay -= 1;
    }
    
    /* Getter methods below */
    public Pathtype getType() {
        return this.type;
    }

    public int getDelay() {
        return this.delay;
    }
    
    /* Check if path is empty */
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /* Overrides */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tile tile : tiles) {
            sb.append(tile.toString());
        }
        return sb.toString();
    }
}
