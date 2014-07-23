package map;

import player.Unit;

/* Tiles for map */
public class Tile {
    private Unit unit;
    private int x;
    private int y;

    /* Public methods */
    public Tile(int x, int y) {
        unit = null;
        this.x = x;
        this.y = y;
    }

    /* Check if tile is empty */
    public boolean isEmpty() {
        if (unit != null) {
            return false;
        }
        return true;
    }

    /* Check if tile is adjacent */
    public boolean isAdjacent(Tile t) {
        if ((Math.abs(this.x - t.x) == 1) && (Math.abs(this.y - t.y) == 0)) {
            return true;
        }
        else if ((Math.abs(this.y - t.y) == 1)
                && (Math.abs(this.x - t.x) == 0)) {
            return true;
        }
        return false;
    }

    /* Setters */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /* Getters */
    public Unit getUnit() {
        return this.unit;
    }
    
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public int distanceFrom(Tile tile) {
        return Math.abs(this.x - tile.getX()) + Math.abs(this.y - tile.getY()); 
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
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Tile t = (Tile) obj;
        if (t == null || this.x != t.getX() || this.y != t.getY()) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "(" + Integer.toString(this.x) + ", " + Integer.toString(this.y)
                + ")";
    }
}
