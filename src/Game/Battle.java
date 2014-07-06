package Game;

import Map.Pathtype;
import Player.Type;
import Player.Unit;

public class Battle {
    Unit a;
    Unit b;
    int aDmg;
    int bDmg;

    public Battle(Unit a, Unit b) {
        this.a = a;
        this.b = b;
        this.aDmg = a.getAttack();
        this.bDmg = b.getAttack();
        System.out.println("Battle engaged\n\t" + a + " vs. " + b);
    }
    
    private class Modifier {
        double[][] modifiers = {
                { 1, 1, 2, 1 },
                { 1, 1, 1, 2 },
                { 1, 2, 1, 2 },
                { 2, 0.75, 2, 1 }
                };
        
        public Modifier() {
            // IDK
        }
        
        public double lookup(Unit a, Unit b) {
            int attacker;
            int defender;
            Type aType = a.getType();
            Type bType = b.getType();
            switch (aType) {
                case FOOTMAN:
                    attacker = 0;
                    break;
                case SPEARMAN:
                    attacker = 1;
                    break;
                case ARCHER:
                    attacker = 2;
                    break;
                case CAVALRY:
                    attacker = 2;
                    break;
                default:
                    attacker = -1;
                    break;
            }
            switch (bType) {
                case FOOTMAN:
                    defender = 0;
                    break;
                case SPEARMAN:
                    defender = 1;
                    break;
                case ARCHER:
                    defender = 2;
                    break;
                case CAVALRY:
                    defender = 2;
                    break;
                default:
                    defender = -1;
                    break;
            }
            if (attacker == -1 || defender == -1) {
                return 1;
            }
            return modifiers[attacker][defender];
        }
    }
    
    /* Process battle */
    public void doBattle() {
        // Calculate damage modifiers
        this.aDmg = modifyDmg(a, b, aDmg);
        this.bDmg = modifyDmg(b, a, bDmg);
        // A is stationary & B is moving: B then A
        if (a.getPath().getType() == Pathtype.STATIONARY) {
            b.reduceHealth(aDmg);
            a.reduceHealth(bDmg);
        }
        // A is moving & B is stationary: A then B
        else if (b.getPath().getType() == Pathtype.STATIONARY) {
            a.reduceHealth(bDmg);
            b.reduceHealth(aDmg);
        }
        // A and B simultaneously attack
        else {
            a.reduceHealth(bDmg);
            b.reduceHealth(aDmg);
        }
    }

    /* Calculates unit damage modifiers */
    public int modifyDmg(Unit atk, Unit def, int attack) {
        attack = (int) (attack * new Modifier().lookup(atk, def));
        return attack;
    }

    /* Check if battle exists */
    public boolean has(Unit ally, Unit enemy) {
        if (this.a.equals(ally) && this.b.equals(enemy)) {
            return true;
        }
        else if (this.a.equals(enemy) && this.b.equals(ally)) {
            return true;
        }
        return false;
    }

    /* Getter methods */
    public Unit getA() {
        return this.a;
    }

    public Unit getB() {
        return this.b;
    }

    /* Overrides */
    public boolean equals(Battle b) {
        if (this.a.equals(b.getA()) && this.b.equals(b.getB())) {
            return true;
        }
        else if (this.a.equals(b.getB()) && this.b.equals(b.getA())) {
            return true;
        }
        return false;
    }
}
