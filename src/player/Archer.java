package player;

import map.Tile;

public class Archer extends Unit {
    private Tile attackTile;
    private int rangedAttack = 30;
    
    public Archer(Team team, int num, Tile tile) {
        super(team, num, tile);
        this.type = UnitType.ARCHER;
        this.attackTile = null;
    }

    public void setAttackTile(Tile tile) {
        if (checkValid(tile)) {
            this.attackTile = tile;
        }
    }
    
    public Tile getAttackTile() {
        return this.attackTile;
    }
    
    public int getRangedAttack() {
        return this.rangedAttack;
    }
    
    private boolean checkValid(Tile tile) {
        /*if (statement) {
            System.out.println("Invalid tile: Not in range");            
        }*/
        return true;
    }
}
