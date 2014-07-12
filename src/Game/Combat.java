package Game;

import Map.Map;
import Map.Tile;
import Player.Team;
import Player.Unit;

import java.util.ArrayList;
import java.util.ListIterator;

public class Combat {
    private Turn turn;
    private Map map;
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<AdjNode> adj = new ArrayList<AdjNode>();
    private ArrayList<Battle> battles = new ArrayList<Battle>();

    /* Public methods */
    public Combat(Turn turn, Team[] teams) {
        this.turn = turn;
        this.map = turn.getMap();
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
        System.out.println("Combat: checking for battles");
        for (Unit unit : units) {
            checkAdjacent(unit);
            if (hasAdjacent(unit)) {
                Unit[] enemies = getAdjacent(unit);
                check: for (Unit enemy : enemies) {
                    if (enemy != null) {
                        // System.out.println("Adjacent enemy");
                        for (Battle b : battles) {
                            if (b.contains(unit, enemy)) {
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
        int[] cycle = new int[4];

        private AdjNode(Unit unit) {
            this.unit = unit;
            for (Unit u : adj) {
                u = null;
            }
            for (int i : cycle) {
                i = 0;
            }
        }

        private boolean hasAdjacent() {
            for (Unit u : adj) {
                if (u != null) {
                    return true;
                }
            }
            return false;
        }

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
            cycle[dir] = turn.getCycle();
        }

        /* Getters */
        private Unit[] getAdjacent() {
            return this.adj;
        }

        private Unit getUnit() {
            return this.unit;
        }

        /* Test print */
        private void print() {
            for (Unit u : adj) {
                System.out.println(u);
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
        int i = findUnit(u1);
        AdjNode node = adj.get(i);
        return node.contains(u2);
    }

    /* Check if there are adjacent enemy units */
    private boolean hasAdjacent(Unit unit) {
        int i = findUnit(unit);
        AdjNode node = adj.get(i);
        return node.hasAdjacent();
    }

    /* Checks for adjacent enemies and */
    /* adds them to adjacent array */
    private void checkAdjacent(Unit unit) {
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
}
