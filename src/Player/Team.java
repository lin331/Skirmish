package Player;

import Map.Position;

import java.util.Scanner;

import Map.Position;

public class Team {
	private final int MAXUNITS = 12;
	private String name;
	private int numUnits;
	private Unit units[];

	public Team(String name) {
		this.name = name;
		numUnits = 0;
		units = new Unit[MAXUNITS];
	}
	
	public void processTurn() {
		
	}
	
	/** Add units to team */
	public void addUnits() {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int health = 10;
		int attack = 5;
		int moves = 3;
		for (int i = 0; i < MAXUNITS; i++) {
			System.out.println("Unit #" + (i+1) + ":");
			System.out.println("Enter x coordinate: ");
			String string = s.next();
			if (string.equals("end")) {
				break;
			}
			int x = Integer.parseInt(string);
			System.out.println("Enter y coordinate: ");
			int y = s.nextInt();
			numUnits += 1;
			Position pos = new Position(x, y);
			units[i] = new Unit(Team.this, numUnits, health, attack, moves, pos);
		}
		System.out.println(this.toString() + " Total Units: " + numUnits);
		
	}
	
	/** Getter methods below */
	// Return specific unit
	public Unit getUnit(int i) {
		return units[i-1];
	}
	
	// Return all units
	public Unit[] getUnits() {
		return units;
	}
	
	public int getNumUnits() {
		return numUnits;
	}
	
	public String getName() {
		return name;
	}
	
	/** Overrides */
	public String toString() {
		return name;
	}
}
