package Game;

import Player.Team;
import Player.Unit;
import Player.Position;

public class Move {
	private Team team; // unit's team
	private Unit unit; // unit to be moved
	private Position pos; // pos to be moved to
	private int dist; // distance from unit's current pos
	private int delay; // 0 if current turn, else delayed
	
	public Move(Team team, int unit, Position pos) {
		this.team = team;
		this.unit = team.getUnit(unit);
		this.pos = pos;
		dist = this.unit.getPos().dist(pos);
	}
	
	public int getDist() {
		return dist;
	}
	
	public void process() {
		unit.setPos(pos);
	}
	
	public void print() {
		System.out.format("%s: %s - %s", team.toString(), unit.toString(),
						  pos.toString());
	}
}
