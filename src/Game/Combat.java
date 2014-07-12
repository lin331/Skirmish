package Game;

import java.util.ArrayList;
import java.util.ListIterator;

import Map.Map;
import Map.Tile;
import Player.Team;
import Player.Unit;

public class Combat {
    private Turn turn;
    private Map map;
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<AdjNode> adj = new ArrayList<AdjNode>();
    private ArrayList<Battle> battles = new ArrayList<Battle>();

    private class AdjNode {
        Unit unit;
        // Index 0 = N; 1 = W; 2 = S; 3 = E
        // Only adjacent enemies
        Unit[] adj = new Unit[4];
        int[] cycle = new int[4];

        public AdjNode(Unit unit) {
            this.unit = unit;
            for (Unit u : adj) {
                u = null;
            }
            for (int i : cycle) {
                i = 0;
            }
        }

        public void setAdjacent(int dir, Unit unit) {
            adj[dir] = unit;
            cycle[dir] = turn.getCycle();
        }

        public boolean hasAdjacent() {
            for (Unit u : adj) {
                if (u != null) {
                    return true;
                }
            }
            return false;
        }

        public boolean has(Unit unit) {
            for (Unit u : adj) {
                if (this.unit == unit) {
                    return true;
                }
            }
            return false;
        }

        public Unit[] getAdjacent() {
            return this.adj;
        }

        public Unit getUnit() {
            return this.unit;
        }
        
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
        
        public void print() {
            for (Unit u : adj) {
                System.out.println(u);
            }
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(adj.toString()); 
            return unit.toString();
        }
    }

    public Combat(Turn turn, Team[] teams) {
        this.turn = turn;
        this.map = turn.getMap();
        initialize(teams);
    }

    public void initialize(Team[] teams) {
        System.out.println("Initializing combat");
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (Unit u : units) {
                adj.add(new AdjNode(u));
                this.units.add(u);
            }
        }
    }

    public int findUnit(Unit unit) {
        for (AdjNode node : adj) {
            if (node.getUnit() == unit) {
                return adj.indexOf(node);
            }
        }
        return -1;
    }
    
    public void setAdjacent(Unit u1, int dir, Unit u2) {
        int i = findUnit(u1);
        AdjNode node = adj.get(i);
        node.setAdjacent(dir, u2);
    }

    public boolean hasAdjacent(Unit unit) {
        int i = findUnit(unit);
        AdjNode node = adj.get(i);
        return node.hasAdjacent();
    }

    public Unit[] getAdjacent(Unit unit) {
        int i = findUnit(unit);
        AdjNode node = adj.get(i);
        return node.getAdjacent();
    }

    public boolean areAdjacent(Unit u1, Unit u2) {
        int i = findUnit(u1);
        AdjNode node = adj.get(i);
        return node.has(u2);
    }

    public void checkAdjacent(Unit unit) {
        Tile tile = unit.getTile();
        for (int dir = 0; dir < 4; dir++) {
            Tile t = map.getAdjacentTile(dir, tile);
            if (t != null) {
                Unit u = t.getUnit();
                if (u != null) {
                    if (unit.getTeam() != u.getTeam()) {
                        setAdjacent(unit, dir, u);
                    }
                }
            }
        }        
    }
    
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
                    if (b.has(u1, u2) && !areAdjacent(u1, u2)) {
                        // If battle has occurred and
                        // units not adjacent anymore
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void checkBattle() {
        System.out.println("Combat: checking for battles");
        for (Unit unit : units) {
            checkAdjacent(unit);
            if (hasAdjacent(unit)) {
                Unit[] enemies = getAdjacent(unit);
                check:
                    for (Unit enemy : enemies) {
                        if (enemy != null) {
                            // System.out.println("Adjacent enemy");
                            for (Battle b : battles) {
                                if (b.has(unit, enemy)) {
                                   // System.out.println("\tHas battled");
                                   break check;
                                }
                            }
                            // System.out.println("\tNot battled");
                            Battle battle = new Battle(unit, enemy);
                            battles.add(battle);
                            battle.doBattle();
                            unit.clearPath();
                            enemy.clearPath();
                        }
                    }
            }
        }
        turn.incCycle();
        System.out.println("Combat: Checking Done");
    }
    
    public void clearBattles() {
        this.battles.clear();
    }
}
