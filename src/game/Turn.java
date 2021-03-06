package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.ListIterator;

import player.Team;
import player.Unit;
import player.UnitType;
import map.Path;
import map.Pathtype;
import map.Tile;

public class Turn {

    private final int MAX_COMMANDS = 3;
    private ArrayList<Unit> units; // Units represent move commands
    private Team[] teams; // Used to get 

    /* Public methods */
    public Turn(Team[] teams) {
        units = new ArrayList<Unit>();
        this.teams = teams;
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

    public Team[] getTeams() {
        return this.teams;
    }

    /* Check if list is empty */
    public boolean isEmpty() {
        return units.isEmpty();
    }

    /* Returns size of list */
    public int size() {
        return units.size();
    }
    
    /* Process turn */
    public void process() {
        System.out.println("Processing");
        // Run ghost cycle to check for conflicts
        ghostCycle();
        // Iterate through list of commands
        ListIterator<Unit> iterator = units.listIterator();
        while (iterator.hasNext()) {
            Unit u = iterator.next();
            if (u.isDead()) {
                // Remove unit from Turn if unit is dead
                iterator.remove();
            }
            else if (u.getNext() == null) {
                // Remove unit from Turn if unit finished moving
                System.out.println("Removed " + u);
                iterator.remove();
            }
            else {
                // Updates unit's tile
                Path p = u.getPath();
                u.setTile(p.remove());
            }
        }
        // Update units' next tiles
        setNextTiles();
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
    private void add(Unit unit) {
        units.add(unit);
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
                        add(u);
                    }
                }
            }
        }
    }

    /* Set all units' next tile */
    private void setNextTiles() {
        for (Unit u : units) {
            if (!u.isDead()) {
                Path p = u.getPath();
                Tile t = p.getNext();
                u.setNext(t);
                if (t == null) {
                    u.setPathtype(Pathtype.STATIONARY);
                }
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
