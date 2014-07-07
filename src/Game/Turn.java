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
    private ArrayList<Unit> units;
    Map map;
    Team[] teams;

    public Turn(Map map, Team[] teams) {
        units = new ArrayList<Unit>();
        this.map = map;
        this.teams = teams;
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

    public void processConflicts(HashSet<ArrayList<Unit>> set)
            throws Exception {
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
            else if (list.get(0).getPathtype()
                    .compareTo(list.get(1).getPathtype()) < 0) {
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

    /* Ghost cycle */
    public void ghost() {
        System.out.println("Ghost cycle");
        // Update units' next tile
        for (Unit u : units) {
            Path p = u.getPath();
            Tile t = p.getNext();
            u.setNext(t);
        }
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
                u.setTile(p.remove());
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Unit u : units) {
            sb.append(u + ": " + u.getPath() + "\n");
        }
        return sb.toString();
    }
}
