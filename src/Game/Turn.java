package Game;

import java.util.Scanner;

import Player.Position;
import Player.Team;

public class Turn {
	private Move moves[];
	
	public Turn() {
		
	}
	
	public void process() {
		
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
