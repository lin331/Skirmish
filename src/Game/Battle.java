package Game;

import Map.Pathtype;
import Player.Unit;

public class Battle {
    Unit a;
    Unit b;
    int aDmg;
    int bDmg;

    /* Public methods */
    public Battle(Unit a, Unit b) {
        System.out.println("Battle engaged\n\t" + a + " vs. " + b);
        this.a = a;
        this.b = b;
        // Calculate modified attack damage
        /* @formatter:off */
        this.aDmg = (int) (a.getAttack() * a.getType()
                .getAttackModifier(b.getType()));
        this.bDmg = (int) (b.getAttack() * b.getType()
                .getAttackModifier(a.getType()));
        /* @formatter:on */
    }

    /* Check if battle exists */
    public boolean contains(Unit ally, Unit enemy) {
        if (this.a == ally && this.b == enemy) {
            return true;
        }
        else if (this.a == enemy && this.b == ally) {
            return true;
        }
        return false;
    }

    /* Process battle */
    public void doBattle() {
        // A is stationary & B is moving: B then A
        if (a.getPathtype() == Pathtype.STATIONARY &&
                b.getPathtype() != Pathtype.STATIONARY) {
            System.out.println("B then A");
            if (b.getPathtype() == Pathtype.GOAL ||
                    b.getPathtype() == Pathtype.SAFEGOAL) {
                // Unit b does nothing
                System.out.println(b + " goal move");
            }
            else {
                System.out.println("\t" + b + " attacks " + a + " for " + bDmg);
                a.reduceHealth(bDmg);
                if (a.isDead()) {
                    System.out.println(a + " killed by " + b);
                    a.setDead();
                    return;
                }
            }
            System.out.println("\t" + a + " attacks " + b + " for " + aDmg);
            b.reduceHealth(aDmg);
            if (b.isDead()) {
                System.out.println(b + " killed by " + a);
                b.setDead();
            }
        }
        // A is moving & B is stationary: A then B
        else if (b.getPathtype() == Pathtype.STATIONARY &&
                a.getPathtype() != Pathtype.STATIONARY) {
            System.out.println("A then B");   
            if (a.getPathtype() == Pathtype.GOAL ||
                    a.getPathtype() == Pathtype.SAFEGOAL) {
                // Unit a does nothing
                System.out.println(a + " goal move");
            }
            else {
                b.reduceHealth(aDmg);
                if (b.isDead()) {
                    System.out.println(b + " killed by " + a);
                    b.setDead();
                    return;
                }
            }
            a.reduceHealth(bDmg);
            if (a.isDead()) {
                System.out.println(a + " killed by " + b);
                a.setDead();
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

    /* Getters */
    /* Prob not needed.... */
    public Unit getA() {
        return this.a;
    }

    public Unit getB() {
        return this.b;
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        Battle battle = (Battle) obj;
        if (battle.contains(this.a, this.b)) {
            return true;
        }
        return false;
    }
}
