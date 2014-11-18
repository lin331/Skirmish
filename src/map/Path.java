package map;

import static output.Output.Print.*;

import player.Unit;

import java.util.ArrayList;


public class Path {
    private Pathtype type;
    private Unit unit;
    private ArrayList<Tile> tiles; // Positions in path
    private int maxMoves; // Total moves in path
    private int delay;
    
    /*
     *  Public methods 
    */
    public Path(Unit unit) {
        this.type = Pathtype.STATIONARY;
        this.unit = unit;
        this.tiles = new ArrayList<Tile>();
        this.maxMoves = unit.getMove();
        delay = 0;
    }

    /* Get path length */
    public int getSize() {
        return tiles.size();
    }
    
    /* Add position to path */
    public Tile add(Tile t) {
        if (isValid(t)) {
            tiles.add(t);
            return t;
        }
        return null;
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
    public Tile remove() {
        if (type == Pathtype.STATIONARY) {
            printf("log.txt", "!!!!!Unit is stationary....!!!!!");
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
            Tile cur = unit.getTile();
            if (t.isAdjacent(cur)) {
                return true;
            }
            return false;
        }
        if (maxMoves == tiles.size()) {
            return false;
        }
        if (t.isAdjacent(tiles.get(tiles.size() - 1))) {
            for (Tile tile : tiles) {
                if (t.equals(tile)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean isValid(Tile t, boolean flag) {
        if (tiles.size() == 0) {
            Tile cur = unit.getTile();
            if (t.isAdjacent(cur)) {
                return true;
            }
            return false;
        }
        if (flag) {
            if (maxMoves == tiles.size() - 1) {
                return false;
            }
        }
        if (maxMoves == tiles.size()) {
            return false;
        }
        if (t.isAdjacent(tiles.get(tiles.size() - 1))) {
            for (Tile tile : tiles) {
                if (t.equals(tile)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    /* Clear path */
    public void clear() {
        this.tiles.clear();
        this.type = Pathtype.STATIONARY;
        this.delay = 0;
    }

    /* Setters below */
    public void setType(Pathtype type) {
        this.type = type;
    }

    public void setDelay(int d) {
        this.delay = d;
    }
    
    public void decDelay() {
        if (delay == 0) {
            return;
        }
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

    public ArrayList<Tile> getTiles() {
        return this.tiles;
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
