package Game;

import Player.Unit;

public class Battle {
    Unit a;
    Unit b;

    public Battle(Unit a, Unit b) {
        this.a = a;
        this.b = b;
        System.out.println("Battle engaged\n\t" + a + " vs. " + b);
    }

    public void attack() {
        a.reduceHealth(b.getAttack());
        b.reduceHealth(a.getAttack());
    }

    /** Getter methods */
    public Unit getA() {
        return this.a;
    }

    public Unit getB() {
        return this.b;
    }

    /** Overrides */
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
