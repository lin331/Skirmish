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
    public Unit checkAdjacent(Unit u, Tile tile) {
        Team t = u.getTeam();
        Tile temp;
        Unit unit;
        temp = getN(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null && unit.getTeam() != t) {
                return unit;
            }
        }
        temp = getE(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null && unit.getTeam() != t) {
                return unit;
            }
        }
        temp = getS(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null && unit.getTeam() != t) {
                return unit;
            }
        }
        temp = getW(tile);
        if (temp != null) {
            unit = temp.getUnit();
            if (unit != null && unit.getTeam() != t) {
                return unit;
            }
        }
        return null;
    }

    /* Check if two units are adjacent */
    public boolean isAdjacent(Unit u1, Unit u2) {
        Tile tile = u1.getTile();
        Tile temp = getN(tile);
        if (temp != null) {
            if (u2.equals(temp.getUnit())) {
                return true;
            }            
        }
        temp = getE(tile);
        if (temp != null) {
            if (u2.equals(temp.getUnit())) {
                return true;
            }            
        }
        temp = getS(tile);
        if (temp != null) {
            if (u2.equals(temp.getUnit())) {
                return true;
            }            
        }
        temp = getW(tile);
        if (temp != null) {
            if (u2.equals(temp.getUnit())) {
                return true;
            }            
        } 
        return false;
    }
    
    /* Check if unit is blocked */
    /* Problably useless now */
    public boolean checkBlocked(Unit unit) {
        if (unit.getPath().getType() == Pathtype.STATIONARY) {
            return true;
        }
        return false;
    }
    
    /* Check if battle removal is needed */
    public void checkBattleChanges(Team[] teams) {
        if (battles.isEmpty()) {
            return;
        }
        ArrayList<Unit> aUnits = teams[0].getUnits();
        ArrayList<Unit> bUnits = teams[1].getUnits();
        ListIterator<Battle> iterator = battles.listIterator();
        while (iterator.hasNext()) {
            Battle b = iterator.next();
            for (Unit u1 : aUnits) {
                for (Unit u2 : bUnits) {
                    if (b.has(u1, u2) && !isAdjacent(u1,u2)) {
                        // If battle has occured and units not adjacent anymore
                        iterator.remove();                        
                    }
                }
            }
        }
    }

    /* Check battle conditions */
    public void checkBattle(Team team) {
        System.out.println("Checking");
        ArrayList<Unit> units = team.getUnits();
        for (int i = 0; i < units.size(); i++) {
            Unit ally = units.get(i);
            Tile tile = ally.getTile();
            Unit enemy = checkAdjacent(ally, tile);
            boolean flag = false; // Flag for already battled
            if (enemy != null) {
                // Get types to check battle mechanics and movement
                Pathtype aType = ally.getPath().getType();
                Pathtype eType = enemy.getPath().getType();
                // Check if both units using goal move
                if (aType == Pathtype.GOAL || aType == Pathtype.SAFEGOAL) {
                    System.out.println("Goal type");
                    switch (eType) {
                        case GOAL:
                        case SAFEGOAL:
                            if (checkBlocked(ally)) {
                                System.out.println(ally + " blocking " + enemy);
                                // TODO: Temporary function
                                enemy.getPath().clear();
                            }
                            System.out.println("No battle");
                            continue;
                        default:
                            break;
                    }
                }
                // Check if battle has occurred already
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
                    switch (aType) {
                        case STANDARD:
                            ally.getPath().clear();
                            break;
                        case GOAL:
                            if (ally.isBlockedBy(enemy)) {
                                System.out.println(enemy + " blocking "
                                        + ally);
                                // TODO: Temporary function
                                ally.getPath().clear();
                            }
                            break;
                        case SAFEGOAL:
                            if (ally.isBlockedBy(enemy)) {
                                System.out.println(enemy + " blocking "
                                        + ally);
                                // TODO: Temporary function
                                ally.getPath().clear();
                            }
                            break;
                        default:
                            break;
                    }
                    switch (eType) {
                        case STANDARD:
                            enemy.getPath().clear();
                            break;
                        case GOAL:
                            if (enemy.isBlockedBy(ally)) {
                                System.out.println(ally + " blocking "
                                         + enemy);
                                // TODO: Temporary function
                                enemy.getPath().clear();
                            }
                            break;
                        case SAFEGOAL:
                            if (enemy.isBlockedBy(ally)) {
                                System.out.println(ally + " blocking "
                                         + enemy);
                                // TODO: Temporary function
                                enemy.getPath().clear();
                            }
                            break;
                        default:
                            break;
                    }
                    battles.add(b);
                    b.doBattle();
                }
            }
        }
    }

    /* Getters for adjacent tiles */
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

    /* Getters below */
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
        System.out.println("  0  1  2  3  4  5  6  7  8 ");
        for (int i = 0; i < HEIGHT; i++) {
            System.out.print("-");
            for (int k = 0; k < WIDTH; k++) {
                System.out.print("---");
            }
            System.out.println();
            System.out.print(i);
            for (int j = 0; j < WIDTH; j++) {
                System.out.print("|");
                if (tiles[i][j].isEmpty()) {
                    System.out.print("  ");
                }
                else {
                    System.out.print(tiles[i][j].getUnit());
                }
            }
            System.out.println("|");
        }
        System.out.print("-");
        for (int k = 0; k < WIDTH; k++) {
            System.out.print("---");
        }
        System.out.println();
    }
}
