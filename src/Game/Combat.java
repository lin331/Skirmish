package Game;

import Map.Map;
import Map.Pathtype;
import Map.Tile;
import Player.Team;
import Player.Unit;
import Player.Archer;
import Player.UnitType;

import java.util.ArrayList;
import java.util.ListIterator;

public class Combat {
    private Turn turn;
    private Map map;
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<AdjNode> adj = new ArrayList<AdjNode>();
    private ArrayList<Battle> battles = new ArrayList<Battle>();

    /* Public methods */
    public Combat(Map map, Turn turn, Team[] teams) {
        this.turn = turn;
        this.map = map;
        initialize(teams);
    }

    /* Check for change in battle conditions */
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
                    if (b.contains(u1, u2) && !areAdjacent(u1, u2)) {
                        // If battle has occurred and
                        // units not adjacent anymore
                        iterator.remove();
                    }
                }
            }
        }
    }

    /* Check for battles */
    public void checkBattle() {
        checkBattleChanges(turn.getTeams());
        System.out.println("Combat: checking for battles");
        for (Unit unit : units) {
            // Update unit's adjacency array
            updateAdjacent(unit);
            // Only check for battle if unit is STATIONARY/STANDARD
            if (unit.getPathtype() == Pathtype.STATIONARY ||
                    unit.getPathtype() == Pathtype.STANDARD) {
                // Check if unit has adjacent enemies
                if (hasAdjacent(unit)) {
                    Unit[] enemies = getAdjacent(unit);
                    check:
                    // Loop through adjacency array
                    for (Unit enemy : enemies) {
                        if (unit.isDead()) {
                            // Leave battle check if unit died
                            break;
                        }
                        if (enemy != null) {
                            // Check if battle has occurred
                            for (Battle b : battles) {
                                if (b.contains(unit, enemy)) {
                                    break check;
                                }
                            }
                            // Make unit/enemy STATIONARY if path is STANDARD
                            if (unit.getPathtype() == Pathtype.STANDARD) {
                                unit.clearPath();
                            }
                            if (enemy.getPathtype() == Pathtype.STANDARD) {
                                enemy.clearPath();
                            }
                            // Check for flanking conditions
                            int flanked = isFlanked(unit, enemy);
                            Battle battle = new Battle(unit, enemy,
                                     flanked);
                            battles.add(battle);
                            battle.doBattle();
                            // Update adjacency array if an unit dies
                            if (unit.isDead()) {
                                updateAdjacent(enemy);
                            }
                            if (enemy.isDead()) {
                                updateAdjacent(unit);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Combat: Checking Done");
    }
    
    /* Check if archers need to attack */
    public void checkArchers() {
        // Add attacking archers to list
        ArrayList<Archer> archers = new ArrayList<Archer>();
        for (Unit u : units) {
            if (u.getType() == UnitType.ARCHER) {
                Archer archer = (Archer) u;
                if (archer.getAttackTile() != null) {
                    archers.add(archer);
                }
            }
        }
        check:
        for (Archer a : archers) {
            if (a.isPathEmpty()) {
                Tile t = a.getAttackTile();
                Unit enemy = t.getUnit();
                if (enemy != null) {
                    if (enemy.getTeam() != a.getTeam()) {
                        for (Battle b : battles) {
                            if (b.contains(a, enemy)) {
                                break check;
                            }
                        }
                        Battle battle = new Battle(a, enemy, 0);
                        battles.add(battle);
                        battle.doBattle(a);
                    }
                }
            }
        }
    }

    /* Clears battle list */
    public void clearBattles() {
        this.battles.clear();
    }
    
    /* Private methods */
    /* Setters */
    private void setAdjacent(Unit u1, int dir, Unit u2) {
        int i = findUnit(u1);
        AdjNode node = adj.get(i);
        node.setAdjacent(dir, u2);
    }

    /* Getters */
    private Unit[] getAdjacent(Unit unit) {
        int i = findUnit(unit);
        AdjNode node = adj.get(i);
        return node.getAdjacent();
    }

    private class AdjNode {
        Unit unit;
        // Index 0 = N; 1 = W; 2 = S; 3 = E
        // Only adjacent enemies
        Unit[] adj = new Unit[4];

        private AdjNode(Unit unit) {
            this.unit = unit;
            for (Unit u : adj) {
                u = null;
            }
        }

        /* Check if this has adjacent units */
        private boolean hasAdjacent() {
            for (Unit u : adj) {
                if (u != null) {
                    return true;
                }
            }
            return false;
        }
        
        /* Check if unit is adjacent */
        private boolean contains(Unit unit) {
            for (Unit u : adj) {
                if (unit == u) {
                    return true;
                }
            }
            return false;
        }

        /* Setters */
        private void setAdjacent(int dir, Unit unit) {
            adj[dir] = unit;
        }

        /* Getters */
        private Unit[] getAdjacent() {
            return this.adj;
        }

        private Unit getUnit() {
            return this.unit;
        }

        /* Clears adjacency array */
        private void clear() {
            for (Unit u : adj) {
                u = null;
            }
        }
        
        /* Check if enemy is flanking */
        private boolean isFlankedBy(Unit enemy) {
            for (Unit u : adj) {
                if (u != null && u != enemy) {
                    return true;
                }
            }
            return false;
        }
        
        /* Test print */
        private void print() {
            System.out.println(unit);
            for (Unit u : adj) {
                System.out.print(u + " ");
            }
            System.out.println("");
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
            if (obj instanceof Unit) {
                Unit u = (Unit) obj;
                return this.unit == u;
            }
            AdjNode node = (AdjNode) obj;
            return this.unit == node.getUnit();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(adj.toString());
            return unit.toString();
        }
    }

    /* Initialize combat object */
    private void initialize(Team[] teams) {
        System.out.println("Initializing combat");
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (Unit u : units) {
                adj.add(new AdjNode(u));
                this.units.add(u);
            }
        }
    }

    /* Find index for specified adjacent unit */
    private int findUnit(Unit unit) {
        for (AdjNode node : adj) {
            if (node.getUnit() == unit) {
                return adj.indexOf(node);
            }
        }
        return -1;
    }

    /* Check if two units are adjacent */
    private boolean areAdjacent(Unit u1, Unit u2) {
        AdjNode node = adj.get(findUnit(u1));
        return node.contains(u2);
    }

    /* Check if there are adjacent enemy units */
    private boolean hasAdjacent(Unit unit) {
        AdjNode node = adj.get(findUnit(unit));
        return node.hasAdjacent();
    }
    
    /* Clear adjacency array for specified unit */
    private void clearAdj(Unit unit) {
        AdjNode node = adj.get(findUnit(unit));
        node.clear();
    }
    
    /* Checks for adjacent enemies and */
    /* adds them to adjacent array */
    private void updateAdjacent(Unit unit) {
        Tile tile = unit.getTile();
        if (tile == null) {
            clearAdj(unit);
            return;
        }
        for (int dir = 0; dir < 4; dir++) {
            Tile t = map.getAdjacentTile(dir, tile);
            if (t != null) {
                Unit u = t.getUnit();
                if (u != null) {
                    if (unit.getTeam() != u.getTeam()) {
                        setAdjacent(unit, dir, u);
                    }
                }
                else {
                    setAdjacent(unit, dir, null);
                }
            }
        }
        /*AdjNode node = adj.get(findUnit(unit));
        node.print();*/
    }
    
    /* Checks if unit is flanked by enemy */
    private int isFlanked(Unit unit, Unit enemy) {
        int n;
        AdjNode n1 = adj.get(findUnit(unit));
        AdjNode n2 = adj.get(findUnit(enemy));
        if (n1.isFlankedBy(enemy)) {
            n = n2.isFlankedBy(unit) ? 3 : 1;
        }
        else {
            n = n2.isFlankedBy(unit) ? 2 : 0;
        }
        // 0 = neither is flanked
        // 1 = unit is flanked
        // 2 = enemy is flanked
        // 3 = both are flanked
        return n;
    }
}
