package Player;

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
    
    /* Overrides */
    @Override
    public String toString() {
        return name;
    }
}
