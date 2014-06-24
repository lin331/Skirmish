package Player;

import Map.Position;

public class Path {
	private Position pos[]; // Positions in path
	int current = 0; // Current index in path
	int moves = 0; // Total moves in path
	
	/** Creates position big enough for unit move */
	public Path(Unit unit) {
		int n = unit.getMove();
		pos = new Position[n];
	}
	
	/** Add position to path */
	public void add(Position p) {
		pos[moves] = p;
		moves++;
	}
	
	/** Get next position in path */
	public Position getNext() {
		return pos[++current];
	}
	
	
	/** Check if there is still more path */
	public boolean hasNext() {
		return current < moves;
	}
	
	/** Overrides */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < moves; i++) {
			sb.append(pos[i].toString());
		}
		return sb.toString();
	}
}
