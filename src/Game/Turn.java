package Game;

import Map.Map;
import Map.Path;
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

    public Turn(Map map, Team[] teams) {
        units = new ArrayList<Unit>();
        this.map = map;
        this.teams = teams;
        this.cycle = 0;
    }

    /* Setters below */
    public void incCycle() {
        this.cycle++;
    }
    
    public void resetCycle() {
        this.cycle = 0;
    }

    /* Getters below */
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

    /* Add unit to list */
    public void add(Unit unit) {
        units.add(unit);
    }

    /* Removes specified unit */
    public void remove(Unit unit) {
        units.remove(unit);
    }

    /* Sort units by move priority */
    public void sort() {
        Collections.sort(units);
    }

    public void setUnits() {
        for (Team t : teams) {
            for (Unit u : t.getUnits()) {
                if (!u.isPathEmpty()) {
                    units.add(u);
                }
            }
        }
    }

    /* @formatter:off */
    public void processConflicts(HashSet<ArrayList<Unit>> set)
            throws Exception {
    /* @formatter:on */
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
            else {
                throw new Exception(
                        "Error: Unit list is not sorted by priority");
            }
        }
    }

    public void setNextTiles() {
        for (Unit u : units) {
            Path p = u.getPath();
            Tile t = p.getNext();
            u.setNext(t);
        }
    }

    /* Ghost cycle */
    public void ghost() {
        System.out.println("Ghost cycle");
        // Update units' next tile
        // setNextTiles();
        HashSet<ArrayList<Unit>> set = new HashSet<ArrayList<Unit>>();
        for (Unit u1 : units) {
            ArrayList<Unit> conflicts = new ArrayList<Unit>();
            conflicts.add(u1);
            for (Unit u2 : units) {
                if (u1 == u2) {
                    continue;
                }
                if (u1.getNext() != null) {
                    if (u1.getNext().equals(u2.getNext())) {
                        conflicts.add(u2);
                    }
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

    /* Process turn */
    public void process() {
        System.out.println("Processing");
        ghost();
        ListIterator<Unit> iterator = units.listIterator();
        while (iterator.hasNext()) {
            Unit u = iterator.next();
            if (u.getNext() == null) {
                System.out.println("Removed " + u);
                iterator.remove();
            }
            else {
                Path p = u.getPath();
                try {
                    u.setTile(p.remove());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setNextTiles();
        cycle += 1;
    }

    /* Test printing */
    public void print() {
        for (Unit u : units) {
            System.out.println(u.getTeam() + " - " + u + ": " + u.getPath());
        }
    }

    /* Override */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Unit u : units) {
            sb.append(u + ": " + u.getPath() + "\n");
        }
        return sb.toString();
    }
}
