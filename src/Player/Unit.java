package Player;

import Map.Map;
import Map.Path;
import Map.Pathtype;
import Map.Tile;

import java.util.Scanner;

public class Unit implements Comparable<Unit> {
    protected Team team; // Unit's team
    protected int num; // Unit's number
    protected UnitType type; // Unit type
    protected int health; // Unit's health stat
    protected int attack; // Unit's attack stat
    protected int moves; // How far unit can move
    protected Tile tile; // Unit's current tile
    protected Tile next; // Unit's next tile
    protected Path path; // Path to new tile

    /* Public methods */
    public Unit(Team team, int num, Tile tile) {
        this.team = team;
        this.num = num;
        this.type = UnitType.DEFAULT;
        this.health = type.getHealth();
        this.attack = type.getAttack();
        this.moves = type.getMove();
        this.tile = tile;
        this.next = null;
        path = new Path(this);
    }

    public Unit(Team team, int num, UnitType type, Tile tile) {
        this.team = team;
        this.num = num;
        this.type = type;
        this.health = type.getHealth();
        this.attack = type.getAttack();
        this.moves = type.getMove();
        this.tile = tile;
        this.next = null;
        path = new Path(this);
    }

    /* Check if unit is blocking this */
    // TODO: Needs testing
    public boolean isBlockedBy(Unit u) {
        if (u.getNext() == this.next) {
            if (u.getPathtype() == Pathtype.STATIONARY) {
                return true;
            }
        }
        return false;
    }

    /* Check if unit is dead */
    public boolean isDead() {
        return health < 1;
    }

    /* Setters */
    public void reduceHealth(int damage) {
        this.health = this.health - damage;
    }

    public void setTile(Tile tile) {
        if (tile.getUnit() != this) {
            this.tile.setUnit(null);
        }
        this.tile = tile;
        this.tile.setUnit(this);
    }

    public void setNext(Tile tile) {
        this.next = tile;
    }

    public void setPath(Path p) {
        this.path = p;
    }

    public void decPathDelay() {
        path.decDelay();
    }

    public void clearPath() {
        this.path.clear();
        this.next = null;
    }

    public void setDead() {
        this.tile.setUnit(null);
        this.tile = null;
        this.next = null;
        this.path.clear();
        this.path.setType(Pathtype.DEAD);
    }

    /* Test purpose-only setters */
    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    /* Getters below */
    public Team getTeam() {
        return team;
    }

    public int getNum() {
        return num;
    }

    public UnitType getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getMove() {
        return moves;
    }

    public Tile getTile() {
        return tile;
    }

    public Tile getNext() {
        return next;
    }

    public Path getPath() {
        return path;
    }

    public int getPathSize() {
        return this.path.getSize();
    }

    public void setPathtype(Pathtype type) {
        this.path.setType(type);
    }

    public Pathtype getPathtype() {
        return path.getType();
    }

    public boolean isPathEmpty() {
        return path.isEmpty();
    }

    /* Test print */
    public void printStats() {
        System.out.println(team + " " + num + "\tHealth: " + health
                + "\tTile: " + tile);
    }

    /* Console input */
    /* Add to path */
    public void addPath(Map map) {
        @SuppressWarnings("resource")
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < moves; i++) {
            System.out.println("Moves left: " + (moves - i));
            System.out.println("Enter x coordinate: ");
            String string = s.next();
            if (string.equals("end")) {
                break;
            }
            int x = Integer.parseInt(string);
            while (x < 0 || x >= map.getWidth()) {
                System.out.println("x error: Not on map");
                System.out.println("Enter x coordinate: ");
                x = s.nextInt();
            }
            System.out.println("Enter y coordinate: ");
            int y = s.nextInt();
            while (y < 0 || y >= map.getWidth()) {
                System.out.println("y error: Not on map");
                System.out.println("Enter y coordinate: ");
                y = s.nextInt();
            }
            path.add(new Tile(x, y));
        }
    }

    /* Overrides */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Unit u = (Unit) obj;
        if (team == u.getTeam()) {
            return num == u.getNum();
        }
        return false;
    }

    @Override
    public String toString() {
        return team + Integer.toString(num);
    }

    @Override
    public int compareTo(Unit unit) {
        return this.getPathtype().compareTo(unit.getPathtype());
    }
}
