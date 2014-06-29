package Player;

import Map.Tile;

import java.util.ArrayList;

public class Team {
    private final int MAXUNITS = 12;
    private String name;
    private ArrayList<Unit> units;

    public Team(String name) {
        this.name = name;
        this.units = new ArrayList<Unit>();
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    /** Getter methods below */
    public Unit getUnit(int i) {
        return units.get(i-1); // Return specific unit
    }

    public ArrayList<Unit> getUnits() {
        return units; // Return all units
    }

    public String getName() {
        return name;
    }

    /** Overrides */
    public String toString() {
        return name;
    }
}
