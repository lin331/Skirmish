package Game;

import Map.Path;
import Player.Unit;
import java.util.ArrayList;
import java.util.ListIterator;


public class Turn {
    private ArrayList<Unit> units;

    public Turn() {
        units = new ArrayList<Unit>();
    }

    /** Check if list is empty */
    public boolean isEmpty() {
        return units.isEmpty();
    }

    /** Returns size of list */
    public int size() {
        return units.size();
    }

    /** Add unit to list */
    public void add(Unit unit) {
        units.add(unit);
    }

    /** Removes specified unit */
    public void remove(Unit unit) {
        units.remove(unit);
    }

    /** Process turn */
    public void process() {
        System.out.println("Processing");
        ArrayList<Unit> removed = new ArrayList<Unit>();
        ListIterator<Unit> iterator = units.listIterator();
        while (iterator.hasNext()) {
            Unit u = iterator.next();
            Path p = u.getPath();
            if (p.isEmpty()) {
                System.out.println("Removing " + u);
                removed.add(u);
            }
            else {
                u.setTile( p.stepPath());
            }
        }
        if (!removed.isEmpty()) {
            iterator = removed.listIterator();
            while (iterator.hasNext()) {
                Unit u = iterator.next();
                units.remove(u);
            }
        }
    }
    
    /** Test printing */
    public void print() {
        /*
         * System.out.println(node.unit.getTeam().toString() + " - " +
         * node.unit.toString() + ": " + node.unit.getPath().toString());
         */
    }
}
