package Map;

import Player.Unit;

import java.util.ArrayList;

public class Path {
    private ArrayList<Position> pos; // Positions in path
    int maxMoves; // Total moves in path

    /** Creates position big enough for unit move */
    public Path(Unit unit) {
        pos = new ArrayList<Position>();
        pos.add(unit.getPos());
        maxMoves = unit.getMove();
    }

    /** Add position to path */
    public void add(Position p) {
        if (isValid(p)) {
            pos.add(p);
        }
    }

    /** Step through the next position in path */
    public Position stepPath() {
        if (pos.size() == 0) return null;
        Position p = pos.get(0);
        pos.remove(pos.size()-1);
        return p;
    }

    /** Check if move is valid */
    public boolean isValid(Position p) {
        if (pos.size() == 0) {
            System.out.println("Valid");
            return true;
        }
        if (p.isAdjacent(pos.get(pos.size()-1))) {
            for (int i = 0; i < pos.size(); i++) {
                if (p.equals(pos.get(i))) {
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
        for (int i = 0; i < pos.size(); i++) {
            sb.append(pos.get(i).toString());
        }
        return sb.toString();
    }
}
