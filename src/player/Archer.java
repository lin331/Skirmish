package player;

import java.util.ArrayList;

import map.Tile;

public class Archer extends Unit {
    private Tile attackTile;
    private int rangedAttack = 30;
    private int maxAttack;
    private int numAttacks;
    private boolean attack;
    
    /*
     *  Public methods 
    */
    public Archer(Team team, int num, Tile tile) {
        super(team, num, UnitType.ARCHER, tile);
        this.type = UnitType.ARCHER;
        this.attackTile = null;
        this.maxAttack = UnitType.ARCHER.getMove() + 2;
        this.numAttacks = maxAttack;
        this.attack = false;
    }

    public int getRemaining() {
        return this.numAttacks;
    }
    
    public void reduceRemaining() {
        this.numAttacks--;
    }
    
    public void resetRemaining() {
        this.numAttacks = this.maxAttack;
    }
    
    public boolean hasAttack() {
        return attack;
    }

    public void setAttack(boolean flag) {
        this.attack = flag;
    }
    
    public Tile setAttackTile(Tile tile) {
        if (checkValid(tile)) {
            this.attackTile = tile;
            return tile;
        }
        return null;
    }
    
    public Tile getAttackTile() {
        return this.attackTile;
    }
    
    public int getRangedAttack() {
        return this.rangedAttack;
    }
    
    private boolean checkValid(Tile tile) {
        ArrayList<Tile> path = getPath().getTiles();
        int distance = path.get(path.size()-1).distanceFrom(tile);
        return distance > 1 && distance <= 5;
    }
}
