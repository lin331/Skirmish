package Game;
import java.util.Scanner;

import Map.Map;
import Player.Position;
import Player.Team;
/**
 * Fun game
 * 
 * @author Wells
 *
 * Contains main method for game
 */
public class Game {
	private Map map;
	private boolean active;
	private Team[] teams;
	
	/**
	 * Constructor for game
	 */
	private Game() {
		initialize();
	}
	
	/**
	 * Initialize game map
	 */
	private void initialize() {
		map = new Map();
		active = true;
	}
	
	private void makeTeams() {
		teams = new Team[2];
		teams[0] = new Team("Team A");
		teams[1] = new Team("Team B");
	}
	
	private void setUnits() {
		map.setUnits(teams);
	}
	
	/**
	 * Takes move commands and processes them
	 */
	private void processTurn() {
		Scanner s = new Scanner(System.in);
		Turn[] turns = new Turn[2];
		for (int i = 0; i < 2; i++) {
			turns[i] = new Turn(teams[i]);
			turns[i].getTurn();
		}
		for (int i = 0; i < 2; i++) {
			turns[i].processTurn();
		}
	}
	
	/**
	 * Prints the current game map to screen
	 */
	private void viewMap() {
		map.printMap();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.initialize();
		game.viewMap();
		game.makeTeams();
		game.setUnits();
		game.viewMap();
		while(game.active) {
			game.processTurn();
			game.viewMap();
		}
	}
}
