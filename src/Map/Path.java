package Map;

import Player.Unit;
import java.util.ArrayList;

public class Path {
    private ArrayList<Tile> tiles; // Positions in path
    int maxMoves; // Total moves in path
    Pathtype type;

    public Path(Unit unit) {
        this.tiles = new ArrayList<Tile>();
        this.tiles.add(unit.getTile());
        this.maxMoves = unit.getMove();
        this.type = Pathtype.STATIONARY;
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
            System.out.println("Error: Empty path");
            return null;
        }
        Tile t = tiles.get(0);
        // System.out.println(t);
        return t;
    }

    /* Delete next tile in path */
    public Tile remove() {
        Tile t = tiles.remove(0);
        if (tiles.isEmpty()) {
            type = Pathtype.STATIONARY;
        }
        return t;
    }
    
    /* Check if move is valid */
    public boolean isValid(Tile t) {
        if (tiles.size() == 0) {
            System.out.println("Valid");
            return true;
        }
        if (maxMoves == tiles.size() - 1) {
            System.out.println("Not valid: path full");
            return false;
        }
        if (t.isAdjacent(tiles.get(tiles.size() - 1))) {
            for (int i = 0; i < tiles.size(); i++) {
                if (t.equals(tiles.get(i))) {
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
    
    /* Setter methods below */
    public void setType(Pathtype type) {
        this.type = type;
    }
    
    /* Getter methods below */
    public Pathtype getType() {
        return this.type;
    }

    /* Check if path is empty */
    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }
    
    /* Overrides */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tiles.size(); i++) {
            sb.append(tiles.get(i).toString());
        }
        return sb.toString();
    }
}
