package map;

import static output.Output.Print.*;
import java.util.ArrayList;

import player.Team;
import player.Unit;

/* Map for game */
public class Map {
    private Tile[][] tiles;
    private final int WIDTH = 9;
    private final int HEIGHT = 6;

    /* Public methods */
    public Map() {
        initialize();
    }

    /* Set units on map */
    public void setUnits(Team teams[]) {
        printf("log.txt", "Map: setting units\n");
        System.out.println("Map: settings units");
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (int i = 0; i < units.size(); i++) {
                Tile tile = units.get(i).getTile();
                tiles[tile.getY()][tile.getX()].setUnit(units.get(i));
            }
        }
    }

    /* Getter for adjacent tiles */
    public Tile getAdjacentTile(int dir, Tile tile) {
        switch (dir) {
            case 0:
                if (tile.getY() == 0) {
                    return null;
                }
                return tiles[tile.getY() - 1][tile.getX()];
            case 1:
                if (tile.getX() == WIDTH - 1) {
                    return null;
                }
                return tiles[tile.getY()][tile.getX() + 1];
            case 2:
                if (tile.getY() == HEIGHT - 1) {
                    return null;
                }
                return tiles[tile.getY() + 1][tile.getX()];
            case 3:
                if (tile.getX() == 0) {
                    return null;
                }
                return tiles[tile.getY()][tile.getX() - 1];
            default:
                // System.out.println("Invalid direction");
                return null;
        }
    }

    /* Prints text map */
    public void printMap() {
        printf("log.txt", "  0  1  2  3  4  5  6  7  8 \n");
        System.out.println("  0  1  2  3  4  5  6  7  8 ");
        for (int i = 0; i < HEIGHT; i++) {
            printf("log.txt", "-");
            System.out.print("-");
            for (int k = 0; k < WIDTH; k++) {
                printf("log.txt", "---");
                System.out.print("---");
            }
            printf("log.txt", "\n%d", i);
            System.out.println();
            System.out.print(i);
            for (int j = 0; j < WIDTH; j++) {
                printf("log.txt", "|");
                System.out.print("|");
                if (tiles[i][j].isEmpty()) {
                    printf("log.txt", "  ");
                    System.out.print("  ");
                }
                else {
                    printf("log.txt", "%s", tiles[i][j].getUnit());
                    System.out.print(tiles[i][j].getUnit());
                }
            }
            printf("log.txt", "|\n");
            System.out.println("|");
        }
        printf("log.txt", "-");
        System.out.print("-");
        for (int k = 0; k < WIDTH; k++) {
            printf("log.txt", "---");
            System.out.print("---");
        }
        printf("log.txt", "\n");
        System.out.println();
    }

    /* Getters */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    /* Private methods */
    /* Initializes tile map */
    private void initialize() {
        tiles = new Tile[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
    }
}
