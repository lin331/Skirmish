
public class Map {
	private Tile tiles[][];
	
	public Map() {
		initialize();
	}
	
	public void initialize() {
		tiles = new Tile[10][12];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 12; j++) {
				tiles[i][j] = new Tile();
			}
		}
	}
	
	public void printMap() {
		String hor = "-----------------------------------------";
		String ver = "| | | | | | | | | | | | | | | | | | | | |";
		for (int i = 0; i < 12; i++) {
			System.out.println(hor);
			System.out.println(ver);
		}
		System.out.println(hor);
	}
}
