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

    /* Both sides attack simultaneously */
    public void doBattle() {
        switch (a.getPath().getType()) {
            case STATIONARY:
                switch (b.getPath().getType()) {
                    case STANDARD:
                        b.reduceHealth(a.getAttack());
                        if (!a.isDead()) {
                            a.reduceHealth(b.getAttack());
                        }
                        break;
                    case GOAL:
                    case SAFEGOAL:
                    default:
                        break;
                }
                break;
            case STANDARD:
                switch (b.getPath().getType()) {
                    case STANDARD:
                        b.reduceHealth(a.getAttack());
                        if (!a.isDead()) {
                            a.reduceHealth(b.getAttack());
                        }
                        break;
                    case GOAL:
                    case SAFEGOAL:
                    default:
                        break;
                }
                break;
            case GOAL:
                switch (b.getPath().getType()) {
                    case STANDARD:
                        b.reduceHealth(a.getAttack());
                        if (!a.isDead()) {
                            a.reduceHealth(b.getAttack());
                        }
                        break;
                    case GOAL:
                    case SAFEGOAL:
                    default:
                        break;
                }
                break;
            case SAFEGOAL:
                switch (b.getPath().getType()) {
                    case STANDARD:
                        b.reduceHealth(a.getAttack());
                        if (!a.isDead()) {
                            a.reduceHealth(b.getAttack());
                        }
                        break;
                    case GOAL:
                    case SAFEGOAL:
                    default:
                        break;
                }
                break;
            default:
                System.out.println("Battle derped");
                break;
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
