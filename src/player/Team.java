package player;

import java.util.ArrayList;

public class Team {
    // private final int MAXUNITS = 12;
    private String name;
    private ArrayList<Unit> units;

    /* Public methods */
    public Team(String name) {
        this.name = name;
        this.units = new ArrayList<Unit>();
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void remove(Unit unit) {
        units.remove(unit);
    }

    /* Getters */
    public Unit getUnit(int i) {
        return units.get(i - 1); // Return specific unit
    }

    public ArrayList<Unit> getUnits() {
        return units; // Return all units
    }

    public String getName() {
        return name;
    }

    public boolean hasUnits() {
        for (Unit u : units) {
            if (!u.isDead()) {
                return true;
            }
        }
        return false;
    }
    
    public int getSize() {
        return units.size();
    }
    
    /* Overrides */
    @Override
    public String toString() {
        return name;
    }
}
