package Player;

import Map.Tile;

public class Archer extends Unit {
    private Tile attackTile;
    
    public Archer(Team team, int num, Tile tile) {
        super(team, num, tile);
        this.attackTile = null;
    }

    public void setAttackTile(Tile tile) {
        this.attackTile = tile;
    }
    
    public Tile getAttackTile() {
        return this.attackTile;
    }
    
    private boolean checkValid(Tile tile) {
        return false;
    }
}
