package Game;

import Map.Map;
import Map.Path;
import Map.Tile;
import Player.Team;
import Player.Unit;

import java.util.ArrayList;
import java.util.Collections;
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
    
    /* Ghost cycle */
    public void ghost() {
        System.out.println("Ghost");
        ListIterator<Unit> iterator = units.listIterator();
        // Update units' next tile
        while (iterator.hasNext()) {
            Unit u = iterator.next();
            Path p = u.getPath();
            Tile t = p.getNext();
            u.setNext(t);
        }
    }
    
    /* Process turn */
    public void process() {
        System.out.println("Processing");
        // ghost();
        ListIterator<Unit> iterator = units.listIterator();
        while (iterator.hasNext()) {
            Unit u = iterator.next();
            Path p = u.getPath();
            u.setTile(p.remove());
            if (p.isEmpty()) {
                System.out.println("Removed " + u);
                iterator.remove();
            }
        }
    }

    /* Test printing */
    public void print() {
        ListIterator<Unit> iterator = units.listIterator();
        while (iterator.hasNext()) {
            Unit u = iterator.next();
            System.out.println(u.getTeam() + " - " + u + ": " + u.getPath());
        }
    }

    /* Override */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < units.size(); i++) {
            sb.append(units.get(i) + ": " + units.get(i).getPath() + "\n");
        }
        return sb.toString();
    }
}
