package Game;
import java.util.Scanner;

import Graphics.Gui;
import Map.Map;
import Map.Position;
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
	private MoveList moves;
	
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
		active = false;
		makeTeams();
		moves = new MoveList();
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
	private void getTurn() {
		Scanner s = new Scanner(System.in);
	}
	
	
	private void processTurn() {
		
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
	/*	game.viewMap();
		game.setUnits();
		game.viewMap();
		while(game.active) {
			game.getTurn();
			game.processTurn();
			game.viewMap();
		}
		Gui gui = new Gui();
	*/
	}
}
