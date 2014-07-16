package Game;

import Map.Map;
import Map.Path;
import Map.Pathtype;
import Map.Tile;
import Player.Team;
import Player.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.ListIterator;

public class Turn {

    private final int MAX_COMMANDS = 3;
    private ArrayList<Unit> units;
    Map map;
    Team[] teams;
    int cycle;

    /* Public methods */
    public Turn(Map map, Team[] teams) {
        units = new ArrayList<Unit>();
        this.map = map;
        this.teams = teams;
        this.cycle = 0;
    }

    /* Sets up turn for processing */
    public void setup() {
        setUnits();
        sort();
        setNextTiles();
    }

    /* Getters */
    public int getMaxCommands() {
        return MAX_COMMANDS;
    }

    public Map getMap() {
        return this.map;
    }

    public int getCycle() {
        return this.cycle;
    }

    /* Check if list is empty */
    public boolean isEmpty() {
        return units.isEmpty();
    }

    /* Returns size of list */
    public int size() {
        return units.size();
    }

    /* Setters */
    public void incCycle() {
        this.cycle++;
    }

    public void resetCycle() {
        this.cycle = 0;
    }

    /* Process turn */
    public void process() {
        System.out.println("Processing");
        ghostCycle();
        ListIterator<Unit> iterator = units.listIterator();
        while (iterator.hasNext()) {
            Unit u = iterator.next();
            if (u.getNext() == null) {
                System.out.println("Removed " + u);
                iterator.remove();
            }
            else {
                Path p = u.getPath();
                u.setTile(p.remove());
            }
        }
        // Update units' next tiles
        setNextTiles();
        incCycle();
    }

    /* Check unit's turn delay and decrement if needed */
    public void checkDelay() {
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (Unit u : units) {
                u.decPathDelay();
            }
        }
    }

    /* Test printing */
    public void print() {
        for (Unit u : units) {
            System.out.println(u.getTeam() + " - " + u + ": " + u.getPath());
        }
    }

    /* Override */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Unit u : units) {
            sb.append(u + ": " + u.getPath() + "\n");
        }
        return sb.toString();
    }

    /* Private methods */
    /* Add unit to list */
    // Not used currently
    private void add(Unit unit) {
        units.add(unit);
    }

    /* Removes specified unit */
    // Not used currently
    private void remove(Unit unit) {
        units.remove(unit);
    }

    /* Sort units by move priority */
    private void sort() {
        Collections.sort(units);
    }

    /* Add units to Turn that are moving */
    private void setUnits() {
        for (Team t : teams) {
            for (Unit u : t.getUnits()) {
                if (!u.isPathEmpty()) {
                    Path p = u.getPath();
                    if (p.getDelay() == 0) {
                        units.add(u);
                    }
                }
            }
        }
    }

    /* Set all units' next tile */
    private void setNextTiles() {
        for (Unit u : units) {
            Path p = u.getPath();
            Tile t = p.getNext();
            u.setNext(t);
            if (t == null) {
                u.setPathtype(Pathtype.STATIONARY);
            }
        }
    }
    
    /* Process tile conflicts */
    private void processConflicts(HashSet<ArrayList<Unit>> set) {
        if (!set.isEmpty()) {
            System.out.println("Conflicts: processing");
            for (ArrayList<Unit> list : set) {
                Collections.sort(list);
                /* @formatter:off */
                if (list.get(0).getPathtype()
                        .compareTo(list.get(1).getPathtype()) == 0) {
                /* @formatter:on */
                    for (Unit u : list) {
                        u.setNext(null);
                        u.clearPath();
                    }
                }
                /* @formatter:off */
                else if (list.get(0).getPathtype()
                        .compareTo(list.get(1).getPathtype()) < 0) {
                /* @formatter:on */
                    list.remove(0);
                    for (Unit u : list) {
                        u.setNext(null);
                        u.clearPath();
                    }
                }
            }
        }
    }

    /* Ghost cycle */
    private void ghostCycle() {
        System.out.println("Ghost cycle");
        HashSet<ArrayList<Unit>> set = new HashSet<ArrayList<Unit>>();
        ArrayList<Unit> temp = new ArrayList<Unit>(units);
        ListIterator<Unit> iterator = temp.listIterator();
        for (Unit u1 : units) {
            Tile next = u1.getNext();
            if (next != null) {
                Unit u = next.getUnit();
                if (u != null) {
                    if (u.getPathtype() == Pathtype.STATIONARY) {
                        u1.clearPath();
                        continue;
                    }
                }
            }
            ArrayList<Unit> conflicts = new ArrayList<Unit>();
            conflicts.add(u1);
            while (iterator.hasNext()) {
                Unit u2 = iterator.next();
                if (u1 != u2) {
                    if (u1.getNext() != null) {
                        if (u1.getNext().equals(u2.getNext())) {
                            conflicts.add(u2);
                            iterator.remove();
                        }
                    }
                }
                else {
                    iterator.remove();
                }
            }
            if (conflicts.size() > 1) {
                set.add(conflicts);
            }
        }
        try {
            processConflicts(set);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
