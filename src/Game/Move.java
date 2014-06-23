package Game;

import Player.Team;
import Player.Unit;
import Player.Position;

public class Move {
	private Team team;
	private Unit unit;
	private Position pos;
	private int dist;
	private int delay;
	
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
		System.out.format("%s: %s - %s", team.toString(), unit.toString(), pos.toString());
	}
}
