package Player;

import Map.Map;
import Map.Path;
import Map.Pathtype;
import Map.Tile;

import java.util.Scanner;

public class Unit implements Comparable<Unit> {
    protected Team team; // Unit's team
    protected int num; // Unit's number
    protected Type type; // Unit type
    protected int health; // Unit's health stat
    protected int attack; // Unit's attack stat
    protected int moves; // How far unit can move
    protected Tile tile; // Unit's location
    protected Tile next;
    protected Path path; // Path to new position

    public Unit(Team team, int num, Tile tile) {
        this.team = team;
        this.num = num;
        this.type = Type.DEFAULT;
        setStats(type);
        this.tile = tile;
        this.next = null;
        path = new Path(this);
    }

    public Unit(Team team, int num, Type type, Tile tile) {
        this.team = team;
        this.num = num;
        this.type = type;
        setStats(type);
        this.tile = tile;
        this.next = null;
        path = new Path(this);
    }

    private void setStats(Type type) {
        switch (type) {
            case DEFAULT:
                this.health = 10;
                this.attack = 5;
                this.moves = 5;
                break;
            case FOOTMAN:
                this.health = 90;
                this.attack = 20;
                this.moves = 5;
                break;
            case SPEARMAN:
                this.health = 70;
                this.attack = 20;
                this.moves = 5;
                break;
            case ARCHER:
                this.health = 70;
                this.attack = 30;
                this.moves = 3;
                break;
            case CAVALRY:
                this.health = 70;
                this.attack = 25;
                this.moves = 10;
                break;
            case SUICIDAL:
                this.health = 50;
                this.attack = 30;
                this.moves = 6;
                break;
            default:
                this.health = 10;
                this.attack = 5;
                this.moves = 5;
                break;
        }
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

    /* Check method below */
    public boolean isDead() {
        return health < 1;
    }

    /* Set method below */
    public void reduceHealth(int damage) {
        health -= damage;
    }

    /* Setter methods below */
    public void setTile(Tile tile) {
        // Normal tile update
        this.tile.setUnit(null);
        this.tile = tile;
        this.tile.setUnit(this);
    }

    public void setNext(Tile tile) {
        this.next = tile;
    }

    public void setPath(Path p) {
        this.path = p;
    }

    public void clearPath() {
        this.path.clear();
    }

    /* Getter methods below */
    public Team getTeam() {
        return team;
    }

    public int getNum() {
        return num;
    }

    public Type getType() {
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

    public boolean isPathEmpty() {
        return path.isEmpty();
    }

    public Pathtype getPathtype() {
        return path.getType();
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
    public int compareTo(Unit u) {
        Pathtype pType = this.path.getType();
        Pathtype uType = u.path.getType();
        switch (pType) {
            case GOAL:
                if (uType == Pathtype.GOAL) {
                    return 0;
                }
                else {
                    return -1;
                }
            case SAFEGOAL:
                if (uType == Pathtype.SAFEGOAL) {
                    return 0;
                }
                else if (uType == Pathtype.GOAL) {
                    return 1;
                }
                else {
                    return -1;
                }
            case STANDARD:
                if (uType == Pathtype.STANDARD) {
                    return 0;
                }
                else {
                    return 1;
                }
            default:
                return 0;
        }
    }
}
