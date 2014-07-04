package Game;

import Map.Pathtype;
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

    /* Both sides attack simultaneously */
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
