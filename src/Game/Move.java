package Game;

import Player.Team;
import Player.Unit;
import Player.Position;

public class Move {
	private Team team;
	private int unit;
	private Position pos;
	
	public Move(Team team, int unit, Position pos) {
		this.team = team;
		this.unit = unit;
		this.pos = pos;
	}
}
