/**
 * Fun game
 * 
 * @author Wells
 *
 * Contains main method for game
 */
public class Game {
	private Map map;

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
	}
}
