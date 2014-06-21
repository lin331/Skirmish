package Game;
import Map.Map;

/**
 * Fun game
 * 
 * @author Wells
 *
 * Contains main method for game
 */
public class Game {
	private Map map;
	private boolean isOver;
	/**
	 * Constructor for game
	 */
	public Game() {
		initialize();
	}
	
	/**
	 * Initialize game map
	 */
	public void initialize() {
		map = new Map();
		isOver = false;
	}
	
	/**
	 * Takes move commands and processes them
	 */
	public void processTurn() {
		
	}
	
	/**
	 * Prints the current game map to screen
	 */
	public void viewMap() {
		map.printMap();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.initialize();
		game.viewMap();
		while(!game.isOver) {
			game.processTurn();
			game.viewMap();
		}
	}
}
