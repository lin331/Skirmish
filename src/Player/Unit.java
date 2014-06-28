package Player;

import java.util.Scanner;

import Map.Map;
import Map.Path;
import Map.Position;

public class Unit {
    protected Team team; // Unit's team
    protected int num; // Unit's number
    protected Type type; // Unit type
    protected int health; // Unit's health stat
    protected int attack; // Unit's attack stat
    protected int moves; // How far unit can move
    protected Position pos; // Unit's location
    protected Path path; // Path to new position
    protected boolean dead; // Dead?

    public Unit(Team team, int num, Position pos) {
        this.team = team;
        this.num = num;
        type = Type.DEFAULT;
        this.health = 10;
        this.attack = 5;
        this.moves = 5;
        this.pos = pos;
        path = new Path(this);
        dead = false;
    }

    public Unit(Team team, int num, int health, int attack, int moves,
            Position pos) {
        this.team = team;
        this.num = num;
        this.type = Type.DEFAULT;
        this.health = health;
        this.attack = attack;
        this.moves = moves;
        this.pos = pos;
        path = new Path(this);
        dead = false;
    }

    public Unit(Team team, Type type, int num, int health, int attack,
            int moves, Position pos) {
        this.team = team;
        this.num = num;
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.moves = moves;
        this.pos = pos;
        path = new Path(this);
        dead = false;
    }

    /** Add to path */
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
            path.add(new Position(x, y));
        }
    }

    /** Check method below */
    public void checkDead() {
        if (health < 1) {
            dead = true;
        }
    }

    /** Set method below */
    public void reduceHealth(int damage) {
        health -= damage;
        checkDead();
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    /** Getter methods below */
    public Team getTeam() {
        return team;
    }

    public int getNum() {
        return num;
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

    public Position getPos() {
        return pos;
    }

    public Path getPath() {
        return path;
    }

    /** Overrides */
    public boolean equals(Unit u) {
        if (team == u.getTeam()) {
            return num == u.getNum();
        }
        return false;
    }

    public String toString() {
        return Integer.toString(num);
    }
}
