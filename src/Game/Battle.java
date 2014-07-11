package Game;

import Map.Pathtype;
import Player.Unit;

public class Battle {
    Unit a;
    Unit b;
    int aDmg;
    int bDmg;

    public Battle(Unit a, Unit b) {
        System.out.println("Battle engaged\n\t" + a + " vs. " + b);
        this.a = a;
        this.b = b;
        /* @formatter:off */
        // Calculate modified attack damage
        this.aDmg = (int) (a.getAttack() * a.getType()
                .getAttackModifier(b.getType()));
        this.bDmg = (int) (b.getAttack() * b.getType()
                .getAttackModifier(a.getType()));
        /* @formatter:on */
    }

    /* Process battle */
    public void doBattle() {
        // A is stationary & B is moving: B then A
        if (a.getPath().getType() == Pathtype.STATIONARY) {
            System.out.println("B then A");
            System.out.println("\t" + b + " attacks " + a + " for " + bDmg);
            a.reduceHealth(bDmg);
            if (a.isDead()) {
                System.out.println(a + " killed by " + b);
                a.setDead();
            }
            else {
                System.out.println("\t" + a + " attacks " + b + " for " + aDmg);
                b.reduceHealth(aDmg);
                if (b.isDead()) {
                    System.out.println(b + " killed by " + a);
                    b.setDead();
                }
            }
        }
        // A is moving & B is stationary: A then B
        else if (b.getPath().getType() == Pathtype.STATIONARY) {
            System.out.println("A then B");
            b.reduceHealth(aDmg);
            if (b.isDead()) {
                System.out.println(b + " killed by " + a);
                b.setDead();
                return;
            }
            else {
                a.reduceHealth(bDmg);
                if (a.isDead()) {
                    System.out.println(a + " killed by " + b);
                    a.setDead();
                }
            }
        }
        // A and B simultaneously attack
        else {
            System.out.println("A and B");
            a.reduceHealth(bDmg);
            b.reduceHealth(aDmg);
            if (a.isDead()) {
                System.out.println(a + " killed by " + b);
                a.setDead();
            }
            if (b.isDead()) {
                System.out.println(b + " killed by " + a);
                b.setDead();
            }
        }
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

    /* Getters */
    /* Prob not needed.... */
    public Unit getA() {
        return this.a;
    }

    public Unit getB() {
        return this.b;
    }

    /* Overrides */
    public boolean equals(Battle b) {
        if (b.has(this.a, this.b)) {
            return true;
        }
        return false;
    }
}
