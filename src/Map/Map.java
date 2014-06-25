package Map;

import Player.Team;
import Player.Unit;

/** Map for game */
public class Map {
    private Tile tiles[][];
    private final int WIDTH = 9;
    private final int HEIGHT = 6;

    public Map() {
        initialize();
    }

    public void initialize() {
        tiles = new Tile[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    /** Getter methods for map size */
    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    /** Set units on map */
    public void setUnits(Team teams[]) {
        for (Team t : teams) {
            Unit[] units = t.getUnits();
            for (int i = 0; i < t.getNumUnits(); i++) {
                Position p = units[i].getPos();
                tiles[p.getY()][p.getX()].setUnit(units[i]);
            }
        }
    }

    /** Prints text map */
    public void printMap() {
        System.out.println("  0 1 2 3 4 5 6 7 8 ");
        for (int i = 0; i < HEIGHT; i++) {
            System.out.print(" ");
            for (int k = 0; k < WIDTH * 2; k++) {
                System.out.print("-");
            }
            System.out.println("-");
            System.out.print(i);
            for (int j = 0; j < WIDTH; j++) {
                System.out.print("|");
                if (tiles[i][j].isEmpty()) {
                    System.out.print(" ");
                } else {
                    System.out.print(tiles[i][j].getUnit().getNum());
                }
            }
            System.out.println("|");
        }
        for (int k = 0; k < WIDTH * 2; k++) {
            System.out.print("-");
        }
        System.out.println("-");
    }
}
