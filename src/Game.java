
public class Game {
	private Map map;
	
	public void initialize() {
		map = new Map();
	}
	
	public Game() {
		initialize();
	}
	
	public void viewMap() {
		map.printMap();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.initialize();
		game.viewMap();
	}
}
