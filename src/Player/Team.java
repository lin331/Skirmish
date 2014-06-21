package Player;

import java.util.Scanner;

public class Team {
	protected String name;
	protected int numUnits;
	protected Unit units[];

	public Team(String name) {
		this.name = name;
		numUnits = 0;
		System.out.println(name + ":\nAdd units: ");
		initialize();
	}
	
	private void initialize() {
		System.out.println("Team initializing");
		units = new Unit[2];
		Scanner s = new Scanner(System.in);
		for (int i = 0; i < 2; i++) {
			System.out.println("Enter x coordinate: ");
			int x = s.nextInt();
			System.out.println("Enter y coordinate: ");
			int y = s.nextInt();
			Position p = new Position(x, y);
			units[i] = new Unit(Team.this, 10, 5, p);
		}
		// s.close();
	}
	
	public void processTurn() {
		
	}
	
	public Unit[] getUnits() {
		return units;
	}
	
	public int getNumUnits() {
		return numUnits;
	}
	
	public void add() {
		numUnits += 1;
	}
	
	public String getName() {
		return name;
	}
}
