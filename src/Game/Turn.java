package Game;

import java.util.Scanner;

import Player.Position;
import Player.Team;

public class Turn {
	private Team team;
	private Move moves[];
	
	public Turn(Team team) {
		this.team = team;
		moves = new Move[2];
		
	}
	
	public void getTurn() {
		moves[0] = setMoves(team);
		moves[1] = setMoves(team);
		
	}
	
	public void processTurn() {
		
	}
	
	private Move setMoves(Team t) {
		Scanner s = new Scanner(System.in);
		System.out.println(t.getName());
		System.out.println("Make your move <Unit x y>");
		int u = s.nextInt();
		int x = s.nextInt();
		int y = s.nextInt();
		Position p = new Position(x,y);
		return new Move(t,u,p);
		// s.close();
	}
}
