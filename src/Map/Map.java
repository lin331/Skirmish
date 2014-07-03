package Map;

import Game.Battle;
import Player.Team;
import Player.Unit;

import java.util.ArrayList;
import java.util.ListIterator;

/* Map for game */
public class Map {
    private Tile[][] tiles;
    private final int WIDTH = 9;
    private final int HEIGHT = 6;
    ArrayList<Battle> battles = new ArrayList<Battle>();

    public Map() {
        initialize();
    }

    /* Initializes tile map */
    public void initialize() {
        tiles = new Tile[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
    }

    /* Set units on map */
    public void setUnits(Team teams[]) {
        System.out.println("Map: settings units");
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (int i = 0; i < units.size(); i++) {
                Tile tile = units.get(i).getTile();
                tiles[tile.getY()][tile.getX()].setUnit(units.get(i));
            }
        }
    }

    /* Check for enemy on adjacent tiles */
    public Unit checkAdjacent(Tile tile) {
        Tile temp;
        Unit unit;
        temp = getN(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null) {
                return unit;
            }
        }
        temp = getE(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null) {
                return unit;
            }
        }
        temp = getS(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null) {
                return unit;
            }
        }
        temp = getW(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null) {
                return unit;
            }
        }
        return null;
    }

    /* Check if unit is blocked */
    public boolean checkBlocked(Unit unit) {
        return false;
    }
    
    /* Check battle conditions */
    public void checkBattle(Team team) {
        System.out.println("Checking");
        ArrayList<Unit> units = team.getUnits();
        for (int i = 0; i < units.size(); i++) {
            Unit ally = units.get(i);
            Tile tile = ally.getTile();
            Unit enemy = checkAdjacent(tile);
            boolean flag = false; // Flag for already battled
            if (enemy != null) {
                // Get types to check battle mechanics and movement
                Pathtype aType = ally.getPath().getType();
                Pathtype eType = enemy.getPath().getType();
                // If goal movement
                if (aType == Pathtype.GOAL || aType == Pathtype.SAFEGOAL) {
                    switch (eType) {
                        case GOAL:
                        case SAFEGOAL:
                            System.out.println("Battle does not occurs");
                            continue;
                        default:
                            System.out.println("Battle occurs");
                    }
                }
                // Iterator for battles that already happened
                ListIterator<Battle> iterator = battles.listIterator();
                while (iterator.hasNext()) {
                    Battle b = iterator.next();
                    if (b.has(ally, enemy)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    // Battle occurs
                    Battle b = new Battle(ally, enemy);
                    if (aType == Pathtype.STANDARD) {
                        ally.getPath().clear();
                    }
                    if (eType == Pathtype.STANDARD) {
                        enemy.getPath().clear();
                    }
                    battles.add(b);
                    b.attack();
                }
            }
        }
    }

    /* Getter methods for adjacent tiles */
    public Tile getN(Tile tile) {
        if (tile.getY() == 0) {
            return null;
        }
        return tiles[tile.getY() - 1][tile.getX()];
    }

    public Tile getE(Tile tile) {
        if (tile.getX() == WIDTH - 1) {
            return null;
        }
        return tiles[tile.getY()][tile.getX() + 1];
    }

    public Tile getS(Tile tile) {
        if (tile.getY() == HEIGHT - 1) {
            return null;
        }
        return tiles[tile.getY() + 1][tile.getX()];
    }

    public Tile getW(Tile tile) {
        if (tile.getX() == 0) {
            return null;
        }
        return tiles[tile.getY()][tile.getX() - 1];
    }

    /* Getter methods below */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    /* Prints text map */
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
                }
                else {
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
