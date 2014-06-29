package Map;

import Player.Unit;

import java.util.ArrayList;

public class Path {
    private ArrayList<Tile> tiles; // Positions in path
    int maxMoves; // Total moves in path

    /** Creates position big enough for unit move */
    public Path(Unit unit) {
        tiles = new ArrayList<Tile>();
        tiles.add(unit.getTile());
        maxMoves = unit.getMove();
    }

    /** Add position to path */
    public void add(Tile t) {
        if (isValid(t)) {
            tiles.add(t);
        }
    }

    /** Step through the next position in path */
    public Tile stepPath() {
        if (tiles.size() == 0) return null;
        Tile t = tiles.get(0);
        tiles.remove(tiles.size()-1);
        return t;
    }

    /** Check if move is valid */
    public boolean isValid(Tile t) {
        if (tiles.size() == 0) {
            System.out.println("Valid");
            return true;
        }
        if (t.isAdjacent(tiles.get(tiles.size()-1))) {
            for (int i = 0; i < tiles.size(); i++) {
                if (t.equals(tiles.get(i))) {
                    System.out.println("Not valid: Already moving there");
                    return false;
                }
            }
            System.out.println("Valid");
            return true;
        }
        System.out.println("Not valid: not adjacent");
        return false;
    }

    /** Overrides */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tiles.size(); i++) {
            sb.append(tiles.get(i).toString());
        }
        return sb.toString();
    }
}
