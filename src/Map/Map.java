package Map;

import Player.Team;
import Player.Unit;

import java.util.ArrayList;

/** Map for game */
public class Map {
    private Tile[][] tiles;
    private final int WIDTH = 9;
    private final int HEIGHT = 6;

    public Map() {
        initialize();
    }

    /** Initializes tile map */
    public void initialize() {
        tiles = new Tile[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
    }

    /** Set units on map */
    public void setUnits(Team teams[]) {
        for (Tile[] tRow : tiles) {
            for (Tile tile : tRow) {
                Unit u = tile.getUnit();
                if (u != null) {
                    if (!u.getTile().equals(tile)) {
                        tile.setUnit(null);
                    }
                }
            }
        }
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (int i = 0; i < units.size(); i++) {
                Tile tile = units.get(i).getTile();
                tiles[tile.getY()][tile.getX()].setUnit(units.get(i));
            }
        }
    }

    /** Getter methods below */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
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
