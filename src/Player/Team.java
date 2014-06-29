package Player;

import Map.Tile;
import java.util.Scanner;

public class Team {
    private final int MAXUNITS = 12;
    private String name;
    private int num;
    private Unit units[];

    public Team(String name) {
        this.name = name;
        num = 0;
        units = new Unit[MAXUNITS];
    }

    public void addUnit(Unit unit) {
        units[num] = unit;
        num++;
    }

    /** Add units to team (Text input) */
    public void addUnits() {
        @SuppressWarnings("resource")
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < MAXUNITS; i++) {
            System.out.println("Unit #" + (i + 1) + ":");
            System.out.println("Enter x coordinate: ");
            String string = s.next();
            if (string.equals("end")) {
                break;
            }
            int x = Integer.parseInt(string);
            System.out.println("Enter y coordinate: ");
            int y = s.nextInt();
            num += 1;
            Tile tile = new Tile(x, y);
            units[i] = new Unit(Team.this, num, Type.DEFAULT, tile);
        }
        System.out.println(this.toString() + " Total Units: " + num);

    }

    /** Getter methods below */
    public Unit getUnit(int i) {
        return units[i - 1]; // Return specific unit
    }

    public Unit[] getUnits() {
        return units; // Return all units
    }

    public int getNumUnits() {
        return num;
    }

    public String getName() {
        return name;
    }

    /** Overrides */
    public String toString() {
        return name;
    }
}
