package Game;

import Map.Pathtype;
import Player.Archer;
import Player.Unit;

public class Battle {
    Unit a;
    Unit b;
    int aDmg;
    int bDmg;
    int flanked;
    
    /* Public methods */
    public Battle(Unit a, Unit b, int flanked) {
        System.out.println("Battle engaged\n\t" + a + " vs. " + b);
        this.a = a;
        this.b = b;
        this.flanked = flanked;
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
        if (this.flanked == 0 || this.flanked == 3) {
            normal();
        }
        else {
            try {
                flanked();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* Battle for archers */
    public void doBattle(Archer a) {
         int damage = a.getRangedAttack();
         b.reduceHealth(damage);
         if (b.isDead()) {
             System.out.println(b + " killed by " + a);
             b.setDead();             
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
    
    /* Private methods */
    /* Calculate damage modifiers */
    private void damageMods() {
        // Calculate modified attack damage
        /* @formatter:off */
        this.aDmg = (int) (a.getAttack() * a.getType()
                .getAttackModifier(b.getType()));
        this.bDmg = (int) (b.getAttack() * b.getType()
                .getAttackModifier(a.getType()));
        /* @formatter:on */
    }
    
    /* Method to do battle normally or when both units are flanked */
    private void normal() {
        damageMods();
        if (this.flanked == 3) {
            System.out.println(a + " & " + b + " are flanked");
            this.aDmg = (int) (aDmg * 0.5);
            this.bDmg = (int) (bDmg * 0.5);            
        }
        // A is stationary & B is moving: B then A
        if (a.getPathtype() == Pathtype.STATIONARY &&
                b.getPathtype() != Pathtype.STATIONARY) {
            System.out.println("B then A");
            if (b.getPathtype() == Pathtype.GOAL ||
                    b.getPathtype() == Pathtype.SAFEGOAL) {
                System.out.println(b + " goal move");
                // Unit b does nothing
                // Check if b is blocked by a
                if (b.getPathSize() > 1) {
                    
                }
                else {
                    if (b.isBlockedBy(a)) {
                        b.clearPath();
                    }
                }
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
        // This should not happen
        else if (b.getPathtype() == Pathtype.STATIONARY &&
                a.getPathtype() != Pathtype.STATIONARY) {
            System.out.println("ERROR: A not stationary\nA then B");   
            if (a.getPathtype() == Pathtype.GOAL ||
                    a.getPathtype() == Pathtype.SAFEGOAL) {
                System.out.println(a + " goal move");
                // Unit a does nothing
                // Check if a is blocked by b
                if (a.getPathSize() > 1) {
                    
                }
                else {
                    if (a.isBlockedBy(b)) {
                        a.clearPath();
                    }
                }
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
    
    /* Method to do battle when there is a flank */
    private void flanked() throws Exception {
        if (this.flanked == 0 || this.flanked == 3) {
            throw new Exception("Not flanked/both flanked");
        }
        damageMods();
        // B attacks first
        if (this.flanked == 1) {
            System.out.println(a + " is flanked");
            this.aDmg = (int) (aDmg * 0.5);
            if (b.getPathtype() == Pathtype.GOAL ||
                    b.getPathtype() == Pathtype.SAFEGOAL) {
                System.out.println(b + " goal move");
                // Unit b does nothing
                // Check if b is blocked by a
                if (b.getPathSize() > 1) {
                    
                }
                else {
                    if (b.isBlockedBy(a)) {
                        b.clearPath();
                    }
                }
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
        // A attacks first
        else {
            System.out.println(b + " is flanked");
            this.bDmg = (int) (bDmg * 0.5);
            if (a.getPathtype() == Pathtype.GOAL ||
                    a.getPathtype() == Pathtype.SAFEGOAL) {
                System.out.println(a + " goal move");
                // Unit a does nothing
                // Check if a is blocked by b
                if (a.getPathSize() > 1) {
                    
                }
                else {
                    if (a.isBlockedBy(b)) {
                        a.clearPath();
                    }
                }
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
            
    }
}
