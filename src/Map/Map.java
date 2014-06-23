package Map;

import Player.Team;
import Player.Unit;

/**
 * 
 * @author Wells
 *
 * Game map
 */
public class Map {
	private Tile tiles[][];
	private final int width = 9;
	private final int height = 6;
	
	public Map() {
		initialize();
	}
	
	public void initialize() {
		tiles = new Tile[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tiles[i][j] = new Tile();
			}
		}
	}
	
	public int getWidth() {
	    return width;
	}
	
	public int getHeight() {
	    return height;
	}
	
	public void setUnits(Team teams[]) {
		for (Team t : teams) {
			Unit[] units = t.getUnits();
			for (Unit u : units) {
				Position p = u.getPos();
				tiles[p.getY()][p.getX()].setUnit(u);
			}
		}
	}
	
	public void printMap() {
		System.out.println("  0 1 2 3 4 5 6 7 8 ");
		for (int i = 0; i < height; i++) {
			System.out.print(" ");
			for (int k = 0; k < width*2; k++) {
				System.out.print("-");
			}
			System.out.println("-");
			System.out.print(i);
			for (int j = 0; j < width; j++) {
				System.out.print("|");
				if (tiles[i][j].isEmpty()) {
					System.out.print(" ");
				}
				else {
					System.out.print(tiles[i][j].getUnit().getNum());
				}
			}
			System.out.println("|");
		}
		for (int k = 0; k < width*2; k++) {
			System.out.print("-");
		}
		System.out.println("-");
	}
}
