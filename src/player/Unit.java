package player;

import static output.Output.Print.*;

import map.Path;
import map.Pathtype;
import map.Tile;

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
    protected boolean delay; // Has delay been set

    /*
     *  Public methods 
    */
    public Unit(Team team, int num, UnitType type, Tile tile) {
        this.team = team;
        this.num = num;
        this.type = type;
        this.health = type.getHealth();
        this.attack = type.getAttack();
        this.moves = type.getMove();
        this.tile = tile;
        this.next = null;
        this.path = new Path(this);
    }

    /* Cloning constructor */
    public Unit(Unit u) {
        this.team = u.getTeam();
        this.num = u.getNum();
        this.type = u.getType();
        this.health = u.getHealth();
        this.attack = u.getAttack();
        this.tile = u.getTile();
        this.next = u.getNext();
        this.path = new Path(this);
        for (Tile t : u.getPath().getTiles()) {
            path.add(t);
        }
    }

    /* Check if unit is blocking this */
    public boolean isBlockedBy(Unit u) {
        if (u.getNext() == this.next) {
            if (u.getPathtype() == Pathtype.STATIONARY) {
                return true;
            }
        }
        return false;
    }

    /* Setters */
    public void setType(UnitType type) {
        this.type = type;
    }

    public void reduceHealth(int damage) {
        this.health = this.health - damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void setTile(Tile tile) {
        if (this.tile.getUnit() == this) {
            this.tile.setUnit(null);
        }
        this.tile = tile;
        this.tile.setUnit(this);
    }

    public void setNext(Tile tile) {
        this.next = tile;
    }

    public void setPath(Path p) throws Exception {
        if (!this.path.isEmpty()) {
            throw new Exception("Path not empty");
        }
        for (Tile t : p.getTiles()) {
            this.path.add(t);
        }
    }

    public void setDelay(int d) {
        this.path.setDelay(d);
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

    public boolean isDead() {
        return health < 1;
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

    public boolean hasPathDelay() {
        return path.getDelay() > 0;
    }
    
    public int getPathDelay() {
        return path.getDelay();
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
        printf("log.txt", "%s %d\tHealth: %d\tTile: %s\n", team, num, health,
                tile);
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
